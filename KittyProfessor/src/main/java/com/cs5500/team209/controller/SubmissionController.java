package com.cs5500.team209.controller;

import com.amazonaws.regions.Regions;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static sun.security.x509.X509CertInfo.SUBJECT;

/**
 * Created by mengtao on 4/4/18.
 */
@Controller
@Scope("session")
public class SubmissionController {
    final static Logger logger = Logger.getLogger(SubmissionController.class);

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

    @GetMapping("/submissions")
    public String getSubmissionList(HttpServletRequest request, @RequestParam("assignmentId") String assignmentId,
                                    Model model) {
        String username = (String) request.getSession().getAttribute("userName");
        List<Submission> submissionList = submissionService.getSubmissionsForAssignment(assignmentId, username);
        int nextSubmissionIdx = submissionList.size() + 1;
        Submission submission = new Submission(assignmentId, username, nextSubmissionIdx);

        model.addAttribute("submission", submission);
        model.addAttribute("submissions", submissionList);
        return "submission";
    }

    @GetMapping("/reports")
    public String getReportList(@RequestParam("assignmentId") String assignmentId,
                                    Model model) {
        List<ReportDisplay> reportDisplayList = new ArrayList<>();
        List<ReportDisplay> reportNotDisplayList = new ArrayList<>();
        List<Report> reportList = reportService.getReportsForAssignment(assignmentId);
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        for (Report r: reportList) {
            ReportDisplay rDisplay = new ReportDisplay(r);
            String username1 = submissionService.getSubmissionById(rDisplay.getReport().getSubmissionId1()).getUsername();
            String email1 = userService.getUserByUsername(username1).getUser().getEmail();
            String username2 = submissionService.getSubmissionById(rDisplay.getReport().getSubmissionId2()).getUsername();
            String email2 = userService.getUserByUsername(username2).getUser().getEmail();
            rDisplay.setUser1(email1);
            rDisplay.setUser2(email2);
            if (rDisplay.getReport().getScore() < assignment.getThreshold()) {
                reportNotDisplayList.add(rDisplay);
            } else {
                reportDisplayList.add(rDisplay);
            }
        }


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

    @PostMapping("/addSubmissions")
    public String createSubmissionForAssignment(HttpServletRequest request, @ModelAttribute Submission submission,
                                                Model model) {
        String username = (String) request.getSession().getAttribute("userName");
        Submission submissionWithFields = new Submission(submission.getAssignmentId(), username, submission.getSubmissionNum());
        submissionService.createSubmission(submissionWithFields);
        return "submission";
    }

    @PostMapping("/upload")
    public String handleFileUpload(HttpServletRequest request, @RequestParam("submissionId") String submissionId,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        Submission queriedSubmission = submissionService.getSubmissionById(submissionId);
        if (queriedSubmission != null) {
            Files.createDirectories(Paths.get("data/"));
            // change into unique name
            String fileName = transformFileName(file.getOriginalFilename());
            String currentFilePath = "data/" + fileName;
            // save file path into submission
            UpdateSubmissionResult updateSubmissionResult =
                    submissionService.addFileToSubmission(currentFilePath, queriedSubmission);
            if (updateSubmissionResult.isSuccess()) {
                //store
                storageService.store(file, Paths.get("data/"), fileName);
                compareSubmissions(currentFilePath, submissionId, queriedSubmission.getAssignmentId(), userName);
            } else {
                logger.warn("updateSubmission fail");
            }
        } else {
            logger.warn("fetch submission fail");
        }
        return "redirect:/";
    }

    /**
     * Incremental comparison strategy
     * @param submissionPath submission file path (under data/)
     * @param submissionId the id of the new submitted submission
     * @param userName the username of the student submitted
     * @throws IOException
     */
    private void compareSubmissions(String submissionPath, String submissionId, String assignmentId, String userName) throws IOException {
        HashMap<String, String> submissionFileIdMap = new HashMap<>();
        // query: same assignmentId, different username
        List<Submission> otherSubmissions =
                new ArrayList<>(submissionService.getOtherStudentSubmissions(assignmentId, userName));
        List<String> otherSubmissionFilePaths = new ArrayList<>();
        for (Submission s : otherSubmissions) {
            // one submission has at least one file
            // TODO: will refactor SubmissionFilePath to single String
            if (!s.getFilePaths().isEmpty()) {
                otherSubmissionFilePaths.add(s.getFilePaths().get(0));
                submissionFileIdMap.put(s.getFilePaths().get(0), s.getSubmissionId());
            }
        }
        // having other submissions
        // then compare with current submission
        if (!otherSubmissionFilePaths.isEmpty()) {
            String srcPath = "exercise1/src/src.zip";
            String srcFolder = "exercise1/src";
            copyIntoPath(submissionPath, srcPath);
            unzip(srcPath, srcFolder);
            // incrementally compare
            for (String s : otherSubmissionFilePaths) {
                String targetPath = "exercise1/target/target.zip";
                String targetFolder = "exercise1/target";
                copyIntoPath(s, targetPath);
                //extract
                unzip(targetPath, targetFolder);
                String reportPath = "dummy.html";
                String transformedPath = transformFileName(reportPath);
                //String urlPath = "reports/" + transformedPath; //for web rendering
                reportPath = "src/main/resources/static/reports/" + transformedPath; // for file storing
                double score = Parser.parse(reportPath);
                // save compared report
                reportService.createReport(new Report(assignmentId, submissionId, submissionFileIdMap.get(s),
                        transformedPath, score));

                Assignment assignment = assignmentService.getAssignmentById(assignmentId);
                Course course = courseService.getCourseByCourseId(assignment.getCourseId());
                User user = userService.getUserByUsername(course.getUserName()).getUser();

                if(score > assignment.getThreshold()) {
                    sendEmail(user.getEmail(), transformedPath);
                }
                // only clean other submission folder if compare not end
                deleteTargetDirectory(targetFolder);
            }
            deleteTargetDirectory("exercise1/");
        }
    }

    private String sendEmail(String to, String path) {

        String from = "report@kittyprofessor.com";
        String TEXTBODY = "Autogenerated email";

        String BODY_HTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>The following students needs your attention</h1>"
                + "<a href=http://kittyprofessor.s3-website-us-east-1.amazonaws.com/"
                + path
                + ">Link to report</a>"
                + "</body>"
                + "</html>";

        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        // Replace US_WEST_2 with the AWS Region you're using for
                        // Amazon SES.
                        .withRegion(Regions.US_EAST_1).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8")
                                .withData(BODY_HTML)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData("report for assignment")))
                .withSource(from);

