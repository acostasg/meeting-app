package edu.uoc.prac;

/**
 * 
 * @author DPOO
 *
 */
public class MeetingException extends Exception {

    private static final long serialVersionUID = -3643037098544095876L;
	
    /** causes */
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String MEETING_GROUP_ALREADY_EXISTS = "Meeting Group already exists";
    public static final String NOT_EXISTING_USER_COORDINATOR = "The coordinator of the Meeting Group is not an exisiting User";
    public static final String NOT_EXISTING_USER_MEMBER = "To be a member of a Meeting Group requires to be an existing user of the Meeting Manager. Check addUser.";
    public static final String NOT_EXISTING_MEETING_GROUP="The requested Meeting Group does not exists";
    public static final String USER_IS_ALREADY_THE_ORGANIZER="The requested User is already the organizer. Not added to coorganizers list";
    public static final String USER_IS_ALREADY_A_COORGANIZER="The requested User is already a coorganizer.";
    public static final String DUPLICATE_ASSIGNMENT="Duplicate Assignment";
    public static final String USER_IS_ALREADY_A_MEMBER="User is already a member of the meeting group";
    public static final String MAXIMUM_NUMBER_OF_INTERESTS_PER_USER="Maximum number of interests per user exceeded or duplicated user interest";
    public static final String PLACE_ALREADY_EXISTS="Already existing place in the same country. Not added";
    public static final String PLACE_NOT_FOUND="Place not found in Meeting Manager";
    public static final String MEETING_GROUP_NOT_FOUND="Meeting Group not found in Meeting Manager";

    public static final String PLACE_ALREADY_IN_MEETING_GROUP="Place already in Meeting Group";
    public static final String MEETING_ALREADY_IN_GROUP="Meeting with same description in Meeting Group. Not added.";
    public static final String MEETING_NOT_FOUND="Meeting not found in Meeting Manager";
    public static final String USER_NOT_FOUND="User not found in Meeting Manager";
    public static final String USER_NOT_FOUND_IN_MG="User not found as member neither coorganizer nor Organizer of the Meeting Group that organizes the meeting";
    public static final String ANSWER_ALREADY_FOUND_FOR_USER_MEETING="The User has already answered Meeting Attending";
    public static final String ANSWER_EXCEEDS_GUESTS_PER_MEETING="The number of guests exceeds the allowed number of guests for meeting";
    public static final String THE_MEETING_IS_FULL="The meeting is full.";
    public static final String NO_MEETING_OR_NO_ANSWERS="No existing Meeting or not Answers related to meeting yet";

    /**
     * 
     * @param cause
     */
    public MeetingException (String cause) {
    	super (cause);
    }
}
