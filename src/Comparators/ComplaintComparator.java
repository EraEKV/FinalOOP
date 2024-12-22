package Comparators;

import System.Complaint;

import java.util.Comparator;
import java.util.Date;

public class ComplaintComparator implements Comparator<Complaint> {

    @Override
    public int compare(Complaint c1, Complaint c2) {
        int urgencyComparison = Integer.compare(c2.getUrgency().ordinal(), c1.getUrgency().ordinal());

        if (urgencyComparison == 0) {
            return c1.getDate().compareTo(c2.getDate());
        }

        return urgencyComparison;
    }
}
