/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;

/**
 * @author Mastah
 *
 */
public class WorkoutFragment extends ListFragment{
	private ArrayAdapter<ExerciseUnit> adapter;

	public WorkoutFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		List<ExerciseUnit> exerciseU= ExerciseUnit.listAll(ExerciseUnit.class);
		adapter = new ArrayAdapter<ExerciseUnit>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, exerciseU);
		
		setListAdapter(adapter);
	}
}
