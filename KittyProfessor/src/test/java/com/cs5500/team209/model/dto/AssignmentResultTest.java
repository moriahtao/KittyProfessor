package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Assignment;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mengtao on 4/6/18.
 */
public class AssignmentResultTest {
    @Test
    public void getAssignment() {
        Assignment a = new Assignment();
        AssignmentResult ar = new AssignmentResult(a);
        assertEquals(ar.getAssignment(), a);
    }
}