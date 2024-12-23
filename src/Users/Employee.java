package Users;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import System.Message;
import System.Notification;

public class Employee extends User {

	private int salary;
	private LocalDate dateHired;
	private boolean onVacation;
	private LocalDate vacationStartDate;
	private LocalDate vacationEndDate;
	private LocalDate lastVacationDate;
	private int vacationCoolDown;

	public Employee() {
		initializeDefaults();
	}

	public Employee(String firstname, String lastname) {
		super(firstname, lastname);
		initializeDefaults();
	}

	public Employee(String firstname, String lastname, String email) {
		super(firstname, lastname, email);
		initializeDefaults();
		this.dateHired = LocalDate.now();
	}

	public Employee(String firstname, String lastname, int salary) {
		this(firstname, lastname);
		this.salary = salary;
	}

	public Employee(String firstname, String lastname, String email, int salary) {
		this(firstname, lastname, email);
		this.salary = salary;
	}

	private void initializeDefaults() {
		this.onVacation = false;
		this.vacationStartDate = null;
		this.vacationEndDate = null;
		this.vacationCoolDown = 0;
		this.lastVacationDate = null;
		this.salary = 0;
		this.dateHired = LocalDate.now();
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDate getDateHired() {
		return dateHired;
	}

	public void setDateHired(LocalDate dateHired) {
		this.dateHired = dateHired;
	}

	public boolean isOnVacation() {
		return onVacation;
	}

	public void setOnVacation(boolean onVacation) {
		this.onVacation = onVacation;
	}

	public LocalDate getVacationStartDate() {
		return vacationStartDate;
	}

	public void setVacationStartDate(LocalDate vacationStartDate) {
		this.vacationStartDate = vacationStartDate;
	}

	public LocalDate getVacationEndDate() {
		return vacationEndDate;
	}

	public void setVacationEndDate(LocalDate vacationEndDate) {
		this.vacationEndDate = vacationEndDate;
	}

	public LocalDate getLastVacationDate() {
		return lastVacationDate;
	}

	public void setLastVacationDate(LocalDate lastVacationDate) {
		this.lastVacationDate = lastVacationDate;
	}

	public int getVacationCoolDown() {
		return vacationCoolDown;
	}

	public void setVacationCoolDown(int vacationCoolDown) {
		this.vacationCoolDown = vacationCoolDown;
	}


	/**
	 * Sends a message to an employee and notifies them of the new message.
	 *
	 * @param employee the recipient employee who will receive the message.
	 *                 Must not be null.
	 * @param message  the message being sent. Must not be null.
	 * @throws IllegalArgumentException if the employee or message is null.
	 */

	/**
	 *
	 * Checks whether the employee can request a vacation based on their current
	 * vacation status and the cooldown period since their last vacation
	 * @param requestDate
	 * @return boolean
	 */

	public boolean requestVacation(LocalDate requestDate) {
		if (!onVacation) {
			if (lastVacationDate == null || ChronoUnit.DAYS.between(lastVacationDate, requestDate) >= vacationCoolDown) {
				return true;
			}
			return false;
		} else if (vacationEndDate != null && requestDate.isAfter(vacationEndDate)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(salary, dateHired, onVacation, vacationStartDate, vacationEndDate, lastVacationDate, vacationCoolDown);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Employee employee = (Employee) obj;
		return salary == employee.salary &&
				onVacation == employee.onVacation &&
				vacationCoolDown == employee.vacationCoolDown &&
				Objects.equals(dateHired, employee.dateHired) &&
				Objects.equals(vacationStartDate, employee.vacationStartDate) &&
				Objects.equals(vacationEndDate, employee.vacationEndDate) &&
				Objects.equals(lastVacationDate, employee.lastVacationDate);
	}
}