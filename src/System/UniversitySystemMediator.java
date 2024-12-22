package System ;

import Academic.Course;
import CustomExceptions.InvalidAuthDataException;
import CustomExceptions.RegistrationCreditsException;
import Enums.NewsTopic;
import Enums.Urgency;
import Users.Admin;
import Users.Student;
import Users.User;

import Database.Database;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

public class UniversitySystemMediator {
	public UniversitySystemMediator() {	}


//	Auth
	public static User authenticateUser(BufferedReader reader) throws InvalidAuthDataException, IOException {
		String email;
		String password;

		auth: while(true) {
			System.out.print("Enter your email(if wanna exit type 0): ");
			email = reader.readLine().trim();

			if(email.equals("0")) {
				break auth;
			} else if(!email.contains("@kbtu.kz")) {
				email += "@kbtu.kz";
			}

			System.out.print("Enter the password: ");
			password = reader.readLine().trim();

			User user = Database.getInstance().findUserByCredentials(new Credentials(email, password));

			if (user == null) {
				System.out.println("User not registered in the system!");
				continue auth;
			}

			return user;
		}
		return null;
	}


	public static void deleteOrganization(String name) {
		Database db = Database.getInstance();
		Organization org = db.findOrganization(name);

		if (org != null) {

			Vector<Student> members = org.getMembers();


			for(Student m : members) {
				m.getNotifications().add(new Message(new Admin("University", "System", "vsp"), "Organization you are joined with name: " + org.getName() + "now not exists" ));
				m.getJoinedOrganizations().remove(org);
			}
			org.getHead().setIsHead(null);
			db.getStudentOrganizations().remove(org);
			System.out.println("Organization " + name + " has been removed.");
		} else {
			System.out.println("Organization with name " + name + " not found.");
		}
	}


//	Registration for course
	public void courseRegistration(Student student) throws RegistrationCreditsException {
		Vector<Course> courses = new Vector<>();
		Database db = Database.getInstance();


	}



//	message sending logic
	public void sendMessage(User sender, String email, String message) {
		Database db = Database.getInstance();
		User user = db.findUserByEmail(email);
		if (user != null) {
			try {
				user.getNotifications().add(new Message(sender, message));
				System.out.println("Message sent successfully to " + user.getEmail());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}



//	News logic
	public static void publishNews(String author, NewsTopic topic, String title, String content) {
		Database db = Database.getInstance();
		try {
			db.getNews().add(new News(author, title, content, topic));
			System.out.println("News added  " + db.getNews().toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void addComment(News news, User sender, String text) {
		try {
			news.getComment().add(new Comment(sender, text));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}



//	Send Complaint
	public void sendComplaint(User sender, Student student, String text, Urgency urgency) {
		Database db = Database.getInstance();
		try {
			db.getDisciplinaryCommittee().getComplaints().add(new Complaint(sender, student, text, urgency));
			System.out.println("Complaint sended");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}



//	Send Invite
	public static void sendInvite(User sender, Student invitee, String text, Organization org) {
		if(text == null) {
			text = "Dear " + invitee.getFirstname() + ",\n"
					+ "You are invited to join the organization: " + org.getName() + ".\n"
					+ "We would be glad to have you as part of our team.\n";

			if (org.getSlogan() != null && !org.getSlogan().isEmpty()) {
				text += "\nOur slogan: " + org.getSlogan() + "\n" + "With love, " + sender.getFirstname();
			}
		}
		Invite invite = new Invite(sender, text, org);
		invitee.getNotifications().add(invite);

		System.out.println("Invite sent successfully!");
	}



//	MUST BE REVIEWED, LOGIC IS NOT READY AT ALL
//	Send Request
//	public void sendRequest()
}

