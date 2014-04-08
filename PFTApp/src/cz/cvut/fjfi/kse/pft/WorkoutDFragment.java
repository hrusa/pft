/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import cz.cvut.fjfi.kse.pft.db.Trainee;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author Petr Hruška
 *
 */
public class WorkoutDFragment extends DialogFragment{
	View view;
	Spinner experience, goal;
	Bundle args;
	Trainee trainee;
	/**
	 * 
	 */
	public WorkoutDFragment() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		args = this.getArguments();
		view = inflater.inflate(R.layout.fragmentd_workout, null);
		trainee = Trainee.findById(Trainee.class, args.getLong("id"));
		Button previous = (Button) view.findViewById(R.id.previous_button);
		Button next = (Button) view.findViewById(R.id.next_button);
		experience = (Spinner) view.findViewById(R.id.experience_spinner);
		ArrayAdapter<CharSequence> Eadapter = ArrayAdapter.createFromResource(getActivity(), R.array.experience_array, android.R.layout.simple_spinner_item);
		Eadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		experience.setAdapter(Eadapter);
		goal = (Spinner) view.findViewById(R.id.goal_spinner);
		ArrayAdapter<CharSequence> Gadapter = ArrayAdapter.createFromResource(getActivity(), R.array.goal_array, android.R.layout.simple_spinner_item);
		Gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		goal.setAdapter(Gadapter);
		setCancelable(false);
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LoginActivity) getActivity()).showBasicInfoDialog();
				dismiss();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Toast.makeText(getActivity(), ""+experience.getSelectedItemId()+" and "+goal.getSelectedItemId(), Toast.LENGTH_SHORT).show();
                trainee.setExperience(experience.getSelectedItemId());
                trainee.setGoal(goal.getSelectedItemId());
                dismiss();
			}
		});
		return view;
	}
}
