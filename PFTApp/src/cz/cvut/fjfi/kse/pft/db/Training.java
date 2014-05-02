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
	private int idtraining = 0;
	private long trainee;
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
	public Training(Context arg0, long trainee, String name) {
		super(arg0);
		this.trainee = trainee;
		this.name = name;
	}
	
	/**
	 * @param arg0
	 * @param id
	 * @param trainee
	 * @param name
	 */
	public Training(Context arg0, int id, long trainee, String name) {
		super(arg0);
		this.idtraining = id;
		this.trainee = trainee;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public int getWebId() {
		return idtraining;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.idtraining = id;
	}

	/**
	 * @return the trainee
	 */
	public long getTrainee() {
		return trainee;
	}

	/**
	 * @param trainee the trainee to set
	 */
	public void setTrainee(long trainee) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
	public String JSONString() {
		return "{\"id\":"+this.getId()+",\"traineeId\":"+trainee+",\"name\":\""+name+"\"}";
	}
}
