package Academic;

import Enums.Mark;

public class AttestationMark {
	private double firstAtt;
	private double secondAtt;
	private double finalExam;
	private int numericValue;
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

	public double getTotal() {
		return firstAtt + secondAtt + finalExam;
	}


	public Mark getFullAtt() {
		return fullAtt;
	}

	public int getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(int numericValue) {
		this.numericValue = numericValue;
	}

	public void setFullAtt(Mark fullAtt) {
		this.fullAtt = fullAtt;
	}

	private void calculateFullAtt() {
		int total = (int) getTotal();
//		Mark marks[] = {Mark.F, Mark.D, Mark.D_PLUS, Mark.C_MINUS, Mark.C, Mark.C_PLUS, Mark.B_MINUS, Mark.B, Mark.B_PLUS, Mark.A_MINUS, Mark.A};
//

//		if (total < 40) {
//			fullAtt =  marks[0];
//			return;
//		}
//
//		int index = Math.min((int) ((total - 40) / 5) + 1, marks.length - 1);
//
//		fullAtt =  marks[index];

		if (total >= 95) {
			fullAtt = Mark.A;
		} else if (total >= 90) {
			fullAtt = Mark.A_MINUS;
		} else if (total >= 85) {
			fullAtt = Mark.B_PLUS;
		} else if (total >= 80) {
			fullAtt = Mark.B;
		} else if (total >= 75) {
			fullAtt = Mark.B_MINUS;
		} else if (total >= 70) {
			fullAtt = Mark.C_PLUS;
		} else if (total >= 65) {
			fullAtt = Mark.C;
		} else if (total >= 60) {
			fullAtt = Mark.C_MINUS;
		} else if (total >= 55) {
			fullAtt = Mark.D_PLUS;
		} else if(total >= 50){
			fullAtt = Mark.D;
		}else {
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
