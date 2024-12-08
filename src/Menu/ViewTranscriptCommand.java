package Menu;
import Users.Student;

class ViewTranscriptCommand implements Command {
    private final Student student;

    public ViewTranscriptCommand(Student student) {
        this.student = student;
    }

    @Override
    public void execute() {
        student.viewTranscript();
    }
}