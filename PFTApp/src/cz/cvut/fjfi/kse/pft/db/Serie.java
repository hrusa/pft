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
public class Serie extends SugarRecord<Serie> {
	private ExerciseUnit exerciseUnit;
	private int weight;
	private int repetition;
	private int pause;
	private String from;
	private String to;

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
	 * @param from
	 * @param to
	 */
	public Serie(Context arg0, ExerciseUnit exerciseUnit, int weight,
			int repetition, int pause, String from, String to) {
		super(arg0);
		this.exerciseUnit = exerciseUnit;
		this.weight = weight;
		this.repetition = repetition;
		this.pause = pause;
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the exerciseUnit
	 */
	public ExerciseUnit getExerciseUnit() {
		return exerciseUnit;
	}

	/**
	 * @param exerciseUnit the exerciseUnit to set
	 */
	public void setExerciseUnit(ExerciseUnit exerciseUnit) {
		this.exerciseUnit = exerciseUnit;
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
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

}
