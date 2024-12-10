package System;

import Users.User;

import java.util.Date;
import java.util.Objects;


public class Comment {

	private Date date;
	private String text;
	private User author;


//	constructors

	public Comment() {
	}

	public Comment(User author, String text) {
		this.author = author;
		this.text = text;
		this.date = new Date();
	}


//	accessors

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}




	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Comment comment = (Comment) o;
		return Objects.equals(date, comment.date) && Objects.equals(text, comment.text) && Objects.equals(author, comment.author);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, text, author);
	}

	@Override
	public String toString() {
		return "Comment{" +
				"date=" + date +
				", text='" + text + '\'' +
				", author=" + author +
				'}';
	}
}

