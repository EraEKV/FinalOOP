package Test;

import Academic.Attestation;
import Academic.AttestationMark;
import Academic.Course;
import Academic.SemesterPeriod;
import Database.Database;
import Enums.Faculty;
import Enums.Semester;
import Enums.TeacherType;
import Menu.AdminMenu;
import Menu.ManagerMenu;
import System.Credentials;
import Users.Admin;
import Users.Manager;
import Users.Student;

import System.UniversitySystemMediator;
import System.CustomPair;
import Users.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestCredentials {
    public static void main(String[] args) {
//        Credentials c = new Credentials();
//
//        String res = c.generateHash("foerwkof");
//        System.out.println(res);


        System.out.println(Credentials.generateHash("Am9OCTI"));


        System.out.println(Database.getInstance().getUsers());
        Admin admin = new Admin();
        AdminMenu menu = new AdminMenu(admin);

        menu.displayMenu();

        try {
            Database.write();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Teacher t = new Teacher("fwefref", "e", "kokenov", TeacherType.PROFESSOR, Faculty.SITE);
        String email = Credentials.generateEmail("e", "kokenov", t.getClass().getSimpleName());
        System.out.println(email);
//        Manager manager = new Manager("er", "rgiore", "fijreio", "ekrfkref");
//
//        ManagerMenu menu = new ManagerMenu(manager, new UniversitySystemMediator());
//        menu.displayMenu();

    }
}
