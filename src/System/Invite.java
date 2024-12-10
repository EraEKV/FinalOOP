package System ;

import Users.User;

import java.util.Date;
import java.util.Objects;

public class Invite extends Notification {
	
	private Organization org;

	public Invite(User author) {

	}

	public Invite(User author, String text, Organization org) {
		super(author, text);
		this.org = org;
	}


	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}



	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Invite invite = (Invite) o;
		return Objects.equals(org, invite.org);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), org);
	}

	@Override
	public String toString() {
		return "Invite{" +
				"organization=" + org.getName() +
				'}';
	}
}

