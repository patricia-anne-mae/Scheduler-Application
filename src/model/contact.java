package model;

public class contact {
    private int ID;
    private String name;
    private String email;

    /**
     * Contact constructor.
     * @param ID
     * @param name
     * @param email
     */
    public contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for contact ID.
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * Setter for contact ID.
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Getter for contact name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for contact name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for contact email.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for contact email.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
