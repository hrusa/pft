/**
 * 
 */
package cz.cvut.fjfi.kse.pft.db;

/**
 * @author Petr Hruška
 *
 */
public interface ReferenceTableInterface {
	public String getName();
	public void setName(String name);
	public int getWebId();
	public void setWebId(int id);
	public String JSONString();
}
