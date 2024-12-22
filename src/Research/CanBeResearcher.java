package Research;


import Enums.Faculty;
import Users.User;

/**
 * The CanBeResearcher interface is part of a system that defines behaviors for users who can participate in research activities
 */
public  interface CanBeResearcher extends Subscriber {
	public void beResearcher() ;
	public Faculty getFaculty();
	public User getUser();
}

