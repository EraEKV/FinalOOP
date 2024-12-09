package Comparators ;

import Users.Teacher;
import java.util.Comparator;

public class TeacherRatingComparator  implements Comparator<Teacher> {
	@Override
	public int compare(Teacher o1, Teacher o2) {
		return Double.compare(o1.getRating(), o2.getRating());
	}
}

