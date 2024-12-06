package System ;

import Users.User;

import java.util.Date;
import java.util.Objects;

public class Invite extends Notification
{
	
	private User receiver;

	public  Invite(User author) {
		super(author);
	}
	public Invite(User author, String text) {
		super(text, author);
	}
	public Invite(User author, String text, User receiver) {
		this(author, text);
		this.receiver = receiver;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Invite invite = (Invite) o;
		return Objects.equals(receiver, invite.receiver);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), receiver);
	}

	@Override
	public String toString() {
		return "Invite{" +
				"receiver=" + receiver +
				'}';
	}
}

