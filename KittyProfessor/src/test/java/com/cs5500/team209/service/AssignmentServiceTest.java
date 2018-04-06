package com.cs5500.team209.service;

import com.cs5500.team209.Driver;
import com.cs5500.team209.model.Assignment;
import com.cs5500.team209.model.User;
import com.cs5500.team209.model.dto.UpdateAssignmentResult;
import com.cs5500.team209.repository.AssignmentRepository;
import com.cs5500.team209.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void createAssignment() {
        Assignment assignment1 =  new Assignment();
        assignment1.setThreshold(70);
        assignment1.setCourseID("CS5500");
        assignment1.setName("structural patterns");
        assignmentRepository.save(assignment1);
        Mockito.when(assignmentRepository.save(Mockito.any(Assignment.class))).thenReturn(assignment1);
        UpdateAssignmentResult found = assignmentService.createAssignment(assignment1);
        assignmentService.getAssignmentsForCourse(assignment1.getCourseID());
    }
}