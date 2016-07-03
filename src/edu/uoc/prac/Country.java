package edu.uoc.prac;

import java.util.ArrayList;

/**
*
* Country class definition
*
* @author Albert Costas
*
*/

public class Country {

    /** attributes definition */
	private String name;
	private ArrayList<Place> places;

	/**
    * Constructor
    */
    public Country(String name) {
    	this.name = name;
    	this.places = new ArrayList<Place>();
    }
    
    /** Getter method **/
	public String getName() { return this.name;	}
	
	/**
	* Method to add a place
	* @param type of {@link Place}
	*/
	public void addPlace(Place place) {
		this.places.add(place);
	}
}
