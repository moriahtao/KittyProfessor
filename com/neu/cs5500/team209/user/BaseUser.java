package com.neu.cs5500.team209.user;

import com.neu.cs5500.team209.assignment.IAssignment;

import java.util.List;

public class BaseUser {

    String id;

    /**
     *
     * A method for Drag and drop folder is not needed,
     * as the functionality is handled by uploadFolder
     * itself.
     *
     * @return true if the upload is successful.
     */
    public boolean uploadFolder() {
        //to be implemented
        return false;
    }

    /**
     *
     * @return true if the download is successful, false otherwise.
     */
    public boolean downloadReport() {
        //to be implemented
        return false;
    }

    /**
     *
     * @return
     */
    public boolean compareFiles() {
        //to be implemented
        return false;
    }

    /**
     *
     * @return
     */
    public boolean reset() {
        //to be implemented
        return false;
    }

    /**
     *
     * @return
     */
    public List<IAssignment> addStudentHW() {
        //to be implemented
        return null;
    }


}
