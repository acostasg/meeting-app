package edu.uoc.prac;
import java.util.ArrayList;

/**
*
* Organizer class definition
*
* @author Albert Costas
*
*/

public class Organizer extends User {
	
	/** attributes definition */
	private String phone;	
	private ArrayList<Assignment> assigments;

	/**
    * Constructor
    */
    public Organizer(String email, String password, String phone) {
    	super(email, password);
    	this.phone = phone;
    	this.assigments = new ArrayList<Assignment>();
	}
    
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Email: ").append(this.getEmail());
        sb.append(" Pwd: ").append(this.getPassword());
        sb.append(" Phone: ").append(this.getPhone()).append("\n");
        return sb.toString();
    }
    
    /**
	* Getter method
	* @return type of String
	*/
	public String getPhone() { return this.phone; }
	
	/**
	* Method to add an assignment
	* @param type of Assignment
	*/
	public void addAssignment(Assignment assignment) {
	     this.assigments.add(assignment);
	}

}
