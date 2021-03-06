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
public class Serie extends SugarRecord<Serie> {
	private int idserie = 0;
	private long exerciseunit;
	private int weight;
	private int repetition;
	private int pause;
	private String start = "1970-01-01 00:00:00";
	private String finish = "1970-01-01 00:00:00";
	private boolean sync = false;

	/**
	 * @param arg0
	 */
	public Serie(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param exerciseUnit
	 * @param weight
	 * @param repetition
	 * @param pause
	 * @param start
	 * @param finish
	 */
	public Serie(Context arg0, long exerciseUnit, int weight, int repetition, int pause) {
		super(arg0);
		this.exerciseunit = exerciseUnit;
		this.weight = weight;
		this.repetition = repetition;
		this.pause = pause;
	}
	
	/**
	 * @param arg0
	 * @param id
	 * @param exerciseUnit
	 * @param weight
	 * @param repetition
	 * @param pause
	 * @param start
	 * @param finish
	 */
	public Serie(Context arg0, int id, long exerciseUnit, int weight, int repetition, int pause) {
		super(arg0);
		this.idserie = id;
		this.exerciseunit = exerciseUnit;
		this.weight = weight;
		this.repetition = repetition;
		this.pause = pause;
		this.sync = true;
	}
	
	/**
	 * @return the id
	 */
	public int getWebId() {
		return idserie;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.idserie = id;
	}

	/**
	 * @return the exerciseUnit
	 */
	public long getExerciseUnit() {
		return exerciseunit;
	}

	/**
	 * @param exerciseUnit the exerciseUnit to set
	 */
	public void setExerciseUnit(long exerciseUnit) {
		this.exerciseunit = exerciseUnit;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the repetition
	 */
	public int getRepetition() {
		return repetition;
	}

	/**
	 * @param repetition the repetition to set
	 */
	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}

	/**
	 * @return the pause
	 */
	public int getPause() {
		return pause;
	}

	/**
	 * @param pause the pause to set
	 */
	public void setPause(int pause) {
		this.pause = pause;
	}

	/**
	 * @return the from
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param from the from to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the to
	 */
	public String getFinish() {
		return finish;
	}

	/**
	 * @param to the to to set
	 */
	public void setFinish(String finish) {
		this.finish = finish;
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
		return "Weight: "+getWeight()+"kg, Repetition: "+getRepetition()+", Rest: "+getPause()+"s";
	}
	
	public int getExerciseUnitWeb() {
		ExerciseUnit eUnit = SugarRecord.findById(ExerciseUnit.class, getExerciseUnit());
		return eUnit.getWebId();
	}
	
	public String JSONString() {
		return "{\"id\":"+this.getWebId()+",\"exerciseunitId\":"+getExerciseUnitWeb()+",\"weight\":"+weight+",\"repetition\":"+repetition+",\"pause\":"+pause+",\"start\":\""+start+"\",\"finish\":\""+finish+"\"}";
	}
}
