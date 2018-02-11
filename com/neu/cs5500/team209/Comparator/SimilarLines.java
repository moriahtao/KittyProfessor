package com.neu.cs5500.team209.Comparator;

import java.util.List;

/**
 * class for representing similar lines between two files.
 * Right now it is just a list of integers.
 * Implementation can be updated later.
 *
 */
public class SimilarLines {

    /**
     * Line numbers of similar lines from file1.
     */
    List<Integer> similarInFile1;

    /**
     * Line numbers of similar lines from file2.
     */
    List<Integer> similarInFile2;

    /**
     * Constructor.
     * @param sf1 lines in file 1
     * @param sf2 lines in file 2
     */
    public SimilarLines(List<Integer> sf1, List<Integer>sf2) {
        this.similarInFile1 = sf1;
        this.similarInFile2 = sf2;
    }

}
