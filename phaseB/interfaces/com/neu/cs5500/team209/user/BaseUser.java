package com.neu.cs5500.team209.user;

import com.neu.cs5500.team209.Comparator.ScoreDetails;
import com.neu.cs5500.team209.assignment.IAssignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic user functionalities are implemented here.
 * Anonymous user can perform the following functionalities.
 */
public class BaseUser implements IBaseUser {

    String id;

    public BaseUser(String id) {
        this.id = id;
    }
    /**
     * A method for Drag and drop folder is not needed,
     * as the functionality is handled by uploadFolder
     * itself. This list will be empty initially and will
     * be updated once the user uploads the list of assignments
     * or single assignment.
     *
     * @return List of folders containing students work.
     */
    public List<IAssignment> uploadFolder() {
        //to be implemented
        return new ArrayList<>();
    }

    /**
     * Lets user download the generated report.
     * @return true if the download is successful, false otherwise.
     */
    public boolean downloadReport() {
        //to be implemented
        return false;
    }

    /**
     * This comparision is for all the folders which are uploaded.
     * not for individual files which will be done in FileComparator.
     * Though it may internally use the FileComparator for comparing.
     * @return Score details containing score and similar lines
     * between all the files.
     */
    public List<ScoreDetails> compareFiles() {
        //to be implemented
        return new ArrayList<>();
    }

    /**
     * deletes the uploaded folders, and allows user to upload again.
     * @return true if successfully able to delete, false otherwise.
     */
    public boolean reset() {
        //to be implemented
        return false;
    }

    /**
     * Provides additional option to add new homework folder.
     * @return updated list of HomeworkFolder.
     */
    public List<IAssignment> addStudentHW() {
        //to be implemented
        return new ArrayList<>();
    }


}
