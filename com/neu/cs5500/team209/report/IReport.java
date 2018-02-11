package com.neu.cs5500.team209.report;

import com.neu.cs5500.team209.assignment.IFile;
import com.neu.cs5500.team209.assignment.IStudentHomework;

/**
 *
 * This interface helps in functional testing
 * using mockito or similar library.
 * @author team-209
 */
public interface IReport {

    /**
     * Score between two homeworks
     * @param hw1 homework1
     * @param hw2 homework2
     * @return score value of similarity between the home works
     */
    public float getScore(IStudentHomework hw1, IStudentHomework hw2);

    /**
     * Score between two files
     * @param file1 file1
     * @param file2 file2
     * @return score value of similarity between two files.
     */
    public float getScore(IFile file1, IFile file2);
}
