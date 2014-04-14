/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import cz.cvut.fjfi.kse.pft.db.Training;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Petr Hruška
 *
 */
public class AddTrainingDFragment extends DialogFragment{
	View view;
	EditText nameT;
	Bundle args = new Bundle();

	/**
	 * 
	 */
	public AddTrainingDFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragmentd_addtraining, null);
		args = getArguments();
		Button cancel = (Button) view.findViewById(R.id.cancel_button);
		Button add = (Button) view.findViewById(R.id.add_button);
		nameT = (EditText) view.findViewById(R.id.name_editText);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChooseTrainingDFragment dialog = new ChooseTrainingDFragment();
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), "ChooseTrainingD");
				dismiss();
			}
		});
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(nameT.length()>0) {
					Training training = new Training(getActivity(), args.getLong("id"), nameT.getText().toString());
					training.save();
					((TrainingListFragment) getFragmentManager().findFragmentByTag("TrainingList")).updateList(training);
					dismiss();
				} else {
					Toast.makeText(getActivity(), "Please insert the name of the Training.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return view;
	}
}
