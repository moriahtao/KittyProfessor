package com.neu.cs5500.team209.user;

/**
 * All the functionality which can be performed by the logged
 * in user are handled here.
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

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**
     * perform the sign in action of the user
     * @return true if successful, false otherwise
     */
    public boolean signIn() {
        return false;
    }

    /**
     * performs the sign out action for the user
     * @return true if successful, false otherwise
     */

    public boolean signOut() {
        return false;
    }

    /**
     * User registration is handled here.
     * @return true if successful, false otherwise
     */

    public boolean signUp() {
        return false;
    }

    /**
     * Saves the report for the user.
     * @param desc name of the report, desc can contain spaces.
     * @return true if successful, false otherwise
     */

    public boolean saveReport(String desc) {
        return false;
    }

}
