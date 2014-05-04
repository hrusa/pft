/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruška
 * 
 */
@SuppressLint("SimpleDateFormat")
public class WorkoutFragment extends ListFragment {
	private ArrayAdapter<ExerciseUnit> adapter;
	private ExerciseUnit exerciseUnit;
	Bundle args;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				exerciseUnit = adapter.getItem(arg2);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder
						.setTitle("Delete " + exerciseUnit.toString() + "?")
						.setMessage(
								"Do you realy want to delete \""
										+ exerciseUnit.toString()
										+ "\" exercise unit?")
						.setCancelable(false)
						.setPositiveButton("Yes", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

								List<Serie> series = Serie.find(Serie.class,
										"exerciseunit =?",
										"" + exerciseUnit.getId());
								for (Serie serie : series) {
									serie.delete();
								}
								exerciseUnit.delete();
							}
						}).setNegativeButton("No", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						}).create().show();
				return false;
			}

		});

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
									workout.setSync(false);
									workout.save();
									Calendar calendar = Calendar.getInstance();
									try {
										calendar.setTime(dateFormat
												.parse(workout.getDate()));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									calendar.add(Calendar.DATE, 7);
									Workout newWorkout = new Workout(
											getActivity(), args
													.getLong("training"),
											workout.getName(), dateFormat
													.format(calendar.getTime()));
									newWorkout.save();
									List<ExerciseUnit> exerciseUs = ExerciseUnit
											.find(ExerciseUnit.class,
													"workout = ?",
													"" + workout.getId());
									for (int i = 0; i < exerciseUs.size(); i++) {
										ExerciseUnit newExersiseU = new ExerciseUnit(
												getActivity(), exerciseUs
														.get(i).getExercise(),
												newWorkout.getId());
										newExersiseU.save();
										Log.i("WorkoutFragment",
												"přidána exerciseunit #" + i);
										List<Serie> series = Serie.find(
												Serie.class,
												"exerciseunit = ?", ""
														+ exerciseUs.get(i)
																.getId());
										for (int j = 0; j < series.size(); j++) {
											Serie serie = new Serie(
													getActivity(), newExersiseU
															.getId(),
													series.get(j).getWeight(),
													series.get(j)
															.getRepetition(),
													series.get(j).getPause());
											serie.save();
											Log.i("WorkoutFragment",
													"přidána serie #" + j);
										}
									}
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
		List<ExerciseUnit> units = ExerciseUnit.find(ExerciseUnit.class,
				"exercise = ? and done = ?", "" + exerciseU.getExercise(),
				"true");
		if (units.isEmpty()) {
			args.putBoolean("1rm", true);
		} else {
			args.putBoolean("1rm", false);
		}
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
