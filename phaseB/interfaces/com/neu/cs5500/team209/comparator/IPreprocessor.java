package com.neu.cs5500.team209.Comparator;

import com.neu.cs5500.team209.assignment.IFile;

/**
 * This is not added as a part of IFileComparator because
 * preProcessing and file comparision can happen independently.
 * We can process multiple files and compare them independent of
 * each other to make it efficient.
 *
 *  This interface helps in functional testing
 *  using mockito or a similar library.
 */

public interface IPreprocessor {

    /**
     * cleans up the file based on agreed format.
     * @param file file which needs to be cleaned.
     * @return cleaned up file.
     */
    public IFile cleanUp(IFile file);

    /**
     * tokenizes the file.
     * @param file file which needs to be tokenized.
     * @return tokenized file.
     */
    public IFile tokenize(IFile file);
}
