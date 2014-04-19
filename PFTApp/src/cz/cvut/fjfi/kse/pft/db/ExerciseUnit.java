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
	private boolean done = false;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Exercise ex = Exercise.findById(Exercise.class, getExercise());
		return ex.getName();
	}
}
