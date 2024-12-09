package Comparators ;


import Academic.Course;
import java.util.Comparator;

public class CourseCreditComparator implements Comparator<Course> {
	@Override
	public int compare(Course o1, Course o2) {
		return Integer.compare(o1.getCredits(), o2.getCredits());
	}
}

