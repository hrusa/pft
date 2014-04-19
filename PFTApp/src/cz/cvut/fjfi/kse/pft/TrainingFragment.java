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
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruška
 * 
 */
public class TrainingFragment extends ListFragment {
	// ListView workouts;
	private ArrayAdapter<Workout> adapter;
	Bundle args = new Bundle();

	public TrainingFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		args = getArguments();
		if (!args.getBoolean("record")) {
			setHasOptionsMenu(true);
		}
		List<Workout> workouts = Workout.find(Workout.class,
				"training = ? and done = ?", "" + args.getLong("training"),
				"false");
		adapter = new ArrayAdapter<Workout>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, workouts);

		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Workout workout = adapter.getItem(position);
		args.putLong("workout", workout.getId());
		WorkoutFragment fragment = new WorkoutFragment();
		Log.i("Training", "" + args.getBoolean("record"));
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, "Workout")
				.addToBackStack(null).commit();
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
		inflater.inflate(R.menu.actionbar_training, menu);

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
		case R.id.add_workout_button:
			showAddWorkoutDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 
	 */
	public void showAddWorkoutDialog() {
		Toast.makeText(getActivity(), "Přidej workout", Toast.LENGTH_SHORT)
				.show();
		AddWorkoutDFragment dialog = new AddWorkoutDFragment();
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddWorkoutD");
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

	public void updateList(Workout workout) {
		adapter.add(workout);
		adapter.notifyDataSetChanged();
	}
}
