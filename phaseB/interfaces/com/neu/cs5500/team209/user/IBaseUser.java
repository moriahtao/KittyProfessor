package com.neu.cs5500.team209.user;


import com.neu.cs5500.team209.Comparator.ScoreDetails;
import com.neu.cs5500.team209.assignment.IAssignment;
import com.neu.cs5500.team209.assignment.IHomeworkFolder;

import java.util.List;

/**
 * Interface for basic user functionalities are implemented here.
 * Anonymous user can perform the following functionalities.
 *
 * This interface helps in functional testing
 * using mockito or similar library.
 * @author team-209
 */
public interface IBaseUser {

    /**
     * A method for Drag and drop folder is not needed,
     * as the functionality is handled by uploadFolder
     * itself. This list will be empty initially and will
     * be updated once the user uploads the list of assignments
     * or single assignment.
     *
     * @return List of folders containing students work.
     */
    public List<IAssignment> uploadFolder();

    /**
     * Lets user download the generated report.
     * @return true if the download is successful, false otherwise.
     */
    public boolean downloadReport();


    /**
     * This comparision is for all the folders which are uploaded.
     * not for individual files which will be done in FileComparator.
     * Though it may internally use the FileComparator for comparing.
     *
     * @return Score details containing score and similar lines
     * between all the files.
     */
    public List<ScoreDetails> compareFiles();

    /**
     * deletes the uploaded folders, and allows user to upload again.
     * @return true if successfully able to delete, false otherwise.
     */
    public boolean reset();

    /**
     * Provides additional option to add new homework folder.
     *
     * @return updated list of HomeworkFolder.
     */
    public List<IAssignment> addStudentHW();

}
