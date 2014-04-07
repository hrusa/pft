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
	private String name;
	private String email;
	private String birth;	//YYYY-MM-DD
	private int gender;
	
	/**
	 * @param arg0
	 */
	public Trainee(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param arg0
	 * @param birth
	 */
	public Trainee(Context arg0, String name, String email, String birth, int gender) {
		super(arg0);
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
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

}
