package Academic;

import java.util.*;
import System.CustomPair;

public class Attestation {
	private Map<SemesterPeriod, List<CustomPair<Course, AttestationMark>>> info;

	public Attestation() {
		this.info = new HashMap<>();
	}

	public Map<SemesterPeriod, List<CustomPair<Course, AttestationMark>>> getInfo() {
		return info;
	}

	public void updateAttestation(SemesterPeriod period, Course course, AttestationMark mark) {
		info.putIfAbsent(period, new ArrayList<>());

		List<CustomPair<Course, AttestationMark>> attestationList = info.get(period);

		for (CustomPair<Course, AttestationMark> entry : attestationList) {
			if (entry.getFirst().equals(course)) {
				entry.setSecond(mark);
				return;
			}
		}
		attestationList.add(new CustomPair<>(course, mark));
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
		if (!(o instanceof Attestation that)) return false;
        return Objects.equals(info, that.info);
	}

	@Override
	public int hashCode() {
		return Objects.hash(info);
	}
}
