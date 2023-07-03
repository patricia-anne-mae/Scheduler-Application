package model;

public class user {
    private final int userID;
    private final String userName;

    /**
     * Constructor for user
     * @param userID
     * @param userName
     */
    public user(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * Getter for user ID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for username
     * @return
     */
    public String getUserName() {
        return userName;
    }
}