/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;

/**
 * @author Petr Hruška
 * 
 */
public class WorkoutFragment extends ListFragment {
	private ArrayAdapter<ExerciseUnit> adapter;
	Bundle args;

	public WorkoutFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		setHasOptionsMenu(true);
		List<ExerciseUnit> exerciseU = ExerciseUnit.listAll(ExerciseUnit.class);
		adapter = new ArrayAdapter<ExerciseUnit>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, exerciseU);
		args = this.getArguments();
		setListAdapter(adapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
	 * android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.actionbar_workout, menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem
	 * )
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.add_exercise_button:
			showAddExerciseDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 
	 */
	private void showAddExerciseDialog() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Přidej cvik", Toast.LENGTH_SHORT)
			.show();
		AddExerciseDFragment dialog = new AddExerciseDFragment();
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddExerciseD");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void updateList(ExerciseUnit exerciseU) {
		adapter.add(exerciseU);
		adapter.notifyDataSetChanged();
	}
}
