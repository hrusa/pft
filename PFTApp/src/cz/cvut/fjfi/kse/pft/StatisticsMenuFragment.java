/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class StatisticsMenuFragment extends Fragment {
	View view;
	Button exercise, measure;
	Bundle args = new Bundle();

	/**
	 * 
	 */
	public StatisticsMenuFragment() {
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
		view = inflater.inflate(R.layout.fragment_statisticsmenu, null);
		exercise = (Button) view.findViewById(R.id.exercise_button);
		measure = (Button) view.findViewById(R.id.measure_button);
		args = getArguments();

		exercise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("StatisticsMenu", "exercise");
			}
		});

		measure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MeasuresListFragment fragment = new MeasuresListFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "MeasuresList")
						.addToBackStack(null).commit();
			}
		});
		return view;
	}
}
