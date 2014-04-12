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
import cz.cvut.fjfi.kse.pft.db.Trainee;

/**
 * @author Petr Hruška
 *
 */
public class CreateTrainingDFragment extends DialogFragment{
	Button create, choose;
	Bundle args;
	Trainee trainee;
	/**
	 * 
	 */
	public CreateTrainingDFragment() {
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
		trainee = Trainee.findById(Trainee.class, args.getLong("id"));
		View view = inflater.inflate(R.layout.fragmentd_createtraining, null);
		create = (Button) view.findViewById(R.id.createT_button);
		choose = (Button) view.findViewById(R.id.chooseT_button);
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		choose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}
	
	void createRecommendedTraining(Trainee trainee) {
		
	}
}
