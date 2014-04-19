/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Workout;

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
		args = this.getArguments();
		if (!args.getBoolean("record")) {
			setHasOptionsMenu(true);
		}
		List<ExerciseUnit> exerciseU = ExerciseUnit.find(ExerciseUnit.class,
				"workout = ? and done = ?", "" + args.getLong("workout"),
				"false");
		adapter = new ArrayAdapter<ExerciseUnit>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, exerciseU);
		setListAdapter(adapter);

		if (exerciseU.isEmpty() && args.getBoolean("record")) {
			AlertDialog.Builder alertDialogB = new AlertDialog.Builder(
					getActivity());
			alertDialogB
					.setTitle("Workout has no other exercises")
					.setMessage("Add please exercise or finish your workout.")
					.setNegativeButton("Finish",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Workout workout = Workout.findById(
											Workout.class,
											args.getLong("workout"));
									workout.setDone(true);
									workout.save();
									TrainingFragment fragment = new TrainingFragment();
									fragment.setArguments(args);
									getFragmentManager()
											.beginTransaction()
											.replace(R.id.container, fragment,
													"Training")
											.addToBackStack(null).commit();
								}
							})
					.setPositiveButton(R.string.add_button,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									showAddExerciseDialog();
								}
							}).setCancelable(false);
			AlertDialog alerDialog = alertDialogB.create();
			alerDialog.show();
		}
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
		if (args.getBoolean("record")) {
			StartRecordFragment fragment = new StartRecordFragment();
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "StartRecord")
					.addToBackStack(null).commit();
		} else {
			ExerciseFragment fragment = new ExerciseFragment();
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "Exercise")
					.addToBackStack(null).commit();
		}
	}

	/**
	 * 
	 */
	private void showAddExerciseDialog() {
		// TODO Auto-generated method stub
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
