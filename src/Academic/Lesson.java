package Academic;
import Users.Teacher;
import Enums.Faculty;
import Enums.LessonType;



import java.util.Date;

public class Lesson {
	private String name;
	private Teacher teacher;
	private Faculty faculty;
	private LessonType lessonType;
	private Date date;

	public Lesson(String name, Teacher teacher, Faculty faculty, LessonType lessonType, Date date) {
		this.name = name;
		this.teacher = teacher;
		this.faculty = faculty;
		this.lessonType = lessonType;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public LessonType getLessonType() {
		return lessonType;
	}

	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Lesson [name=" + name + ", teacher=" + teacher + ", faculty=" + faculty +
				", lessonType=" + lessonType + ", date=" + date + "]";
	}
}