package Comparators;

import Users.User;
import java.util.Comparator;

public class UserNameComparator implements Comparator<User> {
    public int compare(User o1, User o2) {
        int res = o1.getLastname().compareTo(o2.getLastname());

        if (res == 0) {
            return o1.getFirstname().compareTo(o2.getFirstname());
        }

        return res;
    }
}
