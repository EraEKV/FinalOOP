package Academic;

import Enums.Semester;

import java.util.Objects;

public class SemesterPeriod {
    private String years;
    private Semester semester;

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SemesterPeriod that = (SemesterPeriod) o;
        return Objects.equals(years, that.years) && semester == that.semester;
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
