package edu.uoc.prac;
import java.util.Comparator;

public class UserEmailComparator implements Comparator<User> {
	// Sort users by its email
    public int compare(User u1, User u2) {
        return u1.getEmail().compareTo(u2.getEmail());
    }
}
