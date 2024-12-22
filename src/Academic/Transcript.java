package Academic;

import Enums.Mark;
import Users.Student;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import java.util.Map;
import System.CustomPair;
import java.util.Scanner;
import java.util.HashMap;


public class Transcript implements Serializable {
	private Student owner;
	private double totalGpa;
	private double currentSemesterGpa;
	private int currentSemesterNumberOfCredits;
	private int totalNumberOfCredits;
	private Date receivedDate;
	private Vector<CustomPair<Course, Mark>> transcriptData;
	private Map<SemesterPeriod, Vector<CustomPair<Course, Mark> > > semesterInfo;

	public Transcript() {
		transcriptData = new Vector<>();
	}

	public Vector<CustomPair<Course, Mark>> getTranscript() {
		return transcriptData;
	}

	public Student getOwner() {
		return owner;
	}

	public void setOwner(Student owner) {
		this.owner = owner;
	}

	public void setTotalGpa(double totalGpa) {
		this.totalGpa = totalGpa;
	}

	public double getCurrentSemesterGpa() {
		return currentSemesterGpa;
	}

	public void setCurrentSemesterGpa(double currentSemesterGpa) {
		this.currentSemesterGpa = currentSemesterGpa;
	}

	public int getTotalNumberOfCredits() {
		return totalNumberOfCredits;
	}

	public void setTotalNumberOfCredits(int totalNumberOfCredits) {
		this.totalNumberOfCredits = totalNumberOfCredits;
	}

	public int getCurrentSemesterNumberOfCredits() {
		return currentSemesterNumberOfCredits;
	}

	public void setCurrentSemesterNumberOfCredits(int currentSemesterNumberOfCredits) {
		this.currentSemesterNumberOfCredits = currentSemesterNumberOfCredits;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setTranscriptData(Vector<CustomPair<Course, Mark>> transcriptData) {
		this.transcriptData = transcriptData;
	}

	public Map<SemesterPeriod, Vector<CustomPair<Course, Mark>>> getSemesterInfo() {
		return semesterInfo;
	}

	public void setSemesterInfo(Map<SemesterPeriod, Vector<CustomPair<Course, Mark>>> semesterInfo) {
		this.semesterInfo = semesterInfo;
	}

	public double getTotalGpa() {
		return totalGpa;
	}

	public Transcript(Student owner) {
		this.owner = owner;
	}

	public double calculateGrade(int percentage) {
		if (percentage >= 95) {
			return 4.0;
		} else if (percentage >= 90) {
			return 3.67;
		} else if (percentage >= 85) {
			return 3.33;
		} else if (percentage >= 80) {
			return 3.0;
		} else if (percentage >= 75) {
			return 2.67;
		} else if (percentage >= 70) {
			return 2.33;
		} else if (percentage >= 65) {
			return 2.0;
		} else if (percentage >= 60) {
			return 1.67;
		} else if (percentage >= 55) {
			return 1.33;
		} else if (percentage >= 50) {
			return 1.0;
		} else {
			return 0.0;
		}
	}

	public Map<String, Object> calculateGPA() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the number of courses: ");
		int numCourses = scanner.nextInt();

		double totalQualityPoints = 0;
		int totalCredits = 0;

		for (int i = 1; i <= numCourses; i++) {
			System.out.print("Enter the number of credits for course " + i + ": ");
			int credits = scanner.nextInt();

			System.out.print("Enter the grade percentage for course " + i + ": ");
			int percentage = scanner.nextInt();

			double gradePoint = calculateGrade(percentage);
			totalQualityPoints += gradePoint * credits;
			totalCredits += credits;
		}

		double gpa = totalCredits == 0 ? 0.0 : totalQualityPoints / totalCredits;

		Map<String, Object> result = new HashMap<>();
		result.put("GPA", gpa);
		result.put("Total Credits", totalCredits);
		result.put("Number of Courses", numCourses);

		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Transcript that = (Transcript) o;
		return Double.compare(totalGpa, that.totalGpa) == 0 && Double.compare(currentSemesterGpa, that.currentSemesterGpa) == 0 && currentSemesterNumberOfCredits == that.currentSemesterNumberOfCredits && totalNumberOfCredits == that.totalNumberOfCredits && Objects.equals(owner, that.owner) && Objects.equals(receivedDate, that.receivedDate) && Objects.equals(transcriptData, that.transcriptData) && Objects.equals(semesterInfo, that.semesterInfo);
	}

	@Override
	public String toString() {
		return "Transcript{" +
				"owner=" + owner +
				", totalGpa=" + totalGpa +
				", currentSemesterGpa=" + currentSemesterGpa +
				", currentSemesterNumberOfCredits=" + currentSemesterNumberOfCredits +
				", totalNumberOfCredits=" + totalNumberOfCredits +
				", receivedDate=" + receivedDate +
				", transcriptData=" + transcriptData +
				", semesterInfo=" + semesterInfo +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(owner, totalGpa, currentSemesterGpa, currentSemesterNumberOfCredits, totalNumberOfCredits, receivedDate, transcriptData, semesterInfo);
	}
}
