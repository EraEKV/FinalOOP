package Menu;

import Database.Database;
import Enums.*;
import Research.*;
import System.*;
import Users.*;
import Academic.*;

import java.io.IOException;

public class TestData {

    public static void main(String[] args) {
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
        Credentials c2 = new Credentials("e_kokenov@kbtu.kz", "123");


        Teacher t = factory.createUser("Pakizar", "Shamoi", TeacherType.PROFESSOR, Faculty.SITE);
        Credentials c3 = new Credentials("p.shamoi@kbtu.kz", "123");
        t.setEmail("p.shamoi@kbtu.kz");

        Manager m = factory.createUser("some", "guy");
        Credentials c4 = new Credentials("s_guy@kbtu.kz", "123");
        m.setEmail("s_guy@kbtu.kz");

        User ms = factory.createUser("Alidar", "Panaguzhiyev", Faculty.SAM, Speciality.MCM, MasterStudent.class.getSimpleName());
        ms = (MasterStudent) ms;
        ms.setEmail("a_panaguzhiyev@kbtu.kz");
        Credentials c5 = new Credentials("a_panaguzhiyev@kbtu.kz", "123");


        User phd = factory.createUser("Alidar", "Panaguzhiyev", Faculty.SAM, Speciality.MCM, PhDStudent.class.getSimpleName());
        phd = (PhDStudent) phd;
        phd.setEmail("a_panaguzhiyevpro@kbtu.kz");
        Credentials c6 = new Credentials("a_panaguzhiyev@kbtu.kz", "123");



        db.getUsers().put(c1, admin);
        db.getUsers().put(c2, s);
        db.getUsers().put(c3, t);
        db.getUsers().put(c4, m);
        db.getUsers().put(c5, ms);
        db.getUsers().put(c6, phd);

        db.getNews().add(new News("Rector", "New VSP", "Welcome to our new University system VSP 2.0 with rickroll :D", NewsTopic.ANNOUNCMENTS));

        Course cr1 = new Course("CSCI1201", "Principle Programming 1", Faculty.SITE, 5, Semester.FALL);
        Course cr2 = new Course("CSCI1204", "Principles of Programming 1", Faculty.SITE, 5, Semester.FALL);
        Course cr3 = new Course("CSCI1205", "Introduction to Programming", Faculty.SITE, 5, Semester.FALL);
        Course cr4 = new Course("MATH1010", "Calculus 1", Faculty.SITE, 5, Semester.SPRING);
        Course cr5 = new Course("PHYS1020", "Physics for Engineers", Faculty.SEOG, 5, Semester.SPRING);
        Course cr6 = new Course("ENG101", "English Composition", Faculty.SITE, 5, Semester.FALL);

        db.getCourses().add(cr1);
        db.getCourses().add(cr2);
        db.getCourses().add(cr3);
        db.getCourses().add(cr4);
        db.getCourses().add(cr5);
        db.getCourses().add(cr6);


        s.beResearcher(); // student now is Researcher

        ResearchJournal times = new ResearchJournal(ResearchJournalsName.TIMES);
        ResearchJournal lancet = new ResearchJournal(ResearchJournalsName.LANCET);
        ResearchJournal kbtuJournal = new ResearchJournal(ResearchJournalsName.KBTU);

        db.getResearchJournals().add(times);
        db.getResearchJournals().add(lancet);
        db.getResearchJournals().add(kbtuJournal);






//      // DisciplinaryCommittee
        Credentials dc = new Credentials("dc", "123");
        db.getUsers().put(dc, DisciplinaryCommittee.getInstance());



        // Добавляем новых преподавателей
        Teacher t1 = factory.createUser("Nursultan", "Tulegenov", TeacherType.PROFESSOR, Faculty.SITE);
        t1.setEmail("n.tulegenov@kbtu.kz");
        Credentials c7 = new Credentials("n.tulegenov@kbtu.kz", "123");

        Teacher t2 = factory.createUser("Altynai", "Zhaksymbetova", TeacherType.SENIOR_LECTOR, Faculty.SITE);
        t2.setEmail("a.zhaksymbetova@kbtu.kz");
        Credentials c8 = new Credentials("a.zhaksymbetova@kbtu.kz", "123");

        Teacher t3 = factory.createUser("Marat", "Saparov", TeacherType.PROFESSOR, Faculty.SITE);
        t3.setEmail("m.saparov@kbtu.kz");
        Credentials c9 = new Credentials("m.saparov@kbtu.kz", "123");

        Teacher t4 = factory.createUser("Assem", "Mukanova", TeacherType.TUTOR, Faculty.SITE);
        t4.setEmail("a.mukanova@kbtu.kz");
        Credentials c10 = new Credentials("a.mukanova@kbtu.kz", "123");

        Teacher t5 = factory.createUser("Dauren", "Nurmaganbetov", TeacherType.LECTOR, Faculty.SEOG);
        t5.setEmail("d.nurmaganbetov@kbtu.kz");
        Credentials c11 = new Credentials("d.nurmaganbetov@kbtu.kz", "123");

        Teacher t6 = factory.createUser("Aigerim", "Toktarova", TeacherType.LECTOR, Faculty.SITE);
        t6.setEmail("a.toktarova@kbtu.kz");
        Credentials c12 = new Credentials("a.toktarova@kbtu.kz", "123");


        db.getUsers().put(c7, t1);
        db.getUsers().put(c8, t2);
        db.getUsers().put(c9, t3);
        db.getUsers().put(c10, t4);
        db.getUsers().put(c11, t5);
        db.getUsers().put(c12, t6);


        cr1.assignTeacher(t1);
        cr1.assignTeacher(t2);

        cr2.assignTeacher(t3);
        cr2.assignTeacher(t4);

        cr3.assignTeacher(t1);
        cr3.assignTeacher(t6);

        cr4.assignTeacher(t2);
        cr4.assignTeacher(t5);

        cr5.assignTeacher(t3);
        cr5.assignTeacher(t5);

        cr6.assignTeacher(t4);
        cr6.assignTeacher(t6);

        System.out.println("Courses and their assigned teachers:");
        for (Course course : db.getCourses()) {
            System.out.println(course.getName() + ": " + course.getTeachers());
        }



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
