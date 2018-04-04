package com.cs5500.team209.controller;

import com.cs5500.team209.Parser;
import com.cs5500.team209.model.Report;
import com.cs5500.team209.model.Submission;
import com.cs5500.team209.model.dto.UpdateSubmissionResult;
import com.cs5500.team209.service.ReportService;
import com.cs5500.team209.service.SubmissionService;
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
    StorageService storageService;

    @Autowired
    ReportService reportService;

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
            for (String s : otherSubmissionFilePaths) {
                String targetPath = "exercise1/target/target.zip";
                String targetFolder = "exercise1/target";
                copyIntoPath(s, targetPath);
                //extract
                unzip(targetPath, targetFolder);
                String reportPath = "dummy.html";
                reportPath = "src/main/resources/static/" + transformFileName(reportPath);
                Parser.parse(reportPath);
                // save compared report
                reportService.createReport(new Report(submissionId, submissionFileIdMap.get(s), reportPath));
                // only clean other submission folder if compare not end
                deleteTargetDirectory(targetFolder);
            }
            deleteTargetDirectory("exercise1/");
        }
    }


    /**
     * unzip zipFile into destDir
     * @param zipFilePath the file path of zip file
     * @param destDir the unzip target dir path
     * @throws IOException
     */
    private static void unzip(String zipFilePath, String destDir) throws IOException {
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
        oldfile.delete();
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
}
