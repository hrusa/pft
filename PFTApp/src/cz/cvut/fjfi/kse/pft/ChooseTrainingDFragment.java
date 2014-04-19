/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author Petr Hruška
 * 
 */
public class ChooseTrainingDFragment extends DialogFragment {
	Button create, choose;
	Bundle args;

	/**
	 * 
	 */
	public ChooseTrainingDFragment() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		args = this.getArguments();
		View view = inflater.inflate(R.layout.fragmentd_choosetraining, null);
		create = (Button) view.findViewById(R.id.createT_button);
		choose = (Button) view.findViewById(R.id.chooseT_button);
		create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TrainingListFragment fragment = new TrainingListFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "TrainingList")
						.addToBackStack(null)
						.commit();
				dismiss();
				Log.i("ChooseTraining dialog", "starting training list fragment");
			}
		});
		choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createRecommendedTraining(args.getLong("id"));
				dismiss();
			}
		});
		setCancelable(false);
		return view;
	}

	void createRecommendedTraining(long trainee) {
		GenerateTrainingFragment fragment = new GenerateTrainingFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction().replace(R.id.container, fragment, "GenerateTraining").addToBackStack(null).commit();
	}
}
