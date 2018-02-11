package com.neu.cs5500.team209.assignment;

import java.util.List;

public class HomeworkFolder implements IHomeworkFolder {

    /**
     * Class variables.
     * There can be a space in the user given name.
     * There will not be any space in the id.
     */
    private String id;
    private String name;
    private List<IFile> files;

    /**
     * @param id id of the homework folder
     * @param name name of the folder can contain space
     * @param files List of all the files in the folder.
     */
    public HomeworkFolder(String id, String name, List<IFile> files) {
        this.id = id;
        this.name = name;
        this.files = files;
    }

    /**
     *
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
     *
     * @param ad This corresponds to single entity of file or folder.
     * @return
     */
    @Override
    public boolean add(IAssignment ad) {
        //to be implemented later
        return false;
    }

    /**
     *
     * @param ads This corresponds to multiple entities of files or folders.
     * @return
     */
    @Override
    public boolean addMultiple(List<IAssignment> ads) {
        //to be implemented later
        return false;
    }
}
