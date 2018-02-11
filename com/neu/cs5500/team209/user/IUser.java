package com.neu.cs5500.team209.user;


import com.neu.cs5500.team209.assignment.IHomeworkFolder;

import java.util.List;

/**
 *
 * @author team-209
 */
public interface IUser {

    /**
     *
     * @return
     */
    boolean signIn();

    /**
     *
     * @return
     */
    boolean signOut();

    /**
     *
     * @return
     */
    boolean signUp();

    /**
     *
     * @return
     */
    List<IHomeworkFolder> uploadFolder();

    /**
     *
     * @return
     */
    List<IHomeworkFolder> dragAndDropFolder();

    /**
     *
     * @return
     */
    boolean saveReport(String description);

    /**
     *
     * @return
     */
    void downloadReport(String id);
    /**
     *
     * @return
     */
    boolean compareFiles();

    /**
     *
     * @return
     */
    boolean reset();

    /**
     *
     * @return
     */
    boolean addStudentHomeWork();

}
