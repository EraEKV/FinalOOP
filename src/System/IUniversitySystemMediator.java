package System ;

import Enums.NewsTopic;
import Users.Student;
import Users.User;


public interface IUniversitySystemMediator {
	public void notifyUser(User parameter, Notification parameter2);
	
	public void publishNews(String parameter, String parameter2, NewsTopic parameter3);
	
	public void registerUser(Credentials parameter);
	
	public void sendMessage(Student parameter, String parameter2);	
}

