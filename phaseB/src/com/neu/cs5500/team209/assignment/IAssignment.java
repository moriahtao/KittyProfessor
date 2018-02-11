package com.neu.cs5500.team209.assignment;


import java.util.List;

/**
 * Common interface which is needed for uploads.
 * Enforces common functionality to be available for
 * assignments, students work inside assignments,
 * files in student work.
 *
 * This interface helps in functional testing
 * using mockito or similar library.
 */
public interface IAssignment {

    /**
     * Delete an assignment Folder or
     * student work or file inside student work.
     * @return True if successfully deleted, false otherwise.
     */
    public boolean delete();

    /**
     *
     * Add an assignment or student work to the existing work.
     * @param ad This corresponds to single entity of file or folder.
     * @return True if successfully added, false otherwise.
     */
    public boolean add(IAssignment ad);

    /**
     * Add multiple assignments or student work to the existing work.
     * @param ads This corresponds to multiple entities of files or folders.
     * @return True if successfully added, false otherwise.
     */
    public boolean addMultiple(List<IAssignment> ads);

}
