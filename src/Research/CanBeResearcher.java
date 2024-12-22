package Research;


import Enums.Faculty;
import Users.User;

public  interface CanBeResearcher extends Subscriber {
	public void beResearcher() ;
	public Faculty getFaculty();
	public User getUser();
}

