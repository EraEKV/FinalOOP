package Users;


import java.util.Date;
import java.util.Vector;
import System.Message;

public class Employee extends User {

	private int salary;
	private Date dateHired;
	private boolean onVacation;
	private Date vacationEndDate;
	private Vector<Message> messages;

	public Employee() {

	}

	public Employee(String firstname, String lastname) {
		super(firstname, lastname);
		this.onVacation = false;
		this.vacationEndDate = null;
		this.messages = new Vector<>();
		this.salary = 0;
	}

	public Employee(String firstname, String lastname, String email) {
		super(firstname, lastname, email);
		this.onVacation = false;
		this.vacationEndDate = null;
		this.messages = new Vector<>();
		this.salary = 0;
		this.dateHired = new Date();
	}

	public Employee(String firstname, String lastname, int salary) {
		this(firstname, lastname);
		this.salary = salary;
	}

	public Employee(String firstname, String lastname, String email, int salary) {
		this(firstname, lastname, email);
		this.salary = salary;
	}


//	accessors

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





	public Vector<Message> getMessages() {
		return messages;
	}

	public boolean getVacation(Date date) {
		if (!onVacation) {
			System.out.println("Vacation is available.");
			return true;
		} else if (date.after(vacationEndDate)) {
			System.out.println("Vacation is available after " + vacationEndDate);
			return true;
		}
		System.out.println("Currently on vacation until " + vacationEndDate);
		return false;
	}

//	public void sendMessage(Employee employee, String messageContent) {
//		Message message = new Message(this.id, messageContent);
//		employee.getMessages().add(message);
//		System.out.println("Message sent to Employee ID: " + employee.getId());
//	}

	@Override
	public <T> T getUserType() {
		return null;
	}
}

