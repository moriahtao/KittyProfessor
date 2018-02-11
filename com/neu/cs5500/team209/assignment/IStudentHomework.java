package com.neu.cs5500.team209.assignment;

/**
 * Represent individual student work. Contains List of files.
 *
 * This interface helps in functional testing
 * using mockito or similar library.
 *
 * @author team-209
 */
public interface IStudentHomework extends IAssignment {

    /**
     * Changes the name of the student work.
     * @param name new name to be changed to.
     * @return return the student work with updated name.
     */
    public IStudentHomework rename(String name);

}
