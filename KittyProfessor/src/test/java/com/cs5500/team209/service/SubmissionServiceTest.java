package com.cs5500.team209.service;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Role;
import com.cs5500.team209.model.Submission;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.model.dto.UpdateSubmissionResult;
import com.cs5500.team209.model.dto.UpdateUserResult;
import com.cs5500.team209.repository.SubmissionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Driver.class)
public class SubmissionServiceTest {

    @TestConfiguration
    static class SubmissionServiceTestContextConfiguration {

        @Bean
        public SubmissionService submissionService() {
            return new SubmissionService();
        }
    }

    @Autowired
    private SubmissionService submissionService;

    @MockBean
    private SubmissionRepository submissionRepository;

    /**
     * setup mock db before testing
     */
    @Before
    public void setUp() {
        List<String> paths = new ArrayList<>();
        paths.add("admin");
        Submission submission = new Submission("1", "alex", paths);
        submissionRepository.save(submission);
        Mockito.when(submissionRepository.findSubmissionBySubmissionId(submission.getSubmissionId())).thenReturn(submission);
        //Mockito.when(userRepository.findByUsername("joe")).thenReturn(null);
        Mockito.when(submissionRepository.save(Mockito.any(Submission.class))).thenReturn(submission);


    }

    @Test
    public void createSubmission() {
        Submission submission =  new Submission();
        submission.setSubmissionNum(5);
        submission.setSubmissionId("100");
        submission.setAssignmentId("assignment1");
        submission.setUsername("username1");
        submissionRepository.save(submission);
        Mockito.when(submissionRepository.save(Mockito.any(Submission.class))).thenReturn(submission);
        //UpdateSubmissionResult found = submissionService.createSubmission(submission);
        submissionService.getSubmissionsForAssignment("assignment1","username1");
        submissionService.getOtherStudentSubmissions("100","username1");
        submissionService.getSubmissionById("100");
        //UpdateSubmissionResult f = submissionService.addFileToSubmission("result",submission);
        Mockito.when(submissionRepository.findSubmissionBySubmissionId("joe")).thenReturn(null);
    }

    /**
     * should return user after successfully create user
     */
    @Test
    public void whenValidSubmission_thenSubmissionShouldBeFound() {
        List<String> paths = new ArrayList<>();
        paths.add("admin");
        Submission submission = new Submission("1", "alex", paths);
        UpdateSubmissionResult result = submissionService.createSubmission(submission);
        assertEquals("alex", result.getSubmission().getUsername());
    }


    /**
     * should return user after successfully create user
     */
    @Test
    public void whenValidUpdateSubmission_thenSubmissionShouldBeFound() {
        List<String> paths = new ArrayList<>();
        paths.add("admin");
        Submission submission = new Submission("1", "alex", paths);
        UpdateSubmissionResult result = submissionService.addFileToSubmission("admin", submission);
        assertEquals("alex", result.getSubmission().getUsername());
    }
    
}