package com.neu.cs5500.team209.user;

/**
 * All the functionality which can be performed by the user
 * are handled here.
 * So Basically this page acts as backend co-ordinates with other
 * classes.
 *
 * @author team-209
 */

public class User extends BaseUser {

    // User email id for the user, will be null for anonymous user
    public String email = null;
    // User password for the account, will be null for anonymous user
    public String password = null;
    // User firstName will be null for anonymous user
    public String firstName = null;
    // User lastName will be null for anonymous user
    public String lastName = null;

    /**
     *
     * @return
     */
    public boolean signIn() {
        return false;
    }

    /**
     *
     * @return
     */

    public boolean signOut() {
        return false;
    }

    /**
     *
     * @return
     */

    public boolean signUp() {
        return false;
    }



    /**
     *
     * @param desc
     * @return
     */

    public boolean saveReport(String desc) {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean viewSavedReport() {
        return false;
    }



}
