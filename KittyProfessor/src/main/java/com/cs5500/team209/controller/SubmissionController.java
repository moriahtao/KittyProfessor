package com.cs5500.team209.controller;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.cs5500.team209.Parser;
import com.cs5500.team209.model.*;
import com.cs5500.team209.model.dto.ReportDisplay;
import com.cs5500.team209.model.dto.UpdateSubmissionResult;
import com.cs5500.team209.service.*;
import com.cs5500.team209.storage.StorageService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by mengtao on 4/4/18.
 */
@Controller
@Scope("session")
public class SubmissionController {
    final static Logger logger = Logger.getLogger(SubmissionController.class);
    final static AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    final static TransferManager tx = new TransferManager(s3);

    @Autowired
    SubmissionService submissionService;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    StorageService storageService;

    @Autowired
    ReportService reportService;

    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    CourseService courseService;

    /**
     * Controller for reports
     * @param assignmentId assignmentID from front end
     * @param model model to send back data
     * @return
     */
    @GetMapping("/reports")
    public String getReportList(@RequestParam("assignmentId") String assignmentId,
                                    Model model) {
        List<ReportDisplay> reportDisplayList = new ArrayList<>();
        List<ReportDisplay> reportNotDisplayList = new ArrayList<>();
        List<Report> reportList = reportService.getReportsForAssignment(assignmentId);
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        for (Report r: reportList) {
            ReportDisplay rDisplay = new ReportDisplay(r);
            String username1 = submissionService.getSubmissionById(rDisplay.getReport()
                    .getSubmissionId1()).getUsername();
            String email1 = userService.getUserByUsername(username1).getUser().getEmail();
            String username2 = submissionService.getSubmissionById(rDisplay.getReport()
                    .getSubmissionId2()).getUsername();
            String email2 = userService.getUserByUsername(username2).getUser().getEmail();
            rDisplay.setUser1(email1);
            rDisplay.setUser2(email2);
            if (rDisplay.getReport().getScore() < assignment.getThreshold()) {
                reportNotDisplayList.add(rDisplay);
            } else {
                reportDisplayList.add(rDisplay);
            }
        }

        Course course = courseService.getCourseByCourseId(assignment.getCourseId());
        List<Course> courses = courseService.findCourseByCriteria(course.getCourseCode(),
                course.getCourseId());
        String courseId = assignment.getCourseId();
        Course course1 = courseService.getCourseByCourseId(courseId);
        String courseName = course1.getName();
        String courseCode = course1.getCourseCode();
        model.addAttribute("courseName", courseName);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("reports", reportDisplayList);
        model.addAttribute("assignment", assignment);
        model.addAttribute("otherReports", reportNotDisplayList);
        model.addAttribute("courses", courses);
        return "student-report";
    }


    /**
     * get individual report
     * @param reportId id for the report
     * @param model send data for model
     * @return
     */
    @GetMapping("/report")
    public String getReport(@RequestParam("reportId") String reportId,
                                Model model) {
        Report report = reportService.getReportById(reportId);
        String reportLink = report.getFilePath();
        return reportLink;
    }

    /**
     * Uploads zip file
     * @param request for accessing session
     * @param assignmentId assignmentID from front end
     * @param file file to upload
     * @param model model to send back data
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/uploadZipFile")
    public String uploadZipFile(HttpServletRequest request,
                                @RequestParam("assignmentId") String assignmentId,
                                @RequestParam("file") MultipartFile file,
                                Model model) {

        String userName = (String) request.getSession().getAttribute("userName");
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        Course course = courseService.getCourseByCourseId(assignment.getCourseId());
        List<Submission> submissions = submissionService.getSubmissionsForAssignment
                (assignmentId, userName);
        int noSubmission = submissions.size() + 1;

        String courseId = course.getCourseId();
        String instructor = course.getUserName();
        String term = course.getTerm();
        String path = "data/"+instructor+"/"+
                term+"/"+courseId+"/"+assignmentId+"/"+userName+"/"+"s"+noSubmission;
        try {
            Files.createDirectories(Paths.get(path));
            storageService.store(file, Paths.get(path), file.getOriginalFilename());
            extractFolder(path+"/"+file.getOriginalFilename());
        } catch (IOException e) {
            logger.debug(e);
        }

        new File(path+"/"+file.getOriginalFilename()).delete();
        Submission submission = new Submission(assignmentId, userName, noSubmission, path);
        UpdateSubmissionResult ups = submissionService.createSubmission(submission);

        MultipleFileUpload upload = tx.uploadDirectory("kittyprofessor",
                path,
                new File(path+"/"+(file.getOriginalFilename()
                        .substring(0, file.getOriginalFilename().length() - 4))),
                true);

        try {
            compareSubmissions(path, ups.getSubmission().getSubmissionId(),
                    getSimilarAssignments(course.getRelatedCourses().split(","), assignment),
                    assignment,
                    userName);
        } catch (IOException e) {
            logger.debug(e);
        }

        model.addAttribute("assignments", assignmentService.getAssignmentsForCourse(courseId));
        return "assignment";
    }

    /**
     *
     * Helper function helps in accumulating similar Assignments
     * @param courseIds List of related courses selected by professor
     * @param assignment Assignment for which the new submission happened
     * @return List of assignments
     */
    private List<Assignment> getSimilarAssignments(String[] courseIds, Assignment assignment) {
        List<Assignment> rAssignments = new ArrayList<>();
        rAssignments.add(assignment);
        for(String courseId : courseIds) {
            List<Assignment> assignments = assignmentService.getAssignmentsForCourse(courseId);
            for (Assignment assignment1 : assignments) {
                if(assignment.getName().equals(assignment1.getName())) {
                    rAssignments.add(assignment1);
                }
            }
        }
        return rAssignments;
    }

