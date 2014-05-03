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
public class Difficulty extends SugarRecord<Difficulty>{
	int iddifficulty = 0;
	private String name;
	
	/**
	 * Constructors of entity Difficulty
	 */
	public Difficulty(Context ctx) {
		super(ctx);
	}

	/**
	 * @param ctx
	 * @param name
	 */
	public Difficulty(Context ctx, String name) {
		super(ctx);
		this.name = name;
	}
	
	/**
	 * @param ctx
	 * @param id
	 * @param name
	 */
	public Difficulty(Context ctx, int id, String name) {
		super(ctx);
		this.iddifficulty = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getWebId() {
		return iddifficulty;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.iddifficulty = id;
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

	public String JSONString() {
		return "{\"id\":"+this.getWebId()+",\"name\":\""+name+"\"}";
	}
}
