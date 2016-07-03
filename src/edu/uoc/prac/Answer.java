package edu.uoc.prac;

/**
*
* Answer class definition
*
* @author Albert Costas
*
*/

public class Answer {

	/** attributes definition */
	private Boolean attending;
	private Integer guests;
	private AttendingResult attendingResult;
	private User user;
	private Meeting meeting;

	/**
    * Constructor
    */
    public Answer(User user, Meeting meeting, AttendingResult attendingResult, Integer guests) {
    	this.attending = ( attendingResult==AttendingResult.Yes ) ? true : false;
    	this.guests = guests;
    	this.attendingResult = attendingResult;
    	this.user = user;
    	this.meeting = meeting;
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;
        if (object != null && object instanceof Answer)
        {
        	sameSame = this.meeting.getDescription().equals(((Answer) object).meeting.getDescription()) &&
        			   this.user.getEmail().equals(((Answer) object).user.getEmail()) &&
        			   this.user.getPassword().equals(((Answer) object).user.getPassword());
        }
        return sameSame;
    }
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        // User info
        sb.append("User information: ").append(this.getUser());
        // Answer info
        sb.append("Guests Coming: ").append(this.getGuests()).append("\n");
        sb.append("Attending Result: ").append( (this.getAttendingResult().equals(AttendingResult.WantASpot)) ? "WantASpot": this.getAttendingResult().toString().toLowerCase()).append("\n");
        // Meeting info
        String s = this.getMeeting().toString();
        s = s.substring(s.indexOf('\n')+1); // remove first empty line
        sb.append("Information Meeting: ").append(s).append("\n");
        return sb.toString();
    }
    
    /** Getters Methods **/
    public Meeting         getMeeting()          { return this.meeting;          }
    public User            getUser()             { return this.user;             }
    public Integer         getGuests()           { return this.guests;           }
    public Boolean         getIsAttending()      { return this.attending;        }
    public AttendingResult getAttendingResult()  { return this.attendingResult;  }
    
    /** Setters methods **/
    public void setAttendingResult(AttendingResult attendingResult) { this.attendingResult=attendingResult; }

}