package Academic;

import Enums.Mark;

public class AttestationMark {
	private double firstAtt;
	private double secondAtt;
	private double finalExam;
	private String fullAtt;

	private static final String[] GRADE_SCALE = {"D", "D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A"};


//	Constructors

	public AttestationMark() {
		this.firstAtt = 0;
		this.secondAtt = 0;
		this.finalExam = 0;
		calculateFullAtt();
	}

	public AttestationMark(double firstAtt, double secondAtt, double finalExam) {
		this.firstAtt = firstAtt;
		this.secondAtt = secondAtt;
		this.finalExam = finalExam;
		calculateFullAtt();
	}



	public double getFirstAtt() {
		return firstAtt;
	}

	public void setFirstAtt(double firstAtt) {
		this.firstAtt = firstAtt;
		calculateFullAtt();
	}

	public double getSecondAtt() {
		return secondAtt;
	}

	public void setSecondAtt(double secondAtt) {
		this.secondAtt = secondAtt;
		calculateFullAtt();
	}

	public double getFinalExam() {
		return finalExam;
	}

	public void setFinalExam(double finalExam) {
		this.finalExam = finalExam;
		calculateFullAtt();
	}

	public double getTotal() {
		return firstAtt + secondAtt + finalExam;
	}


	public String getFullAtt() {
		return fullAtt;
	}

	public void setFullAtt(String fullAtt) {
		this.fullAtt = fullAtt;
	}

	/**
	 * calculate full attestaion mark
	 */
	private void calculateFullAtt() {
		if (firstAtt < 30 || secondAtt < 30 || finalExam < 10) {
			fullAtt = "F";
		} else {
			double total = getTotal();
			int index = Math.min((int) Math.floor((total / 10) - 5), GRADE_SCALE.length - 1);
			fullAtt = index >= 0 ? GRADE_SCALE[index] : "F";
		}
	}


	@Override
	public String toString() {
		return "AttestationMark[" +
				"firstAtt=" + firstAtt +
				", secondAtt=" + secondAtt +
				", finalExam=" + finalExam +
				", fullAtt=" + fullAtt +
				']';
	}
}
