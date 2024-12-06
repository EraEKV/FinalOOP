package Academic ;

import Enums.Attendance;
import Enums.LessonType;

import java.util.Date;

public class JournalLesson {
	
	private Date date;

	
	private LessonType lessonType;

	
	private Attendance attendance;

	private double mark;

	
	private String comment;

	public JournalLesson(){

	}

	public JournalLesson(Date date, LessonType lessonType) {
		this.date = date;
		this.lessonType = lessonType;
	}

	public JournalLesson(LessonType lessonType, Attendance attendance) {
		this.date = new Date();
		this.lessonType = lessonType;
		this.attendance = attendance;
	}

	public JournalLesson(Date date, LessonType lessonType, Attendance attendance) {
		this.date = date;
		this.lessonType = lessonType;
		this.attendance = attendance;
	}

	public JournalLesson(LessonType lessonType, Attendance attendance, double mark) {
		this(lessonType, attendance);
		this.mark = mark;
	}

	public JournalLesson(Date date, LessonType lessonType, Attendance attendance, double mark) {
		this(date, lessonType, attendance);
		this.mark = mark;
	}


	public JournalLesson(LessonType lessonType, Attendance attendance, double mark, String comment) {
		this(lessonType, attendance, mark);
		this.comment = comment;
	}

	public JournalLesson(Date date, LessonType lessonType, Attendance attendance, double mark, String comment) {
		this(date, lessonType, attendance, mark);
		this.comment = comment;
	}


//	getters and setters


	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}

	public LessonType getLessonType() {
		return lessonType;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	public Attendance getAttendance() {
		return attendance;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public double getMark() {
		return mark;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}

