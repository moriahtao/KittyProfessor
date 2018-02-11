package com.neu.cs5500.team209.assignment;

/**
 * Lowest entity in the assignment category.
 * We are not providing the functionality of
 * renaming individual files.
 *
 * extends to IFile.
 */
public class File extends IFile {

    /**
     * class variables are made private and
     * accessed using getters and setters.
     */
    String id;
    boolean required = true;
    String value;
    String name;


    /**
     * Constructor
     * @param id id of the file, can not contain space.
     * @param required required to be compared or not.
     * @param value String value of the file.
     * @param name User given name, can contain space.
     */
    public File(String id, boolean required, String value, String name) {
        this.id = id;
        this.required = required;
        this.value = value;
        this.name = name;
    }

    /**
     * This value can be updated during the pre-processing stage.
     * @param newValue new value to be updated as.
     */
    @Override
    public void updateRequired(boolean newValue) {
        //to be implemented later
    }

    /**
     * Removes the file from storage.
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean delete() {
        //to be implemented later
        return false;
    }
}
