package com.neu.cs5500.team209.report;

import com.neu.cs5500.team209.assignment.IAssignment;
import com.neu.cs5500.team209.assignment.IFile;
import com.neu.cs5500.team209.assignment.IStudentHomework;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for storing the score values for the files.
 *
 * Implements IReport interface.
 */
public class Report implements IReport {

    String id;
    /**
     * scores parameter will have the
     */
    private Map<Map<IAssignment, IAssignment>, Float> scores =
            new HashMap<>();

    /**
     * Constructor for reporter
     * @param id id of the report associated with the assignment
     * @param scores scores of all the files inside.
     */
    public Report(String id, Map<Map<IAssignment, IAssignment>, Float> scores) {
        this.id = id;
        this.scores = scores;
    }

    /**
     * Score between two homeworks
     * @param hw1 homework1
     * @param hw2 homework2
     * @return score value of similarity between the home works
     */
    @Override
    public float getScore(IStudentHomework hw1, IStudentHomework hw2) {
        //to be implemented
        return 0;
    }

    /**
     * Score between two files
     * @param file1 file1
     * @param file2 file2
     * @return score value of similarity between two files.
     */
    @Override
    public float getScore(IFile file1, IFile file2) {
        //to be implemented
        return 0;
    }
}
