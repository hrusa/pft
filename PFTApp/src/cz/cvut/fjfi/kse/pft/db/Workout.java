/**
 * 
 */
package cz.cvut.fjfi.kse.pft.db;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * @author Petr Hruška
 *
 */
public class Workout extends SugarRecord<Workout> {
	Training training;
	String name;
	String date;	//YYYY-MM-DD

	/**
	 * @param arg0
	 */
	public Workout(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param training
	 * @param name
	 * @param date
	 */
	public Workout(Context arg0, Training training, String name, String date) {
		super(arg0);
		this.training = training;
		this.name = name;
		this.date = date;
	}

	/**
	 * @return the training
	 */
	public Training getTraining() {
		return training;
	}

	/**
	 * @param training the training to set
	 */
	public void setTraining(Training training) {
		this.training = training;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

}
