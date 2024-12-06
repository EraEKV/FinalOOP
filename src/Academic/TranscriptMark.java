package Academic ;


import Enums.GPA;
import Enums.Mark;
import Enums.MarkType;

import java.util.Objects;

public class TranscriptMark
{
	private MarkType markType;
	private double markDouble;
	private Mark mark;
	private GPA gpa;
	public TranscriptMark(){
	}

	public TranscriptMark(MarkType markType) {
		this.markType = markType;
	}

	public TranscriptMark(MarkType markType, double markDouble) {
		this.markType = markType;
		this.markDouble = markDouble;
	}

	public TranscriptMark(MarkType markType, double markDouble, Mark mark) {
		this.markType = markType;
		this.markDouble = markDouble;
		this.mark = mark;
	}

	public TranscriptMark(MarkType markType, Mark mark, GPA gpa, double markDouble) {
		this.markType = markType;
		this.mark = mark;
		this.gpa = gpa;
		this.markDouble = markDouble;
	}

	public MarkType getMarkType() {
		return markType;
	}

	public void setMarkType(MarkType markType) {
		this.markType = markType;
	}

	public double getMarkDouble() {
		return markDouble;
	}

	public void setMarkDouble(double markDouble) {
		this.markDouble = markDouble;
	}

	public Mark getMark() {
		return mark;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public GPA getGpa() {
		return gpa;
	}

	public void setGpa(GPA gpa) {
		this.gpa = gpa;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		TranscriptMark that = (TranscriptMark) o;
		return Double.compare(markDouble, that.markDouble) == 0 && markType == that.markType && mark == that.mark && gpa == that.gpa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(markType, markDouble, mark, gpa);
	}

	@Override
	public String toString() {
		return "TranscriptMark{" +
				"markType=" + markType +
				", markDouble=" + markDouble +
				", mark=" + mark +
				", gpa=" + gpa +
				'}';
	}
}