    /**
     *
     * Uploads from github file
     * @param request for accessing session
     * @param assignmentId assignmentID from front end
     * @param model model to send back data
     * @return
     * @throws GitAPIException
     */
    @PostMapping("/uploadGithubURL")
    public String uploadGithubURL(HttpServletRequest request,
                                @RequestParam("assignmentId") String assignmentId,
                                @RequestParam("githubURL") String githubURL,
                                Model model) {


        String userName = (String) request.getSession().getAttribute("userName");
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        Course course = courseService.getCourseByCourseId(assignment.getCourseId());
        List<Submission> submissions = submissionService.getSubmissionsForAssignment
                (assignmentId, userName);
        int noSubmission = submissions.size() + 1;

        String courseId = course.getCourseId();
        String instructor = course.getUserName();
        String term = course.getTerm();
        String path = "data/"+instructor+"/"+
                term+"/"+courseId+"/"+assignmentId+"/"+userName+"/"+"s"+noSubmission;

        try {
            Git.cloneRepository()
                    .setURI(githubURL)
                    .setDirectory(Paths.get(path).toFile())
                    .call();
        } catch (GitAPIException e) {
            logger.error(e);
        }

        Submission submission = new Submission(assignmentId, userName, noSubmission, path);
        UpdateSubmissionResult ups = submissionService.createSubmission(submission);

        MultipleFileUpload upload = tx.uploadDirectory("kittyprofessor",
                path,
                new File(path),
                true);

        try {
            compareSubmissions(path, ups.getSubmission().getSubmissionId(),
                    getSimilarAssignments(course.getRelatedCourses().split(","),
                            assignment),
                    assignment,
                    userName);
        } catch (IOException e) {
            logger.error(e);
        }

        model.addAttribute("assignments", assignmentService.getAssignmentsForCourse(courseId));
        return "assignment";
    }


    /**
     * Incremental comparison strategy
     * @param submissionPath submission file path (under data/)
     * @param submissionId the id of the new submitted submission
     * @param userName the username of the student submitted
     * @throws IOException
     */
    private void compareSubmissions(String submissionPath,
                                    String submissionId,
                                    List<Assignment> assignments,
                                    Assignment assignmentT,
                                    String userName) throws IOException {
        String srcFolder = "exercise1/src";
        copyIntoPath(submissionPath, srcFolder);
        List<EmailReport> emailReports = new ArrayList<>();
        Course course1 = courseService.getCourseByCourseId(assignmentT.getCourseId());
        String courseInfo1 = course1.getName() + "by" + course1.getUserName()
                + "in" + course1.getTerm();

        String language = assignmentT.getLanguage();

        for(Assignment assignment : assignments) {
            String assignmentId = assignment.getAssignmentId();
            List<Submission> otherSubmissions =
                    submissionService.getOtherStudentSubmissions(assignmentId, userName);
            Assignment assignment2 = assignmentService.getAssignmentById(assignmentId);
            Course course2 = courseService.getCourseByCourseId(assignment2.getCourseId());
            String courseInfo2 = course2.getName() + " by " + course2.getUserName()
                    + " in " + course2.getTerm();

            Submission submission = submissionService.getSubmissionById(submissionId);

            for (Submission oSubmission : otherSubmissions) {
                String targetFolder = "exercise1/target";
                copyIntoPath(oSubmission.getFilePath(), targetFolder);
                String transformedPath = transformFileName();
                String reportPath = "src/main/resources/static/report/" + transformedPath;
                // for file storing


                Files.createDirectories(Paths.get(reportPath));

                double score = Parser.parse(reportPath, language,
                        submission.getUsername(),
                        oSubmission.getUsername(),
                        assignment.getName());
                // save compared report
                reportService.createReport(new Report(assignmentId, submissionId,
                        oSubmission.getSubmissionId(),
                        courseInfo1, courseInfo2,
                        "http://kittyprofessor-report.s3-website-us-east-1.amazonaws.com/"
                                + transformedPath + "/match0.html", score));

                if (score >= assignment.getThreshold()) {
                    emailReports.add(new EmailReport(submission.getUsername(),
                            oSubmission.getUsername(), courseInfo1, courseInfo2,
                            score,
                            "http://kittyprofessor-report.s3-website-us-east-1.amazonaws.com/"
                                    + transformedPath + "/match0.html"));
                }
                // only clean other submission folder if compare not end
                deleteTargetDirectory(targetFolder);
                //deleteTargetDirectory(reportPath);

            }
        }
        deleteTargetDirectory("exercise1/");
        User user = userService.getUserByUsername(course1.getUserName()).getUser();

        if (emailReports.size() > 0) {
            sendEmail(emailReports, user.getEmail());
        }
    }

