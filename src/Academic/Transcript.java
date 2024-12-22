package Academic;

import Users.Student;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Represents the transcript of a student, containing their grades, GPA, and course information.
 * This class handles the storage of course grades, GPA calculations, and provides methods
 * to display the student's transcript.
 * It also provides methods to calculate the letter grade, GPA, and traditional grade based on the student's scores.
 */
public class Transcript implements Serializable {
	private Student owner;
	private HashMap<Course, Double> transcriptData;

	private static final String[] GRADE_SCALE = {"D", "D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A"};
	private static final double[] GPA_SCALE = {1.0, 1.33, 1.67, 2.0, 2.33, 2.67, 3.0, 3.33, 3.67, 4.0};


//	constructors
	public Transcript() {
		transcriptData = new HashMap<>();
	}

	public Transcript(Student owner) {
		this.owner = owner;
	}



//	accessors
	public HashMap<Course, Double> getTranscriptData() {
		return transcriptData;
	}

	public Double getCourseGrade(Course course) {
		return transcriptData.get(course);
	}

	/**
	 * Calculates the average grade of the student based on their courses.
	 *
	 * @return The average grade, rounded down to the nearest integer.
	 */
	public int getAvgGrades() {
		if (transcriptData == null || transcriptData.isEmpty()) {
			return 0;
		}

		double sum = transcriptData.values().stream()
				.mapToDouble(Double::doubleValue)
				.sum();

		return (int) sum / transcriptData.size();
	}



	/**
	 * Displays the transcript in a formatted table, including letter grades, GPA, and course information.
	 * The display includes a cumulative GPA and total credits.
	 */
	public void displayTranscript() {
		double totalGPA = 0.0;
		int totalCredits = 0;

		System.out.printf("%-15s %-10s %-10s %-10s %-15s%n", "Course", "Grade", "GPA", "Credits", "Traditional");
		System.out.println("------------------------------------------------------------");

		for (HashMap.Entry<Course, Double> entry : transcriptData.entrySet()) {
			Course course = entry.getKey();
			double grade = entry.getValue();
			int credits = course.getCredits();

			String letterGrade = getLetterGrade(grade);
			double gpa = getGPA(grade);
			String traditional = getTraditionalGrade(grade);

			totalGPA += gpa * credits;
			totalCredits += credits;

			System.out.println(course + " " + transcriptData.get(course) + " " + letterGrade + " " + gpa + " " + traditional);
		}

		double cumulativeGPA = totalCredits > 0 ? totalGPA / totalCredits : 0.0;
		System.out.println("------------------------------------------------------------");
		System.out.printf("Credits: " + totalCredits + "  GPA: " + cumulativeGPA);
	}

	/**
	 * Converts a numeric grade to a letter grade.
	 *
	 * @param grade The numeric grade (between 0 and 100).
	 * @return The corresponding letter grade.
	 */

	private String getLetterGrade(double grade) {
		int index = Math.min((int) Math.floor((grade / 10) - 5), GRADE_SCALE.length - 1);
		return index >= 0 ? GRADE_SCALE[index] : "F";
	}

	/**
	 * Converts a numeric grade to a GPA value.
	 *
	 * @param grade The numeric grade (between 0 and 100).
	 * @return The corresponding GPA.
	 */
	private double getGPA(double grade) {
		int index = Math.min((int) Math.floor((grade / 10) - 5), GPA_SCALE.length - 1);
		return index >= 0 ? GPA_SCALE[index] : 0.0;
	}

	/**
	 * Converts a numeric grade to a traditional grade description.
	 *
	 * @param grade The numeric grade (between 0 and 100).
	 * @return The corresponding traditional grade description.
	 */
	private String getTraditionalGrade(double grade) {
		if (grade >= 90) return "Excellent";
		else if (grade >= 75) return "Good";
			else if (grade >= 50) return "Satisfactory";
		else return "Unsatisfactory";
	}
	/**
	 * Calculates the total GPA based on the transcript data, considering course credits.
	 *
	 * @return The total GPA based on the courses and grades.
	 */

	private double getTotalGPA() {
		int totalCredits = 0;
		double totalGPA = 0.0;
		for (Course course : transcriptData.keySet()) {
			totalGPA += getGPA(transcriptData.get(course));
			totalCredits += course.getCredits();
		}
		return totalGPA / totalCredits;
	}

//	private int getTotalCredits() {
//		int totalCredits = 0;
//		for (Course course : transcriptData.keySet()) {
//			totalCredits += course.getCredits();
//		}
//
//		return totalCredits;
//	}



//		public double calculateGrade(int percentage) {
//			if (percentage >= 95) {
//				return 4.0;
//			} else if (percentage >= 90) {
//				return 3.67;
//			} else if (percentage >= 85) {
//				return 3.33;
//			} else if (percentage >= 80) {
//				return 3.0;
//			} else if (percentage >= 75) {
//				return 2.67;
//			} else if (percentage >= 70) {
//				return 2.33;
//			} else if (percentage >= 65) {
//				return 2.0;
//			} else if (percentage >= 60) {
//				return 1.67;
//			} else if (percentage >= 55) {
//				return 1.33;
//			} else if (percentage >= 50) {
//				return 1.0;
//			} else {
//				return 0.0;
//			}
//		}
//
//		public Map<String, Object> calculateGPA() {
//			Scanner scanner = new Scanner(System.in);
//
//			System.out.print("Enter the number of courses: ");
//			int numCourses = scanner.nextInt();
//
//			double totalQualityPoints = 0;
//			int totalCredits = 0;
//
//			for (int i = 1; i <= numCourses; i++) {
//				System.out.print("Enter the number of credits for course " + i + ": ");
//				int credits = scanner.nextInt();
//
//				System.out.print("Enter the grade percentage for course " + i + ": ");
//				int percentage = scanner.nextInt();
//
//				double gradePoint = calculateGrade(percentage);
//				totalQualityPoints += gradePoint * credits;
//				totalCredits += credits;
//			}
//
//			double gpa = totalCredits == 0 ? 0.0 : totalQualityPoints / totalCredits;
//
//			Map<String, Object> result = new HashMap<>();
//			result.put("GPA", gpa);
//			result.put("Total Credits", totalCredits);
//			result.put("Number of Courses", numCourses);
//
//			return result;
//		}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Transcript t = (Transcript) o;
		return owner.equals(t.owner) && transcriptData.equals(t.transcriptData);
	}

	@Override
	public String toString() {
		return "Just Transcript of student: " + owner.getFirstname() + " " + owner.getLastname();
	}

	@Override
	public int hashCode() {
		int res = 31;
		res = res * (owner != null ? owner.hashCode() : 0);

		return res;
	}
}
