package com.neu.cs5500.team209.assignment;

import java.util.List;

/**
 *
 */
public class StudentHomework implements IStudentHomework {

    /**
     * Class variables are made private and accessed using
     * setters and getters.
     *
     */
    String id;
    float score;
    String name;
    List<IHomeworkFolder> hws;


    /**
     *
     * @param name new name to be changed to.
     * @return
     */
    @Override
    public IStudentHomework rename(String name) {
        //to be implemented later
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean delete() {
        //to be implemented later
        return false;
    }

    /**
     * Here IAssignment will be IHomeworkFolder.
     * @param ad This corresponds to single entity of file or folder.
     * @return
     */
    @Override
    public boolean add(IAssignment ad) {
        //to be implemented later
        return false;
    }

    /**
     * Here IAssignment will be IHomeworkFolder.
     * @param ads This corresponds to multiple entities of files or folders.
     * @return
     */
    @Override
    public boolean addMultiple(List<IAssignment> ads) {
        //to be implemented later
        return false;
    }
}
