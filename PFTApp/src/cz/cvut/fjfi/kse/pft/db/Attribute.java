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
