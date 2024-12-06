package Academic;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import java.util.Objects;

public class Attestation {
	private Map<SemesterPeriod, Vector<Pair<Course, AttestationMark>>> info;

	public Attestation() {
		this.info = new HashMap<>();
	}

	public Map<SemesterPeriod, Vector<Pair<Course, AttestationMark>>> getInfo() {
		return info;
	}

	public void updateAttestation(SemesterPeriod period, Course course, AttestationMark mark) {
		info.putIfAbsent(period, new Vector<>());

		Vector<Pair<Course, AttestationMark>> attestationList = info.get(period);

		for (Pair<Course, AttestationMark> entry : attestationList) {
			if (entry.getKey().equals(course)) {
				entry.setValue(mark);
				return;
			}
		}

		attestationList.add(new Pair<>(course, mark));
	}

	@Override
	public String toString() {
		return "Attestation{" +
				"info=" + info +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Attestation)) return false;
		Attestation that = (Attestation) o;
		return Objects.equals(info, that.info);
	}

	@Override
	public int hashCode() {
		return Objects.hash(info);
	}
}
