package com.neu.cs5500.team209.Comparator;

import com.neu.cs5500.team209.assignment.IFile;

/**
 * Implementation of IPreprocessor.
 * This is not added as a part of FileComparator because
 * preProcessing and file comparision can happen independently.
 *
 * We can process multiple files and compare them independent of
 * each other to make it efficient.
 */
public class Preprocessor implements IPreprocessor {

    /**
     * cleans up the file based on agreed format.
     * @param file file which needs to be cleaned.
     * @return cleaned up file.
     */
    @Override
    public IFile cleanUp(IFile file) {

        return null;
    }

    /**
     * tokenizes the file.
     * @param file file which needs to be tokenized.
     * @return tokenized file.
     */
    @Override
    public IFile tokenize(IFile file) {
        return null;
    }
}
