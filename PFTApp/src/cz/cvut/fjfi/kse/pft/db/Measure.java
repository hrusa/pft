/**
 * 
 */
package cz.cvut.fjfi.kse.pft.db;

import com.orm.SugarRecord;

import android.content.Context;

/**
 * @author Petr Hruška
 *
 */
public class Measure extends SugarRecord<Measure> {
	private Trainee trainee;
	private Attribute attribute;
	private String date;	//YYYY-MM-DD
	private int value;
	
	/**
	 * @param arg0
	 */
	public Measure(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param trainee
	 * @param attribute
	 * @param date
	 * @param value
	 */
	public Measure(Context arg0, Trainee trainee, Attribute attribute,
			String date, int value) {
		super(arg0);
		this.trainee = trainee;
		this.attribute = attribute;
		this.date = date;
		this.value = value;
	}

	/**
	 * @return the trainee
	 */
	public Trainee getTrainee() {
		return trainee;
	}

	/**
	 * @param trainee the trainee to set
	 */
	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	/**
	 * @return the attribute
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
