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
		view = inflater.inflate(R.layout.fragmentd_workout, null);
		Button previous = (Button) view.findViewById(R.id.previous_button);
		Button next = (Button) view.findViewById(R.id.next_button);
		Spinner experience = (Spinner) view.findViewById(R.id.experience_spinner);
		ArrayAdapter<String> Eadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, R.array.experience_array);
		Eadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		experience.setAdapter(Eadapter);
		Spinner goal = (Spinner) view.findViewById(R.id.goal_spinner);
		ArrayAdapter<String> Gadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, R.array.goal_array);
		Gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		goal.setAdapter(Gadapter);
		setCancelable(false);
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LoginActivity) getActivity()).showBasicInfoDialog();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Toast.makeText(getActivity(), "Frčíme", Toast.LENGTH_SHORT).show();
                
			}
		});
		return view;
	}
}
