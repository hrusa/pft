/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import cz.cvut.fjfi.kse.pft.db.Measure;

/**
 * @author Petr Hruška
 *
 */
@SuppressLint("SimpleDateFormat")
public class AddMeasureDFragment extends DialogFragment{
	View view;
	Bundle args = new Bundle();
	Spinner measure;
	EditText mValue;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	 */
	public AddMeasureDFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragmentd_addmeasure, null);
		args = getArguments();
		Button cancel = (Button) view.findViewById(R.id.cancel_button);
		Button add = (Button) view.findViewById(R.id.add_button);
		mValue = (EditText) view.findViewById(R.id.measure_editText);
		measure = (Spinner) view.findViewById(R.id.measure_spinner);
		ArrayAdapter<CharSequence> adapterM = ArrayAdapter.createFromResource(getActivity(), R.array.measure_array, android.R.layout.simple_spinner_item);
		adapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		measure.setAdapter(adapterM);
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
				Log.i("AddMeasureD", mValue.getText().toString());
				if(mValue.length()>0) {
					Date date = new Date();
					Measure nMeasure = new Measure(getActivity(), args.getLong("trainee"), (int) measure.getSelectedItemId()+1, dateFormat.format(date), Integer.parseInt(mValue.getText().toString()));
					nMeasure.save();
					dismiss();
				} else {
					Toast.makeText(getActivity(), "Please set measure value.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		setCancelable(false);
		return view;
	}
}
