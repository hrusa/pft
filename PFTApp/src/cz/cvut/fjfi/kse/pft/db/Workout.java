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
public class Workout extends SugarRecord<Workout> {
	private long training;
	private String name;
	private String date;	//YYYY-MM-DD
	private boolean done = false;

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
	public Workout(Context arg0, long training, String name, String date) {
		super(arg0);
		this.training = training;
		this.name = name;
		this.date = date;
	}

	/**
	 * @return the training
	 */
	public long getTraining() {
		return training;
	}

	/**
	 * @param training the training to set
	 */
	public void setTraining(long training) {
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

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
