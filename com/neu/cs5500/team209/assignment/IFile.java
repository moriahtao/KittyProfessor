package com.neu.cs5500.team209.assignment;

import java.util.List;

/**
 * Lowest entity in the assignment category.
 * We are not providing the functionality of
 * renaming individual files.
 *
 *  This interface helps in functional testing
 *  using mockito or some other library.
 *
 * @author team-209
 */
public abstract class IFile implements IAssignment {

    /**
     * updates the file required parameter which
     * indicates whether the file should be included
     * in calculating similarity score or not.
     * @param newValue new value to be updated as.
     */
    public abstract void updateRequired(boolean newValue);

    /**
     * Will not perform any action for File class.
     * @param ad This corresponds to single entity of file or folder.
     * @return true if successfully added, false otherwise
     */
    @Override
    public boolean add(IAssignment ad) {
        return false;
    }

    /**
     * Will not perform any action for File class.
     * @param ads This corresponds to multiple entities of files or folders.
     * @return true if successfully added, false otherwise
     */
    @Override
    public boolean addMultiple(List<IAssignment> ads) {
        return false;
    }

}
