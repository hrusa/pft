/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import cz.cvut.fjfi.kse.pft.db.Serie;

/**
 * @author Petr Hruška
 * 
 */
public class ExerciseFragment extends ListFragment {

	private ArrayAdapter<Serie> adapter;
	private Serie ser;
	Bundle args;

	/**
	 * 
	 */
	public ExerciseFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		args = this.getArguments();
		setHasOptionsMenu(true);
		List<Serie> serie = Serie.find(Serie.class, "exerciseunit = ?", ""+args.getLong("exerciseunit"));
		adapter = new ArrayAdapter<Serie>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, serie);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ser = adapter.getItem(arg2);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder
						.setTitle("Delete " + ExerciseFragment.this.ser.toString() + "?")
						.setMessage(
								"Do you realy want to delete \""
										+ ser.toString()
										+ "\" exercise unit?")
						.setCancelable(false)
						.setPositiveButton("Yes", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
									ser.delete();
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
	 * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
	 * android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.actionbar_exercise, menu);
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
		case R.id.add_serie_button:
			showAddSerieDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Serie serie = adapter.getItem(position);
		args.putLong("serie", serie.getId());
		AddSerieDFragment dialog = new AddSerieDFragment();
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddSerieD");
	}

	/**
	 * 
	 */
	private void showAddSerieDialog() {
		// TODO Auto-generated method stub
		AddSerieDFragment dialog = new AddSerieDFragment();
		args.remove("serie");
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddSerieD");
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

	public void updateList(Serie serie) {
		adapter.add(serie);
		adapter.notifyDataSetChanged();
	}
}
