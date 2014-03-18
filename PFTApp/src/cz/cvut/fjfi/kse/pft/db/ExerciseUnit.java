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
public class ExerciseUnit extends SugarRecord<ExerciseUnit> {
	private Exercise exercise;
	private Workout workout;

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
	public ExerciseUnit(Context arg0, Exercise exercise, Workout workout) {
		super(arg0);
		this.exercise = exercise;
		this.workout = workout;
	}

	/**
	 * @return the exercise
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * @param exercise the exercise to set
	 */
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * @return the workout
	 */
	public Workout getWorkout() {
		return workout;
	}

	/**
	 * @param workout the workout to set
	 */
	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

}
