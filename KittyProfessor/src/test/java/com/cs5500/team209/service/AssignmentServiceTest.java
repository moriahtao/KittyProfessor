package com.cs5500.team209.service;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.Submission;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.model.dto.UpdateSubmissionResult;
import com.cs5500.team209.repository.AssignmentRepository;
import com.cs5500.team209.repository.UserRepository;
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
public class AssignmentServiceTest {

    @TestConfiguration
    static class AssignmentServiceTestContextConfiguration {

        @Bean
        public AssignmentService assignmentService() {
            return new AssignmentService();
        }
    }
    @Autowired
    private AssignmentService assignmentService;

    @MockBean
    private AssignmentRepository assignmentRepository;

    @Before
    public void setUp() {
        List<Assignment> assignmentList = new ArrayList<>();
        Assignment assignment = new Assignment();
        assignment.setAssignmentId("assignmentId");
        assignment.setCourseId("courseId");
        assignment.setName("assignmentName");
        assignment.setUserName("username");
        assignmentList.add(assignment);
        assignmentRepository.save(assignment);
        Mockito.when(assignmentRepository.findAssignmentByAssignmentId(assignment.getAssignmentId())).thenReturn(assignment);
        Mockito.when(assignmentRepository.save(Mockito.any(Assignment.class))).thenReturn(assignment);
        Mockito.when(assignmentRepository.findAssignmentsByCourseId(assignment.getCourseId())).thenReturn(assignmentList);
    }

    @Test
    public void createAssignment() {
        Assignment assignment1 =  new Assignment();
        assignment1.setThreshold(70);
        assignment1.setCourseId("CS5500");
        assignment1.setName("structural patterns");
        assignmentRepository.save(assignment1);
        Mockito.when(assignmentRepository.save(Mockito.any(Assignment.class))).thenReturn(assignment1);
        UpdateAssignmentResult found = assignmentService.createAssignment(assignment1);
        assignmentService.getAssignmentsForCourse(assignment1.getCourseId());
    }

    @Test
    public void whenValidAssignmentId_thenShouldBeDeleted() {
        assignmentService.deleteAssignment("assignmentId");
        assertEquals(0, assignmentRepository.findAll().size());
    }

    @Test
    public void whenValidUsername_thenShouldBeDeleted() {
        assignmentService.deleteAssignmentByUserName("username");
        assertEquals(0, assignmentRepository.findAll().size());
    }


    /**
     * should return user after successfully create user
     */
    @Test
    public void wheninValidAssignment_thenErrorsShouldBeFound() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentId("assignmentId");
        assignment.setCourseId("courseId");
        assignment.setUserName("username");
        UpdateAssignmentResult result = assignmentService.createAssignment(assignment);
        assertEquals(1, result.getErrorMessages().size());
    }

    /**
     * should return user after successfully create user
     */
    @Test
    public void whenValidAssignmentId_thenAssignmentShouldBeReturned() {
        Assignment assignment = new Assignment();
        assignment.setAssignmentId("assignmentId");
        assignment.setCourseId("courseId");
        assignment.setUserName("username");
        Assignment result = assignmentService.getAssignmentById(assignment.getAssignmentId());
        assertEquals("courseId", result.getCourseId());
    }

}