package Menu;
import Users.Student;

class ViewMarksCommand implements Command {
    private final Student student;

    public ViewMarksCommand(Student student) {
        this.student = student;
    }

    @Override
    public void execute() {
        student.viewMarks();
    }
}