package edu.uoc.prac;
import java.util.ArrayList;
import java.util.Date;

/**
*
* MeetingManager class definition
*
* @author Albert Costas
*
*/

public class MeetingManager {

	/** attributes definition */
	private ArrayList<User> users;
	private ArrayList<MeetingGroup> meetingGroups;
	private ArrayList<Place> places;
	private ArrayList<Meeting> meetings;
	private ArrayList<Answer> answers;

	/**
    * Constructor
    */
    public MeetingManager() {
		this.users = new ArrayList<User>();
		this.meetingGroups = new ArrayList<MeetingGroup>();
		this.places = new ArrayList<Place>();
		this.meetings = new ArrayList<Meeting>();
		this.answers = new ArrayList<Answer>();
    }
    
    /***********************************************************************************/
    /** PRIVATE METHODS **/
    
    // Search and return a meeting group by its meeting group name
    private MeetingGroup searchMeetingGroupByName(String nameMeetingGroup) {
    	boolean found = false;
    	int i = 0;
    	while ( !found && i < meetingGroups.size() ) {
			found = meetingGroups.get(i).getName().equals(nameMeetingGroup);
    		++i;
		}
    	return found ? meetingGroups.get(i-1) : null;
    }
    // Search and return a user by his email
    private User searchUserByEmail(String emailUser) {
    	boolean found = false;
    	int i = 0;
    	while ( !found && i < users.size() ) {
			found = users.get(i).getEmail().equals(emailUser);
    		++i;
		}
    	return found ? users.get(i-1) : null;
    }
    // Search and return a meeting by its description
    private Meeting searchMeetingByDescription(String descriptionMeeting) {
    	boolean found = false;
    	int i = 0;
    	while ( !found && i < meetings.size() ) {
			found = meetings.get(i).getDescription().equals(descriptionMeeting);
    		++i;
		}
    	return found ? meetings.get(i-1) : null;
    }
    
    /***********************************************************************************/
    /** PUBLIC METHODS **/
    
    /**
   	* Method instantiate and add a user to the system
   	* @return type of User
   	*/
    public User addUser(String email, String password) throws MeetingException {
    	User u = new User(email, password);
    	if ( users.contains(u) ) {
    		throw new MeetingException(MeetingException.USER_ALREADY_EXISTS);
    	} else {
    		users.add(u);
    	}
    	return u;
    }
    
