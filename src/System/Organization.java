package System;

import Users.Student;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;


public class Organization implements Serializable {

	private String name;
	private Student head;
	private Date dateCreated;
	private String slogan;
	private Vector<Student> members;

	public Organization() {
		this.members = new Vector<>();
		this.dateCreated = new Date();
	}

	public Organization(String name, Student head) {
		this.name = name;
		this.head = head;
		this.slogan = "";
		this.members = new Vector<>();
		this.dateCreated = new Date();
	}

	public boolean addMember(Student student) {
		if (student != null && !members.contains(student)) {
			members.add(student);
			return true;
		}
		return false;
	}

	public boolean removeMember(Student student) {
		return members.remove(student);
	}

	public boolean isMember(Student student) {
		return members.contains(student);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHead(Student head) {
		this.head = head;
	}

	public Student getHead() {
		return head;
	}

	public Vector<Student> getMembers() {
		return members;
	}

	public void updateSlogan(String slogan) {
		this.slogan = slogan;
	}

	public void createOrganization() {
		// TODO implement me
	}

	public void deleteOrganization() {
		// TODO implement me
	}


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}

