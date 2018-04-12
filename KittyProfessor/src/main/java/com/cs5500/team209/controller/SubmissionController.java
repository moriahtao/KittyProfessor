package com.cs5500.team209.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
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
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        String courseId = assignment.getCourseId();
        Course course1 = courseService.getCourseByCourseId(courseId);
        String courseName = course1.getName();
        String courseCode = course1.getCourseCode();
        model.addAttribute("courseName", courseName);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("reports", reportDisplayList);
        model.addAttribute("assignment", assignment);
        model.addAttribute("otherReports", reportNotDisplayList);
        return "student-report";
    }

    @GetMapping("/report")
    public String getReport(@RequestParam("reportId") String reportId,
                                Model model) {
        Report report = reportService.getReportById(reportId);
        String reportLink = report.getFilePath();
        return reportLink;
    }


    @PostMapping("/uploadZipFile")
    public String uploadZipFile(HttpServletRequest request,
                                @RequestParam("assignmentId") String assignmentId,
                                @RequestParam("file") MultipartFile file,
                                Model model) throws IOException, InterruptedException {

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
        Files.createDirectories(Paths.get(path));
        storageService.store(file, Paths.get(path), file.getOriginalFilename());
        extractFolder(path+"/"+file.getOriginalFilename());
        new File(path+"/"+file.getOriginalFilename()).delete();
        Submission submission = new Submission(assignmentId, userName, noSubmission, path);
        UpdateSubmissionResult ups = submissionService.createSubmission(submission);

        MultipleFileUpload upload = tx.uploadDirectory("kittyprofessor",
                path,
                new File(path+"/"+(file.getOriginalFilename()
                        .substring(0, file.getOriginalFilename().length() - 4))),
                true);

        compareSubmissions(path, ups.getSubmission().getSubmissionId(),
                ups.getSubmission().getAssignmentId(), userName);

        model.addAttribute("assignments", assignmentService.getAssignmentsForCourse(courseId));
        return "assignment";
    }

    @PostMapping("/uploadGithubURL")
    public String uploadGithubURL(HttpServletRequest request,
                                @RequestParam("assignmentId") String assignmentId,
                                @RequestParam("githubURL") String githubURL,
                                Model model) throws GitAPIException {


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

        Git.cloneRepository()
                .setURI(githubURL)
                .setDirectory(Paths.get(path).toFile())
                .call();

        Submission submission = new Submission(assignmentId, userName, noSubmission, path);
        submissionService.createSubmission(submission);

        MultipleFileUpload upload = tx.uploadDirectory("kittyprofessor",
                path,
                new File(path),
                true);

        model.addAttribute("assignments", assignmentService.getAssignmentsForCourse(courseId));
        return "assignment";
    }

    @PostMapping("generateReport")
    public String handleFileUpload(HttpServletRequest request,
                                   @RequestParam("submissionId") String courseIds) {

        return "";
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
                                    String assignmentId,
                                    String userName) throws IOException {

        List<Submission> otherSubmissions =
                submissionService.getOtherStudentSubmissions(assignmentId, userName);
        String language = assignmentService.getAssignmentById(assignmentId).getLanguage();
        Submission submission = submissionService.getSubmissionById(submissionId);
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        Course course = courseService.getCourseByCourseId(assignment.getCourseId());
        User user = userService.getUserByUsername(course.getUserName()).getUser();
        String srcFolder = "exercise1/src";

        copyIntoPath(submissionPath, srcFolder);
        List<EmailReport> emailReports = new ArrayList<>();
        for(Submission oSubmission : otherSubmissions) {
            String targetFolder = "exercise1/target";
            copyIntoPath(oSubmission.getFilePath(), targetFolder);
            String transformedPath = transformFileName();
            String reportPath = "src/main/resources/static/report/"+ transformedPath; // for file storing
            Files.createDirectories(Paths.get(reportPath));

            double score = Parser.parse(reportPath, language,
                    submission.getUsername(), oSubmission.getUsername(), assignment.getName());
            // save compared report
            reportService.createReport(new Report(assignmentId, submissionId,
                    oSubmission.getSubmissionId(),
                    "http://kittyprofessor-report.s3-website-us-east-1.amazonaws.com/"
                            +transformedPath+"/match0.html", score));

            if(score >= assignment.getThreshold()) {
                emailReports.add(new EmailReport(submission.getUsername(),
                        oSubmission.getUsername(), score,
                        "http://kittyprofessor-report.s3-website-us-east-1.amazonaws.com/"
                                +transformedPath+"/match0.html"));
            }
            // only clean other submission folder if compare not end
            deleteTargetDirectory(targetFolder);
            //deleteTargetDirectory(reportPath);

        }
        deleteTargetDirectory("exercise1/");
        if (emailReports.size() > 0) {
            sendEmail(emailReports, user.getEmail());
        }
    }

    public class EmailReport {
        String student1;
        String student2;
        double score;
        String url;

        public EmailReport(String student1, String student2, double score, String url) {
            this.student1 = student1;
            this.student2 = student2;
            this.score = score;
            this.url = url;
        }
    }

    private void sendEmail(List<EmailReport> emailReports, String to) {

        String BODY_HTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>The following students are adventurous in doing their work</h1>"
                + "<table>"
                + "<tr>"
                + "<th>User</th>"
                + "<th>User</th>"
                + "<th>Score</th>"
                + "<th>Link</th>"
                + "</tr>";

        StringBuffer html_body = new StringBuffer(BODY_HTML);
        for(EmailReport emailReport : emailReports) {
            html_body.append("<tr><td>"+emailReport.student1+
                    "</td><td>"+emailReport.student2+"</td>"+
                    "<td>"+emailReport.score+"</td>"+
                    "<td>"+emailReport.url+"</td></tr>");
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

    @GetMapping("/allStudents")
    public String getAllSubmissionList(HttpServletRequest request,
                                       @RequestParam("courseId") String courseId,
                                        Model model) {
        List<StudentCourse> studentCourses = studentCourseService.getCourseByCourseId(courseId);
        List<String> studentList = new ArrayList<>();
        for(StudentCourse sc: studentCourses) {
            studentList.add(sc.getUserName());
        }
        model.addAttribute("students", studentList);

        return "student-list";
    }


    @GetMapping("/studentSubmissions")
    public String getStudentSubmissionList(HttpServletRequest request,
                                           @RequestParam("studentUsername") String studentUsername,
                                       Model model) {
        List<Submission> submissionList = submissionService.getSubmissionByUsername(studentUsername);
        model.addAttribute("submissions", submissionList);

        return "submission-list";
    }

    private static boolean isRedirected( Map<String, List<String>> header ) {
        for( String hv : header.get( null )) {
            if(   hv.contains( " 301 " )
                    || hv.contains( " 302 " )) return true;
        }
        return false;
    }

    private void downloadFromExternalURL() throws IOException {
        String link =
                "http://github.com/downloads/TheHolyWaffle/ChampionHelper/" +
                        "ChampionHelper-4.jar";
        String            fileName = "ChampionHelper-4.jar";
        URL url  = new URL( link );
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        Map< String, List< String >> header = http.getHeaderFields();
        while( isRedirected( header )) {
            link = header.get( "Location" ).get( 0 );
            url    = new URL( link );
            http   = (HttpURLConnection)url.openConnection();
            header = http.getHeaderFields();
        }
        InputStream  input  = http.getInputStream();
        byte[]       buffer = new byte[4096];
        int          n      = -1;
        OutputStream output = new FileOutputStream( new File( fileName ));
        while ((n = input.read(buffer)) != -1) {
            output.write( buffer, 0, n );
        }
        output.close();
    }

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
