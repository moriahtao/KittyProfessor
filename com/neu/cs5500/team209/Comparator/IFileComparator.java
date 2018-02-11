package com.neu.cs5500.team209.Comparator;

import com.neu.cs5500.team209.assignment.IFile;

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
    public float compareFiles(IASTGenerator ast1, IASTGenerator ast2);

    /**
     * At this stage we are showing only the lines which are similar
     * in both the files. Showing segment of files matching will be
     * consider as an extended feature.
     *
     * @param ast1 ast to compare
     * @param ast2 another ast to compare
     * @return SimilarLines object showing the lines which are
     * similar. In other words lines responsible for the score.
     */
    public SimilarLines getSimilarLines(IASTGenerator ast1, IASTGenerator ast2);

}
