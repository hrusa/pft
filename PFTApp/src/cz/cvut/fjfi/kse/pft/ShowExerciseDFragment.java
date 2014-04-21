/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author Petr Hruška
 *
 */
public class ShowExerciseDFragment extends DialogFragment{
	View view;
	Bundle args = new Bundle();
	Button weightBtn, pauseBtn;
	/**
	 * 
	 */
	public ShowExerciseDFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragmentd_showexercise, null);
		args = getArguments();
		weightBtn = (Button) view.findViewById(R.id.weight_button);
		pauseBtn = (Button) view.findViewById(R.id.pause_button);
		
		weightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				args.putBoolean("weight", true);
				startActivity(new Intent(getActivity(), GraphActivity.class).putExtras(args));
				dismiss();
			}
		});
		pauseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				args.putBoolean("weight", false);
				startActivity(new Intent(getActivity(), GraphActivity.class).putExtras(args));
				dismiss();
			}
		});
		return view;
	}
}
