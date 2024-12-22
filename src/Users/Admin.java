package Users;

import Database.Database;
import System.Credentials;
import java.util.Objects;

/**
 * The Admin class represents an administrator user who has special privileges
 * such as adding, deleting users, viewing logs, and updating user information.
 * It extends the User class and provides methods to manage users and perform admin tasks.
 */
public class Admin extends User {


    public Admin() {
        super();
    }

    public Admin(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }

    /**
     * Adds a user to the system. This method can be used by an admin to add a new user.
     * @param user The User object representing the user to be added.
     */
    public void addUser(User user) {
        System.out.println("User added: " + user.getFirstname() + " " + user.getLastname());
    }

    /**
     * Deletes a user from the system based on their email.
     * This method interacts with the Database instance to delete the user from the database.
     * @param email The email address of the user to be deleted.
     */
    public void deleteUser(String email) {
        Credentials credentials = new Credentials(email);
        Database.getInstance().deleteUser(credentials);
    }

    /**
     * Deletes a user from the system based on the User object.
     * This method interacts with the Database instance to delete the user from the database.
     * @param user The User object representing the user to be deleted.
     */
    public void deleteUser(User user) {
        Credentials credentials = new Credentials(user.getEmail());
        Database.getInstance().deleteUser(credentials);
    }

    /**
     * Views the logs stored in the database.
     * This method allows the admin to view system logs for auditing purposes.
     */
    public void viewLogs() {
        System.out.println(Database.getInstance().getLogs());
    }

    @Override
    public String toString() {
        return "ADMIN";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getEmail());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Admin admin = (Admin) obj;
        return Objects.equals(getFirstname(), admin.getFirstname()) &&
                Objects.equals(getLastname(), admin.getLastname()) &&
                Objects.equals(getEmail(), admin.getEmail());
    }
}
