package Users ;


import Enums.UserType;

public class Admin extends User {


    public Admin(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }


    public void addUser(User user) {
        System.out.println("User added: " + user.getFirstname() + " " + user.getLastname());
    }

    public void deleteUser(User user) {
        System.out.println("User deleted: " + user.getFirstname() + " " + user.getLastname());
    }

    public void updateUser(User user) {
        System.out.println("User updated: " + user.getFirstname() + " " + user.getLastname());
    }

    public String viewLogs() {
        return "Viewing system logs...";
    }


    @Override
    public <T> T getUserType() {
        return (T) UserType.ADM;
    }

    @Override
    public String toString() {
        return "ADMIN";
    }
}