    /**
     * Class as data structure for sending email
     */
    public class EmailReport {
        String student1;
        String student2;
        String courseInfo1;
        String courseInfo2;
        double score;
        String url;

        public EmailReport(String student1, String student2,
                           String courseInfo1, String courseInfo2,
                           double score, String url) {
            this.student1 = student1;
            this.student2 = student2;
            this.courseInfo1 = courseInfo1;
            this.courseInfo2 = courseInfo2;
            this.score = score;
            this.url = url;
        }
    }

    /**
     * Function to send email
     * @param emailReports report details
     * @param to email address
     */
    private void sendEmail(List<EmailReport> emailReports, String to) {

        String BODY_HTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>The following students are adventurous in doing their work</h1>"
                + "<table border='1'>"
                + "<tr>"
                + "<th>User1</th>"
                + "<th>User2</th>"
                + "<th>User1 from course</th>"
                + "<th>User2 from course</th>"
                + "<th>Score</th>"
                + "<th>Link</th>"
                + "</tr>";

        StringBuffer html_body = new StringBuffer(BODY_HTML);
        for(EmailReport emailReport : emailReports) {
            html_body.append("<tr><td>"+emailReport.student1+
                    "</td><td>"+emailReport.student2+"</td>"+
                    "</td><td>"+emailReport.courseInfo1+"</td>"+
                    "</td><td>"+emailReport.courseInfo2+"</td>"+
                    "<td>"+emailReport.score+"</td>"+
                    "<td><a href="+emailReport.url+">Link to report</a>" +
                    "</td></tr>");
        }
        html_body.append("</body>"
                + "</html>");

        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8")
                                .withData(html_body.toString())))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData("report for assignment")))
                .withSource("report@kittyprofessor.com");

        client.sendEmail(request);
    }


    /**
     * Transform input file name into a unique name
     *
     * @return transformed name
     */
    private String transformFileName() {
        String fileName = UUID.randomUUID().toString();
        return fileName;
    }

    /**
     * Copy from source file path to destination file path
     *
     * @param srcPath path of the file to be copied
     * @param dstPath path of the file copied to
     * @throws IOException
     */
    private void copyIntoPath(String srcPath, String dstPath) throws IOException {
        File sourceFile = new File(srcPath);
        File targetDir = new File(dstPath);

        FileUtils.copyDirectory(sourceFile, targetDir);
    }

    /**
     * recursively delete target directory
     *
     * @param path the path of the folder to be deleted
     * @throws IOException
     */
    private void deleteTargetDirectory(String path) throws IOException {
        FileUtils.deleteDirectory(new File(path));
    }

    /**
     * StudentList
     * @param courseId course Id
     * @param model model to send data for front end
     * @return
     */
    @GetMapping("/allStudents")
    public String getAllSubmissionList(@RequestParam("courseId") String courseId,
                                        Model model) {
        List<StudentCourse> studentCourses = studentCourseService.getCourseByCourseId(courseId);
        List<String> studentList = new ArrayList<>();
        for(StudentCourse sc: studentCourses) {
            studentList.add(sc.getUserName());
        }
        model.addAttribute("students", studentList);

        return "student-list";
    }

    /**
     * Student submission list
     * @param studentUsername student user name
     * @param model model to send data
     * @return
     */
    @GetMapping("/studentSubmissions")
    public String getStudentSubmissionList(@RequestParam("studentUsername") String studentUsername,
                                       Model model) {
        List<Submission> submissionList = submissionService.getSubmissionByUsername(studentUsername);
        model.addAttribute("submissions", submissionList);

        return "submission-list";
    }

    /**
     * Extracts zip file
     * @param zipFile location
     * @throws ZipException
     * @throws IOException
     */
    private void extractFolder(String zipFile) throws ZipException, IOException
    {
        System.out.println(zipFile);
        int BUFFER = 2048;
        File file = new File(zipFile);

        ZipFile zip = new ZipFile(file);
        String newPath = zipFile.substring(0, zipFile.length() - 4);

        new File(newPath).mkdir();
        Enumeration zipFileEntries = zip.entries();

        // Process each entry
        while (zipFileEntries.hasMoreElements())
        {
            // grab a zip file entry
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            if(currentEntry.charAt(0) == '_') continue;
            File destFile = new File(newPath, currentEntry);
            //destFile = new File(newPath, destFile.getName());
            File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            if (!entry.isDirectory())
            {
                if(entry.getName().charAt(0)=='.') continue;
                BufferedInputStream is = new BufferedInputStream(zip
                        .getInputStream(entry));
                int currentByte;
                // establish buffer for writing file
                byte data[] = new byte[BUFFER];

                // write the current file to disk
                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        BUFFER);

                // read and write until last byte is encountered
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }

            if (currentEntry.endsWith(".zip"))
            {
                // found a zip file, try to open
                extractFolder(destFile.getAbsolutePath());
            }
        }
    }
}
