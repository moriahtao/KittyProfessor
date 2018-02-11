package com.neu.cs5500.team209.assignment;

/**
 * A folder containing 1 to many students work.
 * Contains a list of students homework.
 *
 * This interface helps in functional testing
 * using mockito or similar library.
 *
 * @author team-209
 */
public interface IHomeworkFolder extends IAssignment {

    /**
     * Changes the name of the student work.
     * @param name new name to be changed to.
     * @return return the object with updated name.
     */
    public IHomeworkFolder rename(String name);
}
