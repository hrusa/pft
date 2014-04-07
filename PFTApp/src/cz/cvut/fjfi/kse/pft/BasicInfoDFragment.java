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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * @author Petr Hruška
 *
 */
public class BasicInfoDFragment extends DialogFragment{
	View view;
	RadioGroup rg;
	RadioButton rb;
	Bundle args;
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
		Button previous = (Button) view.findViewById(R.id.previous_button);
		Button next = (Button) view.findViewById(R.id.next_button);
		rg = (RadioGroup) view.findViewById(R.id.gender_radio_group);
		setCancelable(false);
		getDialog().setTitle(R.string.title_fragmentd_basicinfo);
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LoginActivity) getActivity()).showBirthdateDialog();
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
                	Toast.makeText(getActivity(), "Male madafaka", Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(getActivity(), "Female biatch", Toast.LENGTH_SHORT).show();
                }
                ((LoginActivity) getActivity()).showWorkoutDialog();
                dismiss();
			}
		});
		
		return view;
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle(R.string.title_fragmentd_basicinfo);
		
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragmentd_basic_info, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.title_fragmentd_basicinfo)
		.setView(view)
		.setCancelable(false)
		.setNegativeButton(R.string.previous, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				((LoginActivity) getActivity()).showBirthdateDialog();
			}
		})
		.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		setCancelable(false);
		Dialog dialog = builder.create();
		return dialog;
	}*/
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
	 
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		AlertDialog dialog = (AlertDialog)getDialog();
	    if(dialog != null)
	    {
	        Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
	        positiveButton.setOnClickListener(new View.OnClickListener()
	                {
	                    @Override
	                    public void onClick(View v)
	                    {
	                        LayoutInflater inflater = getActivity().getLayoutInflater();
	                		View view = inflater.inflate(R.layout.fragmentd_basic_info, null);
	                        RadioGroup rg = (RadioGroup) view.findViewById(R.id.gender_radio_group);
	                        //Do stuff, possibly set wantToCloseDialog to true then...
	                        Log.i("RB", ""+rg.getCheckedRadioButtonId());
	                        if(R.id.male_radio == rg.getCheckedRadioButtonId()) {
	                        	Toast.makeText(getActivity(), "Male madafaka", Toast.LENGTH_SHORT).show();
	                        	dismiss();
	                        } else {
	                        	Toast.makeText(getActivity(), "Female biatch", Toast.LENGTH_SHORT).show();
	                        }
	                            
	                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
	                    }
	                });
	    }
	}*/
}
