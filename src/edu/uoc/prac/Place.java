package edu.uoc.prac;
import java.util.ArrayList;

/**
*
* Place class definition
*
* @author Albert Costas
*
*/

public class Place {

    /** attributes definition */
	private String name;
	private String address;
	private String zone;
	private Boolean privateResidence;
	private Integer id;
	private Country country;
	private MeetingGroup meetingGroup;
	private ArrayList<Meeting> meetings;
	
	/**
    * Constructor
    */
	public Place(String name, String address, String zone, 
			     Boolean privateResidence, Country country) {
		this.name = name;
		this.address = address;
		this.zone = zone;
		this.privateResidence = privateResidence;
		this.setId(0);
		this.country = country;
		this.country.addPlace(this);
		this.meetings = new ArrayList<Meeting>();
	}
	
	@Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;
        if (object != null && object instanceof Place)
        {
            sameSame = this.name.equals(((Place) object).getName()) && // check place names
            		   this.getCountry().getName().equals(((Place) object).getCountry().getName()); // check country names
        }
        return sameSame;
    }
	
	@Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identifier: ").append(this.getId());
        sb.append(" Name: ").append(this.getName());
        sb.append(" Address: ").append(this.getAddress());
        sb.append("Zone: ").append(this.getZone());
        sb.append(" Private Residence: ").append( (this.getPrivateResidence()) ? "Yes" : "No" );
        sb.append(" Country: ").append(this.getCountry().getName());
		return sb.toString();
    }

    /** Getters methods **/
	public Integer getId()      { return this.id;   }
	public String  getName()    { return this.name; }
	public String  getZone()    { return this.zone; }
	public Country getCountry() { return this.country; }
	public String  getAddress() { return this.address; }
	public Boolean getPrivateResidence() { return this.privateResidence; }
	
	/** Setters method **/
	public void setId(Integer id) { this.id = id; }
	public void setMeetingGroup(MeetingGroup meetingGroup) { this.meetingGroup = meetingGroup; }
	
	/**
	* Method to add a meeting
	* @param type of {@link Meeting}
	*/
	public void addMeeting(Meeting meeting) {
	    	this.meetings.add(meeting);
	}
}
