package com.neu.cs5500.team209.Comparator;

/**
 * File comparator helps in comparing files and giving a score for
 * the set of files.
 *
 * This interface helps in functional testing
 * using mockito or similar library.
 *
 * @author team-209
 */
public interface IFileComparator {

    /**
     * All the comparisons happen in round robin, so there will
     * never be a case we will need to compare more than two files.
     *
     * @param ast1 ast to compare
     * @param ast2 another ast to compare
     * @return score indicating whether they are similar or not.
     */
    public ScoreDetails compareFiles(IASTGenerator ast1, IASTGenerator ast2);
}
