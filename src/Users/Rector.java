package Users ;
import System.Request;

import java.util.Vector;

public class Rector extends Employee{
	private Vector<Request> requests;

	public Rector() {
		this.requests = new Vector<>();
	}

//	public void signRequest(Request request) {
//		if (requests.contains(request)) {
//			System.out.println("Request signed: " + request.getRequestDetails());
//			requests.remove(request);
//		} else {
//			System.out.println("Request not found.");
//		}
//	}
//
//	public void rejectRequest(Request request) {
//		if (requests.contains(request)) {
//			System.out.println("Request rejected: " + request.getRequestDetails());
//			requests.remove(request);
//		} else {
//			System.out.println("Request not found.");
//		}
//	}

	public void addRequest(Request request) {
		requests.add(request);
	}
}