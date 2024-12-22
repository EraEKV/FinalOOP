package Research;


import Enums.Faculty;

public  interface CanBeResearcher extends Subscriber {
	public void beResearcher() ;
	public Faculty getFaculty();
}

