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
public class Exercise extends SugarRecord<Exercise> {
	private String name;
	private Difficulty difficulty;
	private String description;
	private String video;
	private long musclegroup;

	/**
	 * @param arg0
	 */
	public Exercise(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param name
	 * @param difficulty
	 * @param description
	 * @param video
	 */
	public Exercise(Context arg0, String name, Difficulty difficulty,
			String description, String video, long group) {
		super(arg0);
		this.name = name;
		this.difficulty = difficulty;
		this.description = description;
		this.video = video;
		this.musclegroup = group;
	}

	/**
	 * @return the group
	 */
	public long getGroup() {
		return musclegroup;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(long group) {
		this.musclegroup = group;
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
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

}
