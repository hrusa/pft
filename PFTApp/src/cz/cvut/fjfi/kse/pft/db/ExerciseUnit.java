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
	private int idexerciseunit = 0;
	private long exercise;
	private long workout;
	private boolean done = false;
	private boolean sync = false;

	/**
	 * @param ctx
	 */
	public ExerciseUnit(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param exercise
	 * @param workout
	 */
	public ExerciseUnit(Context ctx, long exercise, long workout) {
		super(ctx);
		this.exercise = exercise;
		this.workout = workout;
	}
	
	/**
	 * @param ctx
	 * @param id
	 * @param exercise
	 * @param workout
	 */
	public ExerciseUnit(Context ctx, int id, long exercise, long workout) {
		super(ctx);
		this.idexerciseunit = id;
		this.exercise = exercise;
		this.workout = workout;
		this.sync = true;
	}

	/**
	 * @return the id
	 */
	public int getWebId() {
		return idexerciseunit;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.idexerciseunit = id;
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
		Exercise ex = Exercise.findById(Exercise.class, getExercise());
		return ex.getName();
	}
	
	public String JSONString() {
		return "{\"id\":"+this.getWebId()+",\"exerciseId\":"+exercise+",\"workoutId\":"+workout+",\"done\":\""+done+"\"}";
	}
}