        client.sendEmail(request);
        System.out.println("Email sent!");
        return "";
    }



    /**
     * unzip zipFile into destDir
     * @param zipFilePath the file path of zip file
     * @param destDir the unzip target dir path
     * @throws IOException
     */
    private static boolean unzip(String zipFilePath, String destDir) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            entries.nextElement();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                // TODO:
                // assumption: no more folder structure in submission
                // assumption: no "/" in file names
                if (!entry.isDirectory()) {
                    File entryDestination = new File(destDir, entry.getName().split("/")[1]);
                    entryDestination.getParentFile().mkdirs();
                    InputStream in = zipFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(entryDestination);
                    IOUtils.copy(in, out);
                    IOUtils.closeQuietly(in);
                    out.close();
                }
            }
        } finally {
            zipFile.close();
        }
        File oldfile = new File(zipFilePath);
        return oldfile.delete();
    }


    /**
     * Transform input file name into a unique name
     *
     * @param file the file for changing name
     * @return transformed name
     */
    private String transformFileName(String file) {
        String fileName = UUID.randomUUID().toString();
        String extension = "";

        int i = file.lastIndexOf('.');
        if (i > 0) {
            extension = file.substring(i + 1);
        }

        fileName = fileName + "." + extension;
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

        FileUtils.copyFile(sourceFile, targetDir);
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
    public String getAllSubmissionList(HttpServletRequest request, @RequestParam("courseId") String courseId,
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
    public String getStudentSubmissionList(HttpServletRequest request, @RequestParam("studentUsername") String studentUsername,
                                       Model model) {
        List<Submission> submissionList = submissionService.getSubmissionByUsername(studentUsername);
        model.addAttribute("submissions", submissionList);

        return "submission-list";
    }
}
