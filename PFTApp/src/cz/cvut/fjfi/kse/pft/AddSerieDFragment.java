/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.ParseException;

import com.orm.SugarRecord;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;
import cz.cvut.fjfi.kse.pft.db.Serie;

/**
 * @author Petr Hruška
 * 
 */
public class AddSerieDFragment extends DialogFragment {

	View view;
	Bundle args = new Bundle();
	NumberPicker npWeight, npRepetition, npPause;

	/**
	 * 
	 */
	public AddSerieDFragment() {
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
		view = inflater.inflate(R.layout.fragmentd_addserie, null);
		args = getArguments();
		Button cancel = (Button) view.findViewById(R.id.cancel_button);
		Button save = (Button) view.findViewById(R.id.save_button);
		npWeight = (NumberPicker) view.findViewById(R.id.weight_numberPicker);
		npWeight.setMinValue(1);
		npWeight.setMaxValue(300);
		npRepetition = (NumberPicker) view
				.findViewById(R.id.repetition_numberPicker);
		npRepetition.setMinValue(1);
		npRepetition.setMaxValue(50);
		npPause = (NumberPicker) view.findViewById(R.id.pause_numberPicker);
		npPause.setMinValue(1);
		npPause.setMaxValue(500);
		Log.i("Je serie inicializována?", "" + args.getBoolean("serie"));
		if (args.containsKey("serie")) {
			Serie serie = SugarRecord.findById(Serie.class, args.getLong("serie"));

			npWeight.setValue(serie.getWeight());
			npRepetition.setValue(serie.getRepetition());
			npPause.setValue(serie.getPause());
		}
		getDialog().setTitle("Setup serie");
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (npWeight.getValue() > 0 && npRepetition.getValue() > 0
						&& npPause.getValue() > 0) {
					Serie serie = new Serie(getActivity(), args
							.getLong("exerciseunit"), npWeight.getValue(),
							npRepetition.getValue(), npPause.getValue());
					serie.save();
					if (args.getBoolean("record")) {
						try {
							((StartRecordFragment) getFragmentManager().findFragmentByTag("StartRecord")).addSerie(serie);
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						((ExerciseFragment) getFragmentManager()
								.findFragmentByTag("Exercise"))
								.updateList(serie);
					}
					dismiss();
				} else {
					Toast.makeText(getActivity(),
							"Serie attributes should not be zero.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		setCancelable(false);
		return view;
	}
}
