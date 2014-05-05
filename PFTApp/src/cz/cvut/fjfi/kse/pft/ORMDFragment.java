/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import com.orm.SugarRecord;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cz.cvut.fjfi.kse.pft.db.Serie;

/**
 * @author Petr Hruška
 * 
 */
public class ORMDFragment extends DialogFragment {
	Bundle args = new Bundle();
	View view;
	EditText rep;
	double orm;

	/**
	 * 
	 */
	public ORMDFragment() {
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
		view = inflater.inflate(R.layout.fragmentd_ormd, null);
		rep = (EditText) view.findViewById(R.id.rep_editText);
		Button save = (Button) view.findViewById(R.id.save_button);
		setCancelable(false);
		getDialog().setTitle("One repeatable maximum");
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("ORMD", "Serie ID: "+args.getLong("serie"));
				Serie serie = SugarRecord.findById(Serie.class, args.getLong("serie"));
				serie.setRepetition(Integer.parseInt(rep.getText().toString()));
				serie.setSync(false);
				serie.save();
				((StartRecordFragment) getFragmentManager().findFragmentByTag("StartRecord")).doOnStopClick();
				/*orm = serie.getWeight()
						/ (1.0278 - 0.0278 * serie.getRepetition());
				Log.i("ORM", orm + "");
				args.putDouble("orm", orm);
				StartRecordFragment fragment = new StartRecordFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "StartRecord")
						.commit();*/
				dismiss();
			}
		});
		return view;
	}
}
