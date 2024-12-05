package Users ;


import Enums.News;
import Research.Subscriber;
import System.Credentials;
import System.Notification;

import java.util.Vector;

// NewsSubscriber interface
public abstract class User implements Subscriber {

	private String firstname;
	private String lastname;
	private String email;

	
	private Vector<Notification> notifications;
	
	public User(){

	}

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public User(String firstname, String lastname, String email) {
		this(firstname, lastname);
		this.email = email;
	}
	
	public boolean login(Credentials parameter) {
		// TODO implement me
		return false;	
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

