package Menu;

import Database.Database;
import Users.Admin;
import Users.Rector;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestMenu {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(Database.getInstance().getUsers());


//        AdminMenu adminMenu = new AdminMenu(new Admin(), br);
//        adminMenu.displayMenu();

//        System.out.println(Database.getInstance().getUsers());
//

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();

//        RectorMenu menu = new RectorMenu(Rector.getInstance(), br);
//        menu.displayMenu();
    }
}
