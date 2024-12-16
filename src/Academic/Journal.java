package Academic ;


import Enums.Attendance;
import Enums.LessonType;
import Enums.Semester;
import Users.Student;
import Enums.Years;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class Journal implements Serializable {

	private Course course;
	private Semester semester;
	private Years years;

	private HashMap<Student, Vector<JournalLesson>> journalData;

	public Journal() {

	}

	public Journal(Course course, Semester semester, Years years) {
		this.course = course;
		this.semester = semester;
		this.years = years;
	}


//	methods

	public void addJournalLesson(Student student, JournalLesson lesson) {
		try {
			journalData.get(student).add(lesson);
			System.out.println("Added Lesson " + lesson);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}



//	update journal
	public void updateJournalData(Student s, Date date, Attendance attendance) {
		Vector<JournalLesson> lessons = journalData.get(s);
		try {
			for (JournalLesson lesson : lessons) {
				if (lesson.getDate().equals(date)) {
					lesson.setAttendance(attendance);
					System.out.println("Updated Lesson " + lesson);
					return;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateJournalData(Student s, Date date, double mark) {
		Vector<JournalLesson> lessons = journalData.get(s);
		try {
			for (JournalLesson lesson : lessons) {
				if (lesson.getDate().equals(date)) {
					lesson.setMark(mark);
					System.out.println("Updated Lesson " + lesson);
					return;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateJournalData(Student s, Date date, String comment) {
		Vector<JournalLesson> lessons = journalData.get(s);
		try {
			for (JournalLesson lesson : lessons) {
				if (lesson.getDate().equals(date)) {
					lesson.setComment(comment);
					System.out.println("Updated Lesson " + lesson);
					return;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateJournalData(Student s, Date date, LessonType type) {
		Vector<JournalLesson> lessons = journalData.get(s);
		try {
			for (JournalLesson lesson : lessons) {
				if (lesson.getDate().equals(date)) {
					lesson.setLessonType(type);
					System.out.println("Updated Lesson " + lesson);
					return;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateJournalData(Student s, Date date, Date newDate) {
		Vector<JournalLesson> lessons = journalData.get(s);
		try {
			for (JournalLesson lesson : lessons) {
				if (lesson.getDate().equals(date)) {
					lesson.setDate(newDate);
					System.out.println("Updated Lesson " + lesson);
					return;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}


//	getters
	public Course getCourse() {
		return course;
	}

	public Semester getSemester() {
		return semester;
	}

	public Years getYears() {
		return years;
	}

	public HashMap<Student, Vector<JournalLesson>> getJournalData() {
		return journalData;
	}



	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Journal j = (Journal) o;
		return semester == j.semester
				&& years == j.years
				&& course == j.course;
	}

	@Override
	public int hashCode() {
		int res = 31;
		res = res * 31 + (semester == null ? 0 : semester.hashCode());
		res = res * 31 + (years == null ? 0 : years.hashCode());
		res = res * 31 + (course == null ? 0 : course.hashCode());

		return res;
	}

	@Override
	public String toString() {
		return "Journal " + years + " " + semester + " " + course;
	}
}