    /**
   	* Method to show all the users in the system 
   	* 
   	*/
    public String listUsers() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("MeetingManager Users:").append("\n");
    	for (int i = 0; i < users.size(); i++)
			sb.append(" * ").append(users.get(i)).append("\n");
    	return sb.toString();
    }
    
    /**
   	* Method to add a meetingGroup to the system
   	* @return type of User
   	*/
    public MeetingGroup addMeetingGroup(String nameMeetingGroup, String email, String pwd, String phone) throws MeetingException {
    	MeetingGroup mg = new MeetingGroup(nameMeetingGroup);
    	if ( meetingGroups.contains(mg) ) {
    		throw new MeetingException(MeetingException.MEETING_GROUP_ALREADY_EXISTS);
    	} else {
        	Organizer o = new Organizer(email, pwd, phone);
    		if ( users.contains(o)) {
    			Assignment a = new Assignment(o, mg);
    			mg.setAssignment(a);
        		meetingGroups.add(mg);
    		} else {
        		throw new MeetingException(MeetingException.NOT_EXISTING_USER_COORDINATOR);
    		}
    	}
    	return mg;
    }
    
    /**
   	* Method to add a coorganizer to a meeting group
   	* @return type of User
   	*/
    public MeetingGroup addCoorganizer(String nameMeetingGroup, String email, String pwd) throws MeetingException {
    	MeetingGroup mg = this.searchMeetingGroupByName(nameMeetingGroup);
    	User u = this.searchUserByEmail(email);
    	if ( !meetingGroups.contains(mg) ) {
    		throw new MeetingException(MeetingException.NOT_EXISTING_MEETING_GROUP);
    	} else if ( u == null ) {
			throw new MeetingException(MeetingException.NOT_EXISTING_USER_MEMBER);
		} else if ( mg.getAssignment().getOrganizer().equals(u) ) {
			throw new MeetingException(MeetingException.USER_IS_ALREADY_THE_ORGANIZER);
		} else if ( mg.getCoorganizers().contains(u) ) {
			throw new MeetingException(MeetingException.USER_IS_ALREADY_A_COORGANIZER);
		} else {
			if ( mg.getMembers().contains(u) ) // member -> coorganizer
				mg.removeMember(u);
			mg.addCoorganizer(u);
    	}
    	return mg;
    }
    
    /**
   	* Method to add a memeber to a meeting group
   	* @return type of User
   	*/
    public MeetingGroup addMember(String nameMeetingGroup, String email, String pwd) throws MeetingException {
    	MeetingGroup mg = this.searchMeetingGroupByName(nameMeetingGroup);
    	User u = this.searchUserByEmail(email);
    	if ( !meetingGroups.contains(mg) ) {
    		throw new MeetingException(MeetingException.NOT_EXISTING_MEETING_GROUP);
    	} else if ( u == null ) {
    		throw new MeetingException(MeetingException.NOT_EXISTING_USER_MEMBER);
		} else if ( mg.getMembers().contains(u) ) {
    		throw new MeetingException(MeetingException.USER_IS_ALREADY_A_MEMBER);
		} else if ( mg.getAssignment().getOrganizer().equals(u) ) {
    		throw new MeetingException(MeetingException.USER_IS_ALREADY_THE_ORGANIZER);
		} else if ( mg.getCoorganizers().contains(u) ) {
    		throw new MeetingException(MeetingException.USER_IS_ALREADY_A_COORGANIZER);
		} else {
			mg.addMember(u);
    	}
    	return mg;
    }
    
    /**
   	* Method to show all the users in the system 
   	* @return type of MeetingGroup
   	*/
    public MeetingGroup listAll(String nameMeetingGroup) throws MeetingException {
    	MeetingGroup mg = this.searchMeetingGroupByName(nameMeetingGroup);
    	if ( mg == null )
    		throw new MeetingException(MeetingException.NOT_EXISTING_MEETING_GROUP);
    	return mg;
    }
    
    /**
   	* Method to show all the users in the system 
   	* @return type of User
   	*/
    public User addInterest(String email, String pwd, String interest) throws MeetingException {
    	User u = this.searchUserByEmail(email);
		if ( !users.contains(u) ) {
    		throw new MeetingException(MeetingException.NOT_EXISTING_USER_MEMBER);
		} else if ( u.getInterests().size() == 5 || u.getInterests().contains(interest) ) {
    		throw new MeetingException(MeetingException.MAXIMUM_NUMBER_OF_INTERESTS_PER_USER);
		} else {
			u.addInterest(interest);
		}
    	return u;
    }
    
    /**
   	* Method to search a meeting
   	* 
   	*/
    public void searchMeeting(String email, String pwd) throws MeetingException {
    	User u = this.searchUserByEmail(email);
		if ( !users.contains(u) ) {
    		throw new MeetingException(MeetingException.NOT_EXISTING_USER_MEMBER);
		} else {
			StringBuilder sb = new StringBuilder();
			ArrayList<String> interests = u.getInterests();
			for (int i = 0; i < interests.size(); i++) {
				String interest_i = interests.get(i);
				sb.append("Checking interest .....").append(interest_i).append("\n");
				for (int j = 0; j < meetingGroups.size(); j++) {
					String meetinGroup_j = meetingGroups.get(j).getName();
					boolean match = meetinGroup_j.toLowerCase().contains(interest_i.toLowerCase()) || interest_i.matches( meetinGroup_j );
					if (match) {
						sb.append("Matching MeetingGroup ").append(meetinGroup_j).append(" for interest ").append(interest_i);
					} else {
						sb.append("No Matching MeetingGroup for interest ").append(interest_i);
					}
					if( i<interests.size()-1 && j<meetingGroups.size()) sb.append("\n");
				}
			}
			System.out.println(sb.toString());
		}
    }
    
    /**
   	* Method to show all the users in the system 
   	* @return type of User
   	*/
    public Place addPlace(String namePlace, String address, String zone, 
		     String privateResidenceStr, String nameCountry) throws MeetingException {
    	Country country = new Country(nameCountry);
    	Boolean privateResidence = (privateResidenceStr.equals("yes")) ? true : false ;
    	Place p = new Place(namePlace, address, zone, privateResidence, country);
    	if ( places.contains(p) ) {
    		throw new MeetingException(MeetingException.PLACE_ALREADY_EXISTS);
    	} else {
    		p.setId(places.size()+1); // set new id
    		places.add(p);			  // add place to manager
    	}
    	return p;
    }
    
    /**
   	* Method to assign a place to a given meeting group
   	* @return type of MeetingGroup
   	*/
    public MeetingGroup assignPlaceMG(String idPlace, String nameMeetingGroup) throws MeetingException {
    	MeetingGroup mg = this.searchMeetingGroupByName(nameMeetingGroup);
    	if ( !meetingGroups.contains(mg) ) {
    		throw new MeetingException(MeetingException.MEETING_GROUP_NOT_FOUND);
    	} else {
    		Integer place_id = Integer.parseInt(idPlace);
    		if ( place_id > places.size() ) {
        		throw new MeetingException(MeetingException.PLACE_NOT_FOUND);
    		} else {
        		Place p = places.get( place_id-1 );
    			if ( mg.getPlaces().contains(p) ) {
            		throw new MeetingException(MeetingException.PLACE_ALREADY_IN_MEETING_GROUP);
				} else {
					mg.addPlace(p);
				}
			}
    	}
    	return mg;
    }
    
    /**
   	* Method to add a meeting to a specific meeting group 
   	* @return type of MeetingGroup
   	*/
    public MeetingGroup addMeetingMG(String nameMeetingGroup, String idPlace, String description, String isDraftStr,
		       String attendeLimitStr, String waitListStr, String guestPerMemberStr, String attendeeTotalStr) throws MeetingException {
    	MeetingGroup mg = this.searchMeetingGroupByName(nameMeetingGroup);
    	if ( !meetingGroups.contains(mg) ) {
    		throw new MeetingException(MeetingException.MEETING_GROUP_NOT_FOUND);
    	} else {
    		Integer place_id = Integer.parseInt(idPlace);
    		if ( place_id > places.size() ) {
        		throw new MeetingException(MeetingException.PLACE_NOT_FOUND);
    		} else {
    		    Date date = new Date(System.currentTimeMillis());
    			Boolean isDraft = (isDraftStr.equals("1")) ? true : false;
    			Integer attendeLimit = Integer.parseInt(attendeLimitStr);
    			Integer waitList = (waitListStr.equals("1")) ? 1 : 0;
    			Integer guestPerMember = Integer.parseInt(guestPerMemberStr);
    			Integer attendeeTotal = Integer.parseInt(attendeeTotalStr);
        		Place p = places.get( place_id-1 );
				Meeting m = new Meeting(description, date, isDraft, attendeLimit, waitList, guestPerMember, attendeeTotal, mg, p);
    			if ( mg.getMeetings().contains(m) ) {
            		throw new MeetingException(MeetingException.MEETING_ALREADY_IN_GROUP);
				} else {
					meetings.add(m);
					mg.addMeeting(m);
				}
			}
    	}
    	return mg;
    }
    
    /**
   	* Method to add a meeting to a specific meeting group 
   	* 
   	*/
    public Answer addAnswer(String descriptionMeeting, String emailUser, String pwdUser, 
    		                String guestsStr, String attendingResultStr) throws MeetingException {
    	// set initial data
		Meeting m = this.searchMeetingByDescription(descriptionMeeting);
    	User u = this.searchUserByEmail(emailUser);
    	Integer guests = Integer.parseInt(guestsStr);
		AttendingResult attendingResult = (attendingResultStr.equals("yes")) ? AttendingResult.Yes : 
			                              ((attendingResultStr.equals("no")) ? AttendingResult.No :  
			                            	                                   AttendingResult.WantASpot);
		Answer a = new Answer(u, m, attendingResult, guests);
		
		// checkings
		if ( u == null ) {
    		throw new MeetingException(MeetingException.USER_NOT_FOUND);
    	} else if  ( m == null ) {
    		throw new MeetingException(MeetingException.MEETING_NOT_FOUND);
		} else if ( !m.getMeetingGroup().getMembers().contains(u) &&
			        !m.getMeetingGroup().getCoorganizers().contains(u) &&
			        !m.getMeetingGroup().getAssignment().getOrganizer().equals(u) ) {
			// No user nas member, coorganizer or organizer
			throw new MeetingException(MeetingException.USER_NOT_FOUND_IN_MG);
		} else if ( answers.contains(a) ) {
    		throw new MeetingException(MeetingException.ANSWER_ALREADY_FOUND_FOR_USER_MEETING);
		} else if ( a.getGuests() > m.getGuestsPerMember() ) {
    		throw new MeetingException(MeetingException.ANSWER_EXCEEDS_GUESTS_PER_MEETING);
		} else {
			if ( m.getAttendeLimit().equals(new Integer(1)) && a.getGuests()+new Integer(1) >= m.getAttendeeTotal() ) {
	        	if ( !m.getWaitList().equals(new Integer(1)) ) {
					throw new MeetingException(MeetingException.THE_MEETING_IS_FULL);
				} else {
					System.out.println("The meeting is full but has waiting List. Wait in Waiting List");
				    a.setAttendingResult(AttendingResult.WantASpot);
				}
			} else if( a.getGuests().equals(new Integer(0)) || 
					   attendingResult.equals(AttendingResult.No)) {
				System.out.println("Answer with Not attendance to Meeting to be added");
			}
			if ( u.equals(m.getMeetingGroup().getAssignment().getOrganizer()) && a.getIsAttending() ) {
				m.setIsDraft(false);
			}
			if (m.getIsDraft()) System.out.println("This meeting is a draft. Please wait organizer confirmation");
			
			answers.add(a);
			u.addAnswer(a);
			m.addAnswer(a);
			return a;
		}
    }
    
    /**
   	* Method show all the answers in the specified meeting
   	* 
   	*/
    public void listMeetingAnswers(String descriptionMeeting) throws MeetingException {
		StringBuilder sb = new StringBuilder();
    	Meeting m = this.searchMeetingByDescription(descriptionMeeting);
		if ( m == null || m.getAnswers().size() == 0 ) { // No meeting found
    		throw new MeetingException(MeetingException.NO_MEETING_OR_NO_ANSWERS);
		} else {
			sb.append("Attending to Meeting");
			for (int i = 0; i < m.getAnswers().size(); i++)
				if ( m.getAnswers().get(i).getAttendingResult().equals(AttendingResult.Yes) )
					sb.append(m.getAnswers().get(i));
			
			sb.append("\n").append("In waiting list");
			for (int i = 0; i < m.getAnswers().size(); i++)
				if ( m.getAnswers().get(i).getAttendingResult().equals(AttendingResult.WantASpot) )
					sb.append(m.getAnswers().get(i));
			
			sb.append("\n").append("No Attending to Meeting");
			for (int i = 0; i < m.getAnswers().size(); i++)
				if ( m.getAnswers().get(i).getAttendingResult().equals(AttendingResult.No) )
					sb.append(m.getAnswers().get(i));
		
		}
		System.out.println(sb.toString());
    }
}
