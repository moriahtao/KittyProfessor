package com.neu.cs5500.team209.assignment;

import java.util.List;

public class HomeworkFolder implements IHomeworkFolder {

    /**
     * Class variables.
     * There can be a space in the user given name.
     * There will not be any space in the id.
     */
    String id;
    String name;
    List<IStudentHomework> shw;

    /**
     * @param id id of the homework folder
     * @param name name of the folder can contain space
     * @param shw List of all the files in the folder.
     */
    public HomeworkFolder(String id, String name, List<IStudentHomework> shw) {
        this.id = id;
        this.name = name;
        this.shw = shw;
    }

    /**
     * changes the name of HomeworkFolder
     * @param name new name to be changed to.
     * @return object with updated folder name.
     */
    @Override
    public IHomeworkFolder rename(String name) {
        //to be implemented later
        return null;
    }

    /**
     * Removes the homework folder from the storage.
     * @return true if successfully deleted.
     */
    @Override
    public boolean delete() {
        //to be implemented later
        return false;
    }

    /**
     * here IAssignment will be IStudentHomework
     * @param ad This corresponds to single entity of file or folder.
     * @return true if successfully added, false otherwise.
     */
    @Override
    public boolean add(IAssignment ad) {
        //to be implemented later
        return false;
    }

    /**
     * here IAssignment will be a list of IStudentHomework
     * @param ads This corresponds to multiple entities of files or folders.
     * @return true if successfully added, false otherwise.
     */
    @Override
    public boolean addMultiple(List<IAssignment> ads) {
        //to be implemented later
        return false;
    }
}
