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
public class ExerciseUnit extends SugarRecord<ExerciseUnit> {
	private long exercise;
	private long workout;

	/**
	 * @param arg0
	 */
	public ExerciseUnit(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param exercise
	 * @param workout
	 */
	public ExerciseUnit(Context arg0, long exercise, long workout) {
		super(arg0);
		this.exercise = exercise;
		this.workout = workout;
	}

	/**
	 * @return the exercise
	 */
	public long getExercise() {
		return exercise;
	}

	/**
	 * @param exercise the exercise to set
	 */
	public void setExercise(long exercise) {
		this.exercise = exercise;
	}

	/**
	 * @return the workout
	 */
	public long getWorkout() {
		return workout;
	}

	/**
	 * @param workout the workout to set
	 */
	public void setWorkout(long workout) {
		this.workout = workout;
	}

}
