/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
		if(!exerciseU.isEmpty()) {
			Log.i("Počet eU", ""+exerciseU.size());
			Log.i("WorkoutFragment", "Exercise id: "+exerciseU.get(0).getExercise());
			Log.i("WorkoutFragment", "Workout id: "+exerciseU.get(0).getWorkout());
			Log.i("WorkoutFragment", "ExerciseUnit id: "+exerciseU.get(0).getId());
		} else {
			Log.i("WorkoutFragment", "Workout is empty!");
		}
		setListAdapter(adapter);
		Log.i("WorkoutFragment", "po setListAdapter");
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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		ExerciseUnit exerciseU = adapter.getItem(position);
		args.putLong("exerciseu", exerciseU.getId());
		ExerciseFragment fragment = new ExerciseFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, "Exercise").commit();
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
