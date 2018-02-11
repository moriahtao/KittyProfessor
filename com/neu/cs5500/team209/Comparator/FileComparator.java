package com.neu.cs5500.team209.Comparator;

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
     * @param ast1 ast to compare
     * @param ast2 another ast to compare
     * @return score details for the two asts passed.
     */
    @Override
    public ScoreDetails compareFiles(IASTGenerator ast1, IASTGenerator ast2) {
        //to be implemented
        return null;
    }
}
