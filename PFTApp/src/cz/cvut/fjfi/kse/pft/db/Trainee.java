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
public class Trainee extends SugarRecord<Trainee> {
	private String birth;	//YYYY-MM-DD
	
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
	public Trainee(Context arg0, String birth) {
		super(arg0);
		this.birth = birth;
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
