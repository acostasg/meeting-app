package edu.uoc.prac;

/**
*
* Assignment class definition
*
* @author Albert Costas
*
*/

public class Assignment {
	
	/** attributes definition */
	private Integer fixedFee;
	private Double percentage;
	private Organizer organizer;
	private MeetingGroup meetingGroup;
	
	/**
    * Constructor
    */
    public Assignment(Organizer organizer, MeetingGroup meetingGroup) {
    	this.fixedFee = 15;
    	this.percentage = 0.1;
    	this.setOrganizer(organizer);
    	this.setMeetingGroup(meetingGroup);
    }
    
    /** Getters methods **/
    public Integer getFixedFee()    { return this.fixedFee;   }
   	public Double getPercentage()   { return this.percentage; }
    public Organizer getOrganizer() { return this.organizer;  }
    
    /** Setters methods **/
    public void setMeetingGroup(MeetingGroup meetingGroup) { this.meetingGroup = meetingGroup; }
    public void setOrganizer(Organizer organizer) {
	     this.organizer = organizer;
	     this.organizer.addAssignment(this);
	}

}
