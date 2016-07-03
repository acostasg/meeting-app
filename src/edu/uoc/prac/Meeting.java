package edu.uoc.prac;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
*
* Meeting class definition
*
* @author Albert Costas
*
*/

public class Meeting {

	/** attributes definition */
	private String description;
	private Date date;
	private Boolean isDraft;
	private Integer attendeLimit;
	private Integer waitList;
	private Integer guestsPerMember;
	private Integer attendeeTotal;
	private MeetingGroup meetingGroup;
	private Place place;
	private ArrayList<Answer> answers;

	/**
    * Constructor
    */
    public Meeting(String description, Date date, Boolean isDraft,
    		       Integer attendeLimit, Integer waitList, Integer guestsPerMember,
    		       Integer attendeeTotal, MeetingGroup meetingGroup, Place place) {

    	this.description = description;
    	this.date = date;
    	this.isDraft = isDraft;
    	this.attendeLimit = attendeLimit;
    	this.waitList = waitList;
    	this.guestsPerMember = guestsPerMember;
    	this.attendeeTotal = attendeeTotal;
    	this.meetingGroup = meetingGroup;
    	this.place = place;
    	this.place.addMeeting(this);
    	this.answers = new ArrayList<Answer>();
    }
	
	@Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;
        if (object != null && object instanceof Meeting)
        {
        	sameSame = this.description.equals(((Meeting) object).getDescription());
        }
        return sameSame;
    }
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        // Basic info
        sb.append("Description ").append(this.getDescription());
        sb.append(" Date ").append(new SimpleDateFormat("dd-MM-yyyy").format(this.getDate()));
        sb.append(" AttendeeLimit: ").append(this.getAttendeLimit());
        sb.append(" guestsPerMember: ").append(this.getGuestsPerMember());
        sb.append(" attendeeTotal ").append(this.getAttendeeTotal()).append("\n");
        // Related place info
        sb.append("Related Place to Meeting info: ").append(this.getPlace()).append("\n");
        return sb.toString();
    }
    
    /**  Getters methods **/
	public String            getDescription()     { return this.description;     }
    private Date             getDate()            { return this.date;            }
	public Boolean           getIsDraft()         { return this.isDraft;         }
	public Integer           getAttendeLimit()    { return this.attendeLimit;    }
	public Integer           getWaitList()        { return this.waitList;        }
	public Integer           getGuestsPerMember() { return this.guestsPerMember; }
	public Integer           getAttendeeTotal()   { return this.attendeeTotal;   }
	private Place            getPlace ()          { return this.place;           }
	public MeetingGroup      getMeetingGroup()    { return this.meetingGroup;    }
	public ArrayList<Answer> getAnswers()         { return this.answers;         }
	
	/** Setters methods **/
    public void setIsDraft(Boolean isDraft) { this.isDraft=isDraft; }

	/**
	* Method to add an Answer
	* @param type of {@link Answer}
	*/
	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}

}