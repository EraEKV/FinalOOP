package Users;

import System.Request;
import Enums.Status;
import java.util.Objects;
import java.util.Vector;

public class Rector extends Employee {
	private static Rector instance;
	private Vector<Request> requests;

	private Rector() {
		this.requests = new Vector<>();
	}

	public static Rector getInstance() {
		if (instance == null) {
			instance = new Rector();
		}
		return instance;
	}

	public Vector<Request> getRequests() {
		return requests;
	}

	public void addRequest(Request request) {
		requests.add(request);
	}

	public void signRequest(Request request) {
		if (requests.contains(request)) {
			request.setSigned(true);
			request.setStatus(Status.DONE);
			System.out.println("Request signed: " + request.getTopic());
			requests.remove(request);
		} else {
			System.out.println("Request not found.");
		}
	}

	public void rejectRequest(Request request) {
		if (requests.contains(request)) {
			request.setStatus(Status.REJECTED);
			System.out.println("Request rejected: " + request.getTopic());
			requests.remove(request);
		} else {
			System.out.println("Request not found.");
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(requests);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Rector rector = (Rector) obj;
		return Objects.equals(requests, rector.requests);
	}
}