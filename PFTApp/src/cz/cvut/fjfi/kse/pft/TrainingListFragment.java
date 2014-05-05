/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import com.orm.SugarRecord;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Training;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruška
 * 
 */
public class TrainingListFragment extends ListFragment {
	ArrayAdapter<Training> adapter;
	Training training;
	Bundle args = new Bundle();

	/**
	 * 
	 */
	public TrainingListFragment() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// needed for action bar items, without this are items not visible
		args = getArguments();
		if (!args.getBoolean("record")) {
			setHasOptionsMenu(true);
		}
		getActivity().getActionBar().setTitle("Trainings");
		List<Training> trainings = SugarRecord.listAll(Training.class);
		adapter = new ArrayAdapter<Training>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, trainings);

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				training = adapter.getItem(arg2);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder
						.setTitle("Delete " + training.getName() + "?")
						.setMessage(
								"Do you realy want to delete \""
										+ training.getName() + "\" training?")
						.setCancelable(false)
						.setPositiveButton("Yes", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								List<Workout> workouts = SugarRecord.find(
										Workout.class,
										"training =? and done =?", ""
												+ training.getId(), "false");
								for (Workout workout : workouts) {
									List<ExerciseUnit> exerciseUnits = SugarRecord
											.find(ExerciseUnit.class,
													"workout =? and done =?",
													"" + workout.getId(),
													"false");
									for (ExerciseUnit exerciseUnit : exerciseUnits) {
										List<Serie> series = SugarRecord.find(
												Serie.class, "exerciseunit =?",
												"" + exerciseUnit.getId());
										for (Serie serie : series) {
											serie.delete();
										}
										exerciseUnit.delete();
									}
									workout.delete();
								}
								training.delete();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView
	 * , android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Training training = adapter.getItem(position);

		args.putLong("training", training.getId());
		TrainingFragment fragment = new TrainingFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, "Training")
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
		inflater.inflate(R.menu.actionbar_traininglist, menu);
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
		case R.id.add_training_button:
			showAddTrainingDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 
	 */
	private void showAddTrainingDialog() {
		// TODO Auto-generated method stub
		AddTrainingDFragment dialog = new AddTrainingDFragment();
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddTrainingD");
	}

	public void updateList(Training training) {
		adapter.add(training);
		adapter.notifyDataSetChanged();
	}
}
