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
public class MuscleBlock extends SugarRecord<MuscleBlock> {
	private Exercise exercise;
	private MusclePart musclePart;
	private String block;

	/**
	 * @param arg0
	 */
	public MuscleBlock(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
     */
	public MuscleBlock(Context arg0, Exercise exercise, MusclePart musclePart, String block) {
		super(arg0);
		this.exercise = exercise;
		this.musclePart = musclePart;
		this.block = block;
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
	 * @return the musclePart
	 */
	public MusclePart getMusclePart() {
		return musclePart;
	}

	/**
	 * @param musclePart the musclePart to set
	 */
	public void setMusclePart(MusclePart musclePart) {
		this.musclePart = musclePart;
	}

	/**
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
	}

}
