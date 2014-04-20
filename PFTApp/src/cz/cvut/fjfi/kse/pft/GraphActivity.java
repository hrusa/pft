/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author Petr Hruška
 * 
 */
public class GraphActivity extends FragmentActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_graph);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container, new ShowMeasureGraphFragment(),
						"ShowMeasureGraph").addToBackStack(null).commit();
	}

}
