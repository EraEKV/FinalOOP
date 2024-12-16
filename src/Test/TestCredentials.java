package Test;

import Database.Database;
import Menu.AdminMenu;
import System.Credentials;
import Users.Admin;

import javax.xml.crypto.Data;

public class TestCredentials {
    public static void main(String[] args) {
//        Credentials c = new Credentials();
//
//        String res = c.generateHash("foerwkof");
//        System.out.println(res);



        System.out.println(Database.getInstance().getUsers());
        Admin admin = new Admin();
        AdminMenu menu = new AdminMenu(admin);

        menu.displayMenu();

        try {
            Database.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
