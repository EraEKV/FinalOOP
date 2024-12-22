package Users ;

import CustomExceptions.InsufficientAccessRightsException;
import CustomExceptions.NotAResearcherException;
import Research.*;
import System.News;
import System.Notification;

import java.io.Serializable;
import java.util.Vector;

// NewsSubscriber interface
public abstract class User implements Subscriber, Serializable {

	private String firstname;
	private String lastname;
	private String email;
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

	public void removePersonalData(User user) throws InsufficientAccessRightsException {
		if(user instanceof DisciplinaryCommittee || user instanceof Admin) {
			this.firstname = null;
			this.lastname = null;
			this.email = null;
			this.notifications = new Vector<Notification>();
		} else {
			throw new InsufficientAccessRightsException("You have insufficient access rights for deletion sensitive information");
		}
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

	//Observer pattern
	public void update(){
		notifications.add(new Notification("New post in research journal!!!"));
		System.out.println("New post in research journal!!!");
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
			return getClass().getSimpleName()
					+ " firstname=" + firstname
					+ ", lastname=" + lastname
					+ ", email=" + email;
	}
}

