package Users ;

import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import System.Message;
import System.Notification;

public class Employee extends User {

	private String id;
	private int salary;
	private Date dateHired;
	private boolean onVacation;
	private Date vacationEndDate;
	private Researcher isResearcher;
//	Messages logic will be discussed
	private Vector<Message> messages;

	public Employee() {
	}
	public Employee(String firstname, String lastname) {
		super(firstname, lastname);
	}
	public Employee(String firstname, String lastname, String email) {
		super(firstname, lastname, email);
	}
	public Employee(String firstname, String lastname, String email, String id) {
		super(firstname, lastname, email);
		this.id = id;
	}
	public Employee(String firstname, String lastname, String email, String id, int salary) {
		this(firstname, lastname, email, id);
		this.salary = salary;
	}
	public Employee(String firstname, String lastname, String email, String id, int salary, Date dateHired) {
		this(firstname, lastname, email, id, salary);
		this.dateHired = dateHired;
	}
	public Employee(String firstname, String lastname, String email, String id, int salary, Date dateHired, boolean onVacation) {
		this(firstname, lastname, email, id, salary, dateHired);
		this.onVacation = onVacation;
	}
	public Employee(String firstname, String lastname, String email, String id, int salary, Date dateHired, boolean onVacation, Researcher isResearcher) {
		this(firstname, lastname, email, id, salary, dateHired, onVacation);
		this.isResearcher = isResearcher;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public boolean isOnVacation() {
		return onVacation;
	}

	public void setOnVacation(boolean onVacation) {
		this.onVacation = onVacation;
	}

	public Date getVacationEndDate() {
		return vacationEndDate;
	}

	public void setVacationEndDate(Date vacationEndDate) {
		this.vacationEndDate = vacationEndDate;
	}

	public Researcher getIsResearcher() {
		return isResearcher;
	}

	public void setIsResearcher(Researcher isResearcher) {
		this.isResearcher = isResearcher;
	}

	public Vector<Message> getMessages() {
		return messages;
	}

	public void setMessages(Vector<Message> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Employee employee = (Employee) o;
		return salary == employee.salary && onVacation == employee.onVacation && Objects.equals(id, employee.id) && Objects.equals(dateHired, employee.dateHired) && Objects.equals(vacationEndDate, employee.vacationEndDate) && Objects.equals(isResearcher, employee.isResearcher) && Objects.equals(messages, employee.messages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, salary, dateHired, onVacation, vacationEndDate, isResearcher, messages);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id='" + id + '\'' +
				", salary=" + salary +
				", dateHired=" + dateHired +
				", onVacation=" + onVacation +
				", vacationEndDate=" + vacationEndDate +
				", isResearcher=" + isResearcher +
				", messages=" + messages +
				'}';
	}

	public void getVacation(Date vacationEndDate) {
		if (onVacation) {
			System.out.println("Already is on vacation");
		} else {
			onVacation = true;
			this.vacationEndDate = vacationEndDate;
			System.out.println("Employee is on vacation and will back to work in " + vacationEndDate);
		}
	}

	public void receiveMessage(Message message) {
		messages.add(message);
	}

	public void sendMessage(Message message, Employee employee) {
		employee.receiveMessage(message);
	}

	@Override
	public void update() {
		Notification n = new Notification("There is update in ResearchJournal!!!Check it!");
		super.getNotifications().add(n);
		System.out.println(n);
	}
}

