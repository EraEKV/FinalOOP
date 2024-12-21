package Menu;

import Users.Admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestMenu {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();
        AdminMenu adminMenu = new AdminMenu(new Admin(), br);
        adminMenu.displayMenu();

    }
}
