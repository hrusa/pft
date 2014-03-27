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
public class MusclePart extends SugarRecord<MusclePart> {
	private MuscleGroup muscleGroup;
	private String name;

	/**
	 * @param arg0
	 */
	public MusclePart(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param muscleGroup
	 * @param name
	 */
	public MusclePart(Context arg0, MuscleGroup muscleGroup, String name) {
		super(arg0);
		this.muscleGroup = muscleGroup;
		this.name = name;
	}

	/**
	 * @return the muscleGroup
	 */
	public MuscleGroup getMuscleGroup() {
		return muscleGroup;
	}

	/**
	 * @param muscleGroup the muscleGroup to set
	 */
	public void setMuscleGroup(MuscleGroup muscleGroup) {
		this.muscleGroup = muscleGroup;
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
