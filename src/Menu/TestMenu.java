package Menu;

import Database.Database;
import Users.Admin;
import Users.Rector;
import Users.Student;
import Users.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestMenu {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Database db = Database.getInstance();

        System.out.println(Database.getInstance().getUsers());


//        AdminMenu adminMenu = new AdminMenu(new Admin(), br);
//        adminMenu.displayMenu();

//        System.out.println(Database.getInstance().getUsers());
//

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();

//        User user = db.findUserByEmail("e_kokenov@kbtu.kz");
//        Student student = (Student) user;
//        System.out.println(student.getRegisteredCourses());

//        RectorMenu menu = new RectorMenu(Rector.getInstance(), br);
//        menu.displayMenu();
    }
}
