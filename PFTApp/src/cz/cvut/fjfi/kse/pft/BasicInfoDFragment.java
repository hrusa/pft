/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cz.cvut.fjfi.kse.pft.db.Attribute;
import cz.cvut.fjfi.kse.pft.db.Measure;
import cz.cvut.fjfi.kse.pft.db.Trainee;

/**
 * @author Petr Hruška
 *
 */
public class BasicInfoDFragment extends DialogFragment{
	View view;
	EditText heightText, weightText;
	RadioGroup rg;
	RadioButton rb;
	Bundle args;
	Trainee trainee;
	Attribute height, weight;
	Measure mHeight, mWeight;
	List<Attribute> attrs;
	int test = 0;
	/**
	 * 
	 */
	public BasicInfoDFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("Start onCrateView", ""+test++);
		args = this.getArguments();
		view = inflater.inflate(R.layout.fragmentd_basic_info, null);
		heightText = (EditText) view.findViewById(R.id.height_etext);
		weightText = (EditText) view.findViewById(R.id.weight_etext);
		Button previous = (Button) view.findViewById(R.id.previous_button);
		Button next = (Button) view.findViewById(R.id.next_button);
		rg = (RadioGroup) view.findViewById(R.id.gender_radio_group);
		setCancelable(false);
		getDialog().setTitle(R.string.title_fragmentd_basicinfo);
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BirthdateDFragment dialog = new BirthdateDFragment();
			    dialog.setArguments(args);
			    getFragmentManager().beginTransaction().replace(R.id.container, dialog).addToBackStack(null).commit();
				dismiss();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
                //Do stuff, possibly set wantToCloseDialog to true then...
                Log.i("RB", ""+rg.getCheckedRadioButtonId());
                if(R.id.male_radio == rg.getCheckedRadioButtonId()) {
                	trainee = new Trainee(getActivity(), args.getString("name"), args.getString("email"), args.getString("birth"), 0);
                	
                } else {
                	trainee = new Trainee(getActivity(), args.getString("name"), args.getString("email"), args.getString("birth"), 0);
                }
                trainee.save();
                Log.i("DB insert: ", "Trainee ID " + trainee.getId() + " inserted as " + trainee.getName()+" "+ trainee.getEmail() +" "+trainee.getBirth()+trainee.getExperience()+trainee.getGoal()+trainee.getGender());
                attrs = Attribute.listAll(Attribute.class);
/*                if(attrs.isEmpty()) {
					height = new Attribute(getActivity(), "Height");
					height.save();
					weight = new Attribute(getActivity(), "Weight");
					weight.save();
                }*/
                attrs = Attribute.find(Attribute.class, "name = ?", "Height");
                //přidat kontrolu zadání hodnot!
                mHeight = new Measure(getActivity(), trainee.getId(), attrs.get(0).getId(), getTodayDate(), Integer.parseInt(heightText.getText().toString()));
                mHeight.save();
                Log.i("DB insert: ", "Trainee "+mHeight.getTrainee()+" with "+mHeight.getAttribute()+mHeight.getValue());
                attrs.clear();
                attrs = Attribute.find(Attribute.class, "name = ?", "Weight");
                Log.i("Test attributu s názvem Weight", ""+attrs.isEmpty());
                mWeight = new Measure(getActivity(), trainee.getId(), attrs.get(0).getId(), getTodayDate(), Integer.parseInt(weightText.getText().toString()));
                mWeight.save();
                Log.i("DB insert: ", "Trainee "+mWeight.getTrainee()+" with "+mWeight.getAttribute()+mWeight.getValue());
                args.clear();
            	args.putLong("trainee", trainee.getId());
                WorkoutDFragment dialog = new WorkoutDFragment();
        	    dialog.setArguments(args);
        		dialog.show(getFragmentManager(), "WorkoutD");
                dismiss();
			}
		});
		
		return view;
	}
	
	private String getTodayDate() {
		Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		
		return mYear+"-"+mMonth+"-"+mDay;
	}
}
