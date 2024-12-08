package Academic;

import java.util.Vector;
import Enums.Faculty;
import Enums.Speciality;
import Users.Teacher;
import Enums.Semester;
import Enums.Years;

public class Course {
    private String code;
    private String name;
    private Faculty faculty;
    private Speciality speciality;
    private Vector<Teacher> instructors;
    private Vector<Teacher> teachers;
    private int credits;
    private Semester semester;
    private Years year;

    public Course(String code, String name, Faculty faculty, Speciality speciality, int credits, Semester semester, Years year) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.speciality = speciality;
        this.instructors = new Vector<>();
        this.teachers = new Vector<>();
        this.credits = credits;
        this.semester = semester;
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Vector<Teacher> getInstructors() {
        return instructors;
    }

    public void addInstructor(Teacher instructor) {
        this.instructors.add(instructor);
    }

    public Vector<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Years getYear() {
        return year;
    }

    public void setYear(Years year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Course [code=" + code + ", name=" + name + ", faculty=" + faculty +
                ", speciality=" + speciality + ", credits=" + credits +
                ", semester=" + semester + ", year=" + year + "]";
    }
}