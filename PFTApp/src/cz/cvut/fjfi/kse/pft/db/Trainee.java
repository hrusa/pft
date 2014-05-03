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
public class Trainee extends SugarRecord<Trainee> {
	private int idtrainee = 0;
	private String name;
	private String email;
	private String birth;	//YYYY-MM-DD
	private int gender;		//0 = male, 1 = female
	private int experience = -1;	//0 = beginner, 1 = advanced, 2 = experienced
	private int goal = -1;		//0 = lose fat, 2 = gain strength, 3 = bulking, 4 = cutting
	
	/**
	 * @param arg0
	 */
	public Trainee(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param arg0
	 * @param name
	 * @param email
	 * @param birth
	 * @param gender
	 */
	public Trainee(Context arg0, String name, String email, String birth, int gender) {
		super(arg0);
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
	}
	
	/**
	 * @param arg0
	 * @param id
	 * @param name
	 * @param email
	 * @param birth
	 * @param gender
	 * @param experience
	 * @param goal
	 */
	public Trainee(Context arg0, int id, String name, String email, String birth, int gender, int experience, int goal) {
		super(arg0);
		this.idtrainee = id;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
		this.experience = experience;
		this.goal = goal;
	}
	
	/**
	 * @return the id
	 */
	public int getWebId() {
		return idtrainee;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.idtrainee = id;
	}
	
	/**
	 * @return the experience
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(int experience) {
		this.experience = experience;
	}

	/**
	 * @return the goal
	 */
	public int getGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(int goal) {
		this.goal = goal;
	}

	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the birth
	 */
	public String getBirth() {
		return birth;
	}
	
	/**
	 * @param birth the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String JSONString() {
		return "{\"id\":"+this.getId()+",\"name\":\""+name+"\",\"email\":\""+email+"\",\"birth\":\""+birth+"\",\"gender\":"+gender+",\"experience\":"+experience+",\"goal\":"+goal+"}";
	}
}
