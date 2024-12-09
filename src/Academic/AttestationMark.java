package Academic;

public class AttestationMark {
	private double firstAtt;
	private double secondAtt;
	private double finalExam;
	private Mark fullAtt;

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

	public Mark getFullAtt() {
		return fullAtt;
	}

	private void calculateFullAtt() {
		// Example calculation: 30% first attestation, 30% second attestation, 40% final exam
		double total = (firstAtt * 0.3) + (secondAtt * 0.3) + (finalExam * 0.4);

		// Map total grade to Mark enum (example thresholds)
		if (total >= 90) {
			fullAtt = Mark.A_PLUS;
		} else if (total >= 85) {
			fullAtt = Mark.A;
		} else if (total >= 80) {
			fullAtt = Mark.A_MINUS;
		} else if (total >= 75) {
			fullAtt = Mark.B_PLUS;
		} else if (total >= 70) {
			fullAtt = Mark.B;
		} else if (total >= 65) {
			fullAtt = Mark.B_MINUS;
		} else if (total >= 60) {
			fullAtt = Mark.C_PLUS;
		} else if (total >= 55) {
			fullAtt = Mark.C;
		} else if (total >= 50) {
			fullAtt = Mark.C_MINUS;
		} else {
			fullAtt = Mark.F;
		}
	}

	@Override
	public String toString() {
		return "AttestationMark{" +
				"firstAtt=" + firstAtt +
				", secondAtt=" + secondAtt +
				", finalExam=" + finalExam +
				", fullAtt=" + fullAtt +
				'}';
	}
}

