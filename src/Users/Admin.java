package Users;

import Database.Database;
import Enums.UserType;
import java.util.Objects;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }

    public void addUser(User user) {
        System.out.println("User added: " + user.getFirstname() + " " + user.getLastname());
    }

    public void deleteUser(User user) {
        System.out.println("User deleted: " + user.getFirstname() + " " + user.getLastname());
    }

//    public void updateUser(User user) {
//        System.out.println("User updated: " + user.getFirstname() + " " + user.getLastname());
//    }

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