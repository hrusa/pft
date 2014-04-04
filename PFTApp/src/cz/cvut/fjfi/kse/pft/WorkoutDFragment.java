/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Petr Hruška
 *
 */
public class WorkoutDFragment extends DialogFragment{
	Context mContext;
	/**
	 * 
	 */
	public WorkoutDFragment() {
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
		View view = inflater.inflate(R.layout.fragmentd_workout, null);
		setCancelable(false);
		return view;
	}
}
