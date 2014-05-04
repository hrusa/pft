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
	private boolean sync = false;

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
		this.sync = true;
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

	/**
	 * @return the sync
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * @param sync the sync to set
	 */
	public void setSync(boolean sync) {
		this.sync = sync;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
	public int getTraineeWeb() {
		Trainee tre = Trainee.findById(Trainee.class, getTrainee());
		return tre.getWebId();
	}
	
	public String JSONString() {
		return "{\"id\":"+this.getWebId()+",\"traineeId\":"+getTraineeWeb()+",\"name\":\""+name+"\"}";
	}
}
