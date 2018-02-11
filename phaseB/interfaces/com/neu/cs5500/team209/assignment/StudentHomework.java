package com.neu.cs5500.team209.assignment;

import java.util.List;

/**
 * Student Homework represent individual student work.
 */
public class StudentHomework implements IStudentHomework {

    /**
     * Class variables are made private and accessed using
     * setters and getters.
     */
    String id;
    String name;
    List<IFile> files;

    /**
     * constructor
     * @param id id of the student homework. Can not contain spaces.
     * @param name name of the student homework.
     * @param files files inside the student homework.
     */
    public StudentHomework(String id, String name, List<IFile> files) {
        this.id = id;
        this.name = name;
        this.files = files;
    }

    /**
     * renames the Student homework.
     * @param name new name to be changed to.
     * @return object with updated name.
     */
    @Override
    public IStudentHomework rename(String name) {
        //to be implemented later
        return null;
    }

    /**
     * deletes storage used for student homework.
     * @return true if successfully deleted false otherwise.
     */
    @Override
    public boolean delete() {
        //to be implemented later
        return false;
    }

    /**
     * Here IAssignment will be IFiles.
     * @param ad This corresponds to single entity of file or folder.
     * @return true if successfully added false otherwise.
     */
    @Override
    public boolean add(IAssignment ad) {
        //to be implemented later
        return false;
    }

    /**
     * Here IAssignment will be IFiles.
     * @param ads This corresponds to multiple entities of files or folders.
     * @return true if successfully added false otherwise.
     */
    @Override
    public boolean addMultiple(List<IAssignment> ads) {
        //to be implemented later
        return false;
    }
}
