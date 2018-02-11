package com.neu.cs5500.team209.Comparator;

import com.neu.cs5500.team209.assignment.IFile;

/**
 * Implementation of IFileComparator.
 * File comparator helps in comparing files and giving a score for
 * the set of files.
 * @author team-209
 */
public class FileComparator implements IFileComparator {

    /**
     * All the comparisons happen in round robin, so there will
     * never be a case we will need to compare more than two files.
     * @param file1 file to compare
     * @param file2 another file to compare
     * @return similarity score.
     */
    @Override
    public float compareFiles(IASTGenerator ast1, IASTGenerator ast2) {
        //to be implemented
        return 0;
    }

    /**
     * Compares and returns line numbers of files which are similar.
     * @param file1 file to compare
     * @param file2 another file to compare
     * @return similar lines object
     */
    @Override
    public SimilarLines getSimilarLines(IASTGenerator ast1, IASTGenerator ast2) {
        //to be implemented later
        return null;
    }
}
