package Users ;


import Academic.Course;
import java.util.Vector;

public  interface CanViewStudents {
	public Vector<Student> viewStudentsInfo(Course parameter);
}

