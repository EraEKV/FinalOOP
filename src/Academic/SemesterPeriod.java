package Academic;

import Enums.Semester;
import Enums.Years;

import java.util.Objects;

public class SemesterPeriod {
    private Years years;
    private Semester semester;

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Years getYears() {
        return years;
    }

    public void setYears(Years years) {
        this.years = years;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SemesterPeriod that = (SemesterPeriod) o;
        return years == that.years && semester == that.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(years, semester);
    }

    @Override
    public String toString() {
        return "SemesterPeriod{" +
                "years=" + years +
                ", semester=" + semester +
                '}';
    }
}
