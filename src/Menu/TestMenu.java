package Menu;

import Database.Database;
import Users.Admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestMenu {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//        AdminMenu adminMenu = new AdminMenu(new Admin(), br);
//        adminMenu.displayMenu();

        System.out.println(Database.getInstance().getUsers());

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();


    }
}
