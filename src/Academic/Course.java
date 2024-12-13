package Academic;

import java.util.Objects;
import java.util.Vector;
import Enums.Faculty;
import Enums.Speciality;
import Users.Student;
import Users.Teacher;
import Enums.Semester;

public class Course {
    private String code;
    private String name;
    private Faculty faculty;
    private Speciality speciality;
    private Vector<Teacher> instructors;
    private Vector<Teacher> teachers;
    private Vector<Student> students;
    private int credits;
    private Semester semester;

    public Course() {
        this.instructors = new Vector<>();
        this.teachers = new Vector<>();
        this.students = new Vector<>();
    }

    public Course(String code, String name, Faculty faculty, Speciality speciality, int credits, Semester semester) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.speciality = speciality;
        this.instructors = new Vector<>();
        this.teachers = new Vector<>();
        this.students = new Vector<>();
        this.credits = credits;
        this.semester = semester;
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

    public Vector<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Course [code=" + code + ", name=" + name + ", faculty=" + faculty +
                ", speciality=" + speciality + ", credits=" + credits +
                ", semester=" + semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, faculty, speciality, instructors, teachers, students, credits, semester);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return credits == course.credits &&
                Objects.equals(code, course.code) &&
                Objects.equals(name, course.name) &&
                faculty == course.faculty &&
                speciality == course.speciality &&
                Objects.equals(instructors, course.instructors) &&
                Objects.equals(teachers, course.teachers) &&
                Objects.equals(students, course.students) &&
                semester == course.semester;
    }
}