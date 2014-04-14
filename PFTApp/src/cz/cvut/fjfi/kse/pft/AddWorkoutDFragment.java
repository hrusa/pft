/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Mastah
 *
 */
public class AddWorkoutDFragment extends DialogFragment{
	Bundle args = new Bundle();
	View view;
	EditText nameW;
	DatePicker dp;
	public AddWorkoutDFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		args = getArguments();
		view = inflater.inflate(R.layout.fragmentd_addworkout, null);
		Button cancel = (Button) view.findViewById(R.id.cancel_button);
		Button add = (Button) view.findViewById(R.id.add_button);
		nameW = (EditText) view.findViewById(R.id.name_editText);
		dp = (DatePicker) view.findViewById(R.id.workout_datePicker);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!nameW.getText().toString().isEmpty()) {
					Workout workout = new Workout(getActivity(), args.getLong("training"), nameW.getText().toString(), dp.getYear()+"-"+dp.getMonth()+"-"+dp.getDayOfMonth());
					workout.save();
				    ((TrainingFragment) getFragmentManager().findFragmentByTag("Training")).updateList(workout);
					dismiss();
				} else {
					Toast.makeText(getActivity(), "Vyplňte název", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return view;
	}
}
