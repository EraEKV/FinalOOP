package Pakita;

import Enums.Mark;
import Users.Student;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import java.util.Map;
import System.CustomPair;
import Academic.SemesterPeriod;
import Academic.Course;


public class Transcript {
	private Student owner;
	private double totalGpa;
	private double currentSemesterGpa;
	private int currentSemesterNumberOfCredits;
	private int totalNumberOfCredits;
	private Date receivedDate;
	private Vector<CustomPair<Course, GPA>> transcriptData;
	private Map<SemesterPeriod, Vector<CustomPair<Course, Mark>>> semesterInfo;

	public Transcript() {
		transcriptData = new Vector<>();
	}

	public Vector<CustomPair<Course, GPA>> getTranscript() {
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

	public void setTranscriptData(Vector<CustomPair<Course, GPA>> transcriptData) {
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

	public double calculateSemesterGPA() {
		return 0.0;
	}

	public double calculateTotalGPA() {
		return 0.0;
	}

	public int calculateTotalCreditsCounted() {
		return 0;
	}

	public int calculateTotalCreditsTook() {
		return 0;
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
