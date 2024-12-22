package Users;

import System.Request;
import Enums.Status;
import java.util.Objects;
import java.util.Vector;

/**
 * The Rector class represents a rector (head) of an academic institution.
 * The class is implemented as a Singleton, ensuring that only one instance of the rector exists in the system.
 * The rector has the authority to manage requests (add, sign, reject) and track the status of those requests.
 */
public class Rector extends Employee {

	private static Rector instance;

	private Vector<Request> requests;

	private Rector() {
		super("Gabdullin", "Maratbek", "gabdullin@kbtu.kz");
		this.requests = new Vector<>();
	}

	/**
	 * Retrieves the single instance of the Rector class.
	 * If the instance does not exist, it creates one.
	 * @return The single instance of the Rector class.
	 */
	public static Rector getInstance() {
		if (instance == null) {
			instance = new Rector();
		}
		return instance;
	}

	/**
	 * Gets the list of requests assigned to the Rector.
	 * @return A vector containing the requests.
	 */
	public Vector<Request> getRequests() {
		return requests;
	}

	/**
	 * Adds a new request to the Rector's list of requests.
	 * @param request The request to be added.
	 */
	public void addRequest(Request request) {
		requests.add(request);
	}

	/**
	 * Signs a request, marking it as done and removing it from the list of requests.
	 * @param request The request to be signed.
	 */
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

	/**
	 * Rejects a request, setting its status to rejected and removing it from the list of requests.
	 * @param request The request to be rejected.
	 */
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
