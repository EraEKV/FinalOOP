package Users ;

import CustomExceptions.NotAResearcherException;
import Research.*;
import System.News;
import System.Notification;

import java.io.Serializable;
import java.util.Vector;

// NewsSubscriber interface
public abstract class User implements Subscriber, CanBeResearcher, Serializable {

	private String firstname;
	private String lastname;
	private String email;
	private Researcher isResearcher;
	private Vector<Notification> notifications;
	
	public User() {
	}

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.notifications = new Vector<Notification>();
	}

	public User(String firstname, String lastname, String email) {
		this(firstname, lastname);
		this.email = email;
	}

	public Vector<News> viewNews() {
		// TODO implement me
		return null;	
	}

	
//	public void addComments(News news, String text) {
//		// TODO implement me
//		return;
//	}
	
//	public void newNotifications( parameter) {
//		// TODO implement me
//		return null;
//	}


//	accessors

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Vector<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Vector<Notification> notifications) {
		this.notifications = notifications;
	}
	//Observer pattern
	public void update(){
		notifications.add(new Notification("New post in research journal!!!"));
		System.out.println("New post in research journal!!!");
	}

	//researcher realization
	@Override
	public void beReseacrher() {
		this.isResearcher = new Researcher();
	}

	public Researcher getIsResearcher() {
		if (isResearcher == null) {
			throw new NotAResearcherException("Access denied: User is not a researcher.");
		}
		return isResearcher;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		User u = (User) o;

		return firstname.equals(u.firstname)
				&& lastname.equals(u.lastname)
				&& email.equals(u.email);
	}

	@Override
	public int hashCode() {
		int res = 31;
		res = 31 * res + (firstname != null ? firstname.hashCode() : 0);
		res = 31 * res + (lastname != null ? lastname.hashCode() : 0);
		res = 31 * res + (email != null ? email.hashCode() : 0);

		return res;
	}

	@Override
	public String toString() {
			return getClass().getName()
					+ "  firstname=" + firstname
					+ ", lastname=" + lastname
					+ ", email=" + email;
	}
}

