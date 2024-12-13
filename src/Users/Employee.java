package Users;

import java.util.Date;
import java.util.Objects;
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

	@Override
	public <T> T getUserType() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(salary, dateHired, onVacation, vacationEndDate, messages);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Employee employee = (Employee) obj;
		return salary == employee.salary &&
				onVacation == employee.onVacation &&
				Objects.equals(dateHired, employee.dateHired) &&
				Objects.equals(vacationEndDate, employee.vacationEndDate) &&
				Objects.equals(messages, employee.messages);
	}
}