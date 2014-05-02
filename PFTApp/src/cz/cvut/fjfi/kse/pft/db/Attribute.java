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
public class Attribute extends SugarRecord<Attribute>{
	private int idattribute = 0;
	private String name;

	/**
	 * Constructors of entity Attribute
	 */
	public Attribute(Context ctx) {
		super(ctx);
	}

	/**
	 * @param ctx
	 * @param name
	 */
	public Attribute(Context ctx, String name) {
		super(ctx);
		this.name = name;
	}
	
	/**
	 * @param ctx
	 * @param id
	 * @param name
	 */
	public Attribute(Context ctx, int id, String name) {
		super(ctx);
		this.idattribute = id;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public int getWebId() {
		return idattribute;
	}

	/**
	 * @param id, the id to set
	 */
	public void setWebId(int id) {
		this.idattribute = id;
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
		return "{\"id\":"+this.getId()+",\"name\":\""+name+"\"}";
	}
}
