package System ;

import Users.User;

import java.util.Date;

public class Notification {
	private Date date;

	private String text;

	
	private User author;

	public Notification() {

	}

	public Notification(User author) {
		this.author = author;
	}

	public Notification(String text, User author) {
		this.text = text;
		this.author = author;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Notification notification = (Notification) o;

		return author.equals(notification.author)
				&& text.equals(notification.text);
	}

	@Override
	public int hashCode() {
		int res = 31;
		res = res * 31 + (author != null ? author.hashCode() : 0);
		res = res * 31 + (text != null ? text.hashCode() : 0);

		return res;
	}

	@Override
	public String toString() {
		return getClass().getName() +
				"  date=" + date +
				", text=" + text +
				", author=" + author;
	}
}

