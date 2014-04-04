/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * @author Petr Hruška
 *
 */
public class BasicInfoDFragment extends DialogFragment{
	Context mContext;
	/**
	 * 
	 */
	public BasicInfoDFragment() {
		// TODO Auto-generated constructor stub
		mContext = getActivity();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragmentd_basic_info, null);
		/*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.title_fragmentd_basicinfo)
		.setView(view)
		.setCancelable(false)
		.setNeutralButton(R.string.previous, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setNeutralButton(R.string.next, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.show();*/
		/*getDialog().setTitle(R.string.title_fragmentd_basicinfo);
		//setCancelable(false);
*/		return view;
	}
	
	public void onRadioButtonClicked (View view) {
		// Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.male_radio:
	            if (checked)
	                Toast.makeText(mContext, "Male madafaka", Toast.LENGTH_SHORT).show();
	            break;
	        case R.id.female_radio:
	            if (checked)
	            	Toast.makeText(mContext, "Female biatch", Toast.LENGTH_SHORT).show();
	            break;
	    }
	}
}
