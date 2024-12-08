package Users;


import java.util.Date;
import java.util.Vector;

public class Employee {
	private String id;
	private int salary;
	private Date dateHired;
	private boolean onVacation;
	private Date vacationEndDate;
	private Vector<Message> messages;

	public Employee(String id, int salary, Date dateHired) {
		this.id = id;
		this.salary = salary;
		this.dateHired = dateHired;
		this.onVacation = false;
		this.vacationEndDate = null;
		this.messages = new Vector<>();
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

	public void sendMessage(Employee employee, String messageContent) {
		Message message = new Message(this.id, messageContent);
		employee.getMessages().add(message);
		System.out.println("Message sent to Employee ID: " + employee.getId());
	}
}

class Message {
	private String senderId;
	private String content;

	public Message(String senderId, String content) {
		this.senderId = senderId;
		this.content = content;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getContent() {
		return content;
	}
}