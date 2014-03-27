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
public class Training extends SugarRecord<Training> {
	private Trainee trainee;
	private String name;

	/**
	 * @param arg0
	 */
	public Training(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param trainee
	 * @param name
	 */
	public Training(Context arg0, Trainee trainee, String name) {
		super(arg0);
		this.trainee = trainee;
		this.name = name;
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

}
