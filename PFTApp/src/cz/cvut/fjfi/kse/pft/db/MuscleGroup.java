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
public class MuscleGroup extends SugarRecord<MuscleGroup> implements ReferenceTableInterface{
	private int idmusclegroup = 0;
	private String name;

	/**
	 * Constructors of entity MuscleGroup
	 */
	public MuscleGroup(Context ctx) {
		super(ctx);
	}

	/**
	 * @param ctx
	 * @param name
	 */
	public MuscleGroup(Context ctx, String name) {
		super(ctx);
		this.name = name;
	}
	
	/**
	 * @param ctx
	 * @param id
	 * @param name
	 */
	public MuscleGroup(Context ctx, int id, String name) {
		super(ctx);
		this.idmusclegroup = id;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public int getWebId() {
		return idmusclegroup;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.idmusclegroup = id;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

	public String JSONString() {
		return "{\"id\":"+this.getWebId()+",\"name\":\""+name+"\"}";
	}
}
