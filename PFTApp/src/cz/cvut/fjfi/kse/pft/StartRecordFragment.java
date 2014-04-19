/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Serie;

/**
 * @author Petr Hruška
 * 
 */
public class StartRecordFragment extends Fragment {
	View view;
	TextView eName, eSerie;
	EditText sWeight, sRepetition, sPause;
	Button startBtn, stopBtn, finishBtn;
	LinearLayout lWeight, lRep, lPause;
	Serie serie;
	List<Serie> series;
	Bundle args = new Bundle();
	int size, currentSerie = 0;
	ExerciseUnit exerciseU;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 */
	public StartRecordFragment() {
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
		view = inflater.inflate(R.layout.fragment_startrecord, null);
		args = this.getArguments();

		eName = (TextView) view.findViewById(R.id.recordName_textView);
		eSerie = (TextView) view.findViewById(R.id.recordSerie_textView);

		sWeight = (EditText) view.findViewById(R.id.weight_editText);
		sRepetition = (EditText) view.findViewById(R.id.rep_editText);
		sPause = (EditText) view.findViewById(R.id.pause_editText);

		startBtn = (Button) view.findViewById(R.id.start_button);
		stopBtn = (Button) view.findViewById(R.id.stop_button);
		finishBtn = (Button) view.findViewById(R.id.finish_button);

		lWeight = (LinearLayout) view.findViewById(R.id.weight_linearLayout);
		lRep = (LinearLayout) view.findViewById(R.id.rep_linearLayout);
		lPause = (LinearLayout) view.findViewById(R.id.pause_linearLayout);

		exerciseU = ExerciseUnit.findById(ExerciseUnit.class,
				args.getLong("exerciseu"));
		series = Serie.find(Serie.class, "exerciseunit = ?", exerciseU.getId()
				.toString());
		size = series.size();

		eName.setText(exerciseU.toString());
		setupRecordSerie();

		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sWeight.setEnabled(false);
				sRepetition.setEnabled(false);
				sPause.setEnabled(false);
				Date date = new Date();
				serie.setStart(dateFormat.format(date));
				serie.setWeight(Integer.parseInt(sWeight.getText().toString()));
				serie.setRepetition(Integer.parseInt(sRepetition.getText()
						.toString()));
				serie.setPause(Integer.parseInt(sPause.getText().toString()));
				serie.save();
				startBtn.setVisibility(View.GONE);
				stopBtn.setVisibility(View.VISIBLE);
			}
		});
		stopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sWeight.setEnabled(true);
				sRepetition.setEnabled(true);
				sPause.setEnabled(true);
				Date date = new Date();
				serie.setFinish(dateFormat.format(date));
				serie.save();
				startBtn.setVisibility(View.VISIBLE);
				stopBtn.setVisibility(View.GONE);
				currentSerie++;
				args.putInt("pause", serie.getPause());
				PauseFragment dialog = new PauseFragment();
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), "Pause");
				setupRecordSerie();
			}
		});
		finishBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exerciseU.setDone(true);
				exerciseU.save();
				showWorkoutFragment();
			}
		});
		return view;
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

	/**
	 * 
	 */
	private void showAddSerieDialog() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "Přidej serii", Toast.LENGTH_SHORT)
				.show();
		AddSerieDFragment dialog = new AddSerieDFragment();
		args.remove("serie");
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddSerieD");
	}

	private void setupRecordSerie() {
		if (currentSerie < size) {
			if (series.get(currentSerie).getFinish() == null) {
				serie = series.get(currentSerie);
				eSerie.setText(String.format(
						getResources().getString(R.string.currentSerie_text),
						"" + (currentSerie + 1)));
				lWeight.setVisibility(View.VISIBLE);
				lRep.setVisibility(View.VISIBLE);
				lPause.setVisibility(View.VISIBLE);
				sWeight.setText(String.valueOf(serie.getWeight()));
				sRepetition.setText(String.valueOf(serie.getRepetition()));
				sPause.setText(String.valueOf(serie.getPause()));
			} else {
				currentSerie++;
				setupRecordSerie();
			}
		} else {
			setHasOptionsMenu(true);
			startBtn.setVisibility(View.GONE);
			if (size == 0) {
				AlertDialog.Builder alertDialogB = new AlertDialog.Builder(
						getActivity());
				alertDialogB
						.setTitle("Exercise has no series")
						.setMessage("Add please serie to your exercise")
						.setNegativeButton(R.string.cancel_button,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										showWorkoutFragment();
									}
								})
						.setPositiveButton(R.string.add_button,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										showAddSerieDialog();
									}
								}).setCancelable(false);
				AlertDialog alerDialog = alertDialogB.create();
				alerDialog.show();
			} else {
				finishBtn.setVisibility(View.VISIBLE);
				lWeight.setVisibility(View.INVISIBLE);
				lRep.setVisibility(View.INVISIBLE);
				lPause.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void addSerie(Serie serie) {
		finishBtn.setVisibility(View.GONE);
		startBtn.setVisibility(View.VISIBLE);
		series.add(serie);
		size = series.size();
		setHasOptionsMenu(false);
		setupRecordSerie();
	}

	private void showWorkoutFragment() {
		WorkoutFragment fragment = new WorkoutFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, "Workout").addToBackStack(null).commit();
	}
}
