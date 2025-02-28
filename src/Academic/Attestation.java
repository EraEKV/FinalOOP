package Academic;

import java.io.Serializable;
import java.util.*;
import Users.Student;

/**
 * The Transcript class represents an academic transcript of a student.
 * It contains details of the courses the student has taken, their corresponding grades, and the GPA information.
 * The class provides methods to calculate and display the student's overall performance.
 */
public class Attestation implements Serializable {
	private Student student;
	SemesterPeriod semesterPeriod;
	private Map<Course, AttestationMark> info;

	{
		this.info = new HashMap<>();
	}

	public Attestation() {
	}

	public Attestation(Student student) {
		this.student = student;
	}

	public Attestation(Student student, Map<Course, AttestationMark> info) {
		this.student = student;
		this.info = info;
	}

	public Attestation(Student student, SemesterPeriod semesterPeriod, Map<Course, AttestationMark> info) {
		this(student, info);
		this.semesterPeriod = semesterPeriod;
	}


	public Student getStudent() {
		return student;
	}

	public Map<Course, AttestationMark> getInfo() {
		return info;
	}

	public void setInfo(Map<Course, AttestationMark> info) {
		this.info = info;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	public SemesterPeriod getSemesterPeriod() {
		return semesterPeriod;
	}


	@Override
	public String toString() {
		return "Attestation{" +
				"info=" + info +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Attestation that)) return false;
        return Objects.equals(info, that.info);
	}

	@Override
	public int hashCode() {
		return Objects.hash(info);
	}
}
