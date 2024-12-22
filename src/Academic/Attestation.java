package Academic;

import java.util.*;
import Users.Student;

public class Attestation {
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

	public void calculateAverageMark() {
		double totalMarks = 0;
		int count = info.size();

		for(AttestationMark m : info.values()) {
			totalMarks += m.getTotal();
		}

		if (count == 0) {
			System.out.println("No marks available to calculate average.");
			return;
		}

		double average = totalMarks / count;
		System.out.println("Average Mark for " + student.getId() + " is  : " + average);
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
