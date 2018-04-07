package com.cs5500.team209.model.dto;

import com.cs5500.team209.model.Assignment;

/**
 * Created by mengtao on 4/6/18.
 */
public class AssignmentResult {
    private Assignment assignment;
    public AssignmentResult(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }


}
