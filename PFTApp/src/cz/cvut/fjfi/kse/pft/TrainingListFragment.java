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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cz.cvut.fjfi.kse.pft.db.Training;

/**
 * @author Petr Hruška
 * 
 */
public class TrainingListFragment extends ListFragment {
	ArrayAdapter<Training> adapter;
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
		//needed for action bar items, without this are items not visible
		setHasOptionsMenu(true);
		List<Training> trainings = Training.listAll(Training.class);
		adapter = new ArrayAdapter<Training>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, trainings);

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
				.replace(R.id.container, fragment, "Training").commit();
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
		Toast.makeText(getActivity(), "Přidej training", Toast.LENGTH_SHORT)
				.show();
		AddTrainingDFragment dialog = new AddTrainingDFragment();
		args = getArguments();
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddTrainingD");
	}

	public void updateList(Training training) {
		adapter.add(training);
		adapter.notifyDataSetChanged();
	}
}
