package Academic;

import Enums.Faculty;
import Enums.Semester;
import Enums.Speciality;
import Enums.Years;
import Users.Teacher;

import java.util.Vector;

public class Course {
    private String code;
    private String name;
    private Faculty faculty;
    private Speciality speciality;
    private Integer credits;
    private Semester semester;
    private Years years;

    private Vector<Teacher> instructors;
    private Vector<Teacher> teachers;


    public Course() {

    }
}
