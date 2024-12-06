package Users ;


import java.util.Date;
import java.util.Vector;

import System.Message;

public class Employee extends User {

	private String id;
	private int salary;
	private Date dateHired;

	private boolean onVacation;
	private Date vacationEndDate;

	private Researcher isResearcher;

//	Messages logic will be discussed
	private Vector<Message> messages;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Employee() {

	}

	public Employee(String firstname, String lastname) {
		super(firstname, lastname);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean getVacation(Date parameter) {
		// TODO implement me
		return false;	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void sendMessage(Message parameter2) {
		// TODO implement me
		return;
	}

	
	public void sendMessage(Employee parameter, String parameter2) {
		// TODO implement me
		return;
	}

	@Override
	public void update() {

	}
}

