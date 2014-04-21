/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * @author Petr Hruška
 * 
 */
public class GraphActivity extends FragmentActivity {
	Bundle args = new Bundle();

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		args = getIntent().getExtras();
		setContentView(R.layout.activity_graph);
		if (args.getBoolean("statistics")) {
			if (args.getBoolean("weight")) {
				ShowWeightGraphFragment fragment = new ShowWeightGraphFragment();
				fragment.setArguments(args);
				Log.i("GraphActivity", "Pokus o spuštění weightgraph");
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "ShowWeightGraph")
						.addToBackStack(null).commit();
			} else {
				ShowPauseGraphFragment fragment = new ShowPauseGraphFragment();
				fragment.setArguments(args);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "ShowPauseGraph")
						.addToBackStack(null).commit();
			}
		} else {
			ShowMeasureGraphFragment fragment = new ShowMeasureGraphFragment();
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "ShowMeasureGraph")
					.addToBackStack(null).commit();
		}
	}
}
