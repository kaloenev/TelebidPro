package model;

import java.io.Serializable;

/**
 * JavaBean class used in jsp action tags.
 * @author Ramesh Fadatare
 */
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    private boolean isEnabled;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
