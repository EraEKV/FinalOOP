package Menu;

import Database.Database;
import Enums.Faculty;
import Enums.NewsTopic;
import Enums.Speciality;
import Enums.TeacherType;
import System.*;
import Users.*;

import java.io.IOException;

public class TestData {

    public static void main(String args[]) {
        Database db = Database.getInstance();
        UserFactory factory = UserFactory.getInstance();

        Rector r = Rector.getInstance();
        db.setRector(r);

        DisciplinaryCommittee committee = DisciplinaryCommittee.getInstance();
        db.setDisciplinaryCommittee(committee);

        System.out.println(db.getUsers());

        Admin admin = new Admin("krutoi", "chelik", "777");
        admin.setEmail("krutoi@kbtu.kz");
        Credentials c1 = new Credentials("777", "777");


        Student s = factory.createUser("Erasyl", "Kokenov", Faculty.SITE, Speciality.IS);
        s.setEmail("e_kokenov@kbtu.kz");
        Credentials c2 = new Credentials("e_kokenov@kbtu.kz", "1234");


        Teacher t = factory.createUser("Pakizar", "Shamoi", TeacherType.PROFESSOR, Faculty.SITE);
        Credentials c3 = new Credentials("p.shamoi@kbtu.kz", "1234");
        t.setEmail("p.shamoi@kbtu.kz");

        Manager m = factory.createUser("some", "guy");
        Credentials c4 = new Credentials("s_guy@kbtu.kz", "1234");
        m.setEmail("s_guy@kbtu.kz");

        User ms = factory.createUser("Alidar", "Panaguzhiyev", Faculty.SAM, Speciality.MCM, MasterStudent.class.getSimpleName());
        ms = (MasterStudent) ms;
        ms.setEmail("a_panaguzhiyev@kbtu.kz");
        Credentials c5 = new Credentials("a_panaguzhiyev@kbtu.kz", "1234");



        db.getUsers().put(c1, admin);
        db.getUsers().put(c2, s);
        db.getUsers().put(c3, t);
        db.getUsers().put(c4, m);
        db.getUsers().put(c5, ms);

        db.getNews().add(new News("Rector", "New WSP", "Welcome to our new University system WSP 2.0 with rickroll :D", NewsTopic.ANNOUNCMENTS));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        db.getNews().add(new News("Manager", "test1", "Welcome to our new University system WSP 2.0 with rickroll :D", NewsTopic.ANNOUNCMENTS));

        db.getUsers().put(new Credentials("gabdullin@kbtu.kz", "1234"), Rector.getInstance());

        try {
            Database.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
