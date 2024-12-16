package Academic;

import java.util.*;
import System.CustomPair;
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
		this.info = info;
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

//	public void updateAttestation(SemesterPeriod period, Course course, AttestationMark mark) {
//		info.putIfAbsent(period, new ArrayList<>());
//
//		List<CustomPair<Course, AttestationMark>> attestationList = info.get(period);
//
//		for (CustomPair<Course, AttestationMark> entry : attestationList) {
//			if (entry.getFirst().equals(course)) {
//				entry.setSecond(mark);
//				return;
//			}
//		}
//		attestationList.add(new CustomPair<>(course, mark));
//	}

	public void calculateAverageMark() {
		double totalMarks = 0;
		int count = info.size();

//		for (Map.Entry<SemesterPeriod, List<CustomPair<Course, AttestationMark>>> entry : info.entrySet()) {
//			for (CustomPair<Course, AttestationMark> pair : entry.getValue()) {
//				AttestationMark mark = pair.getSecond();
//				totalMarks += mark.getNumericValue();
//				count++;
//			}
//		}

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
