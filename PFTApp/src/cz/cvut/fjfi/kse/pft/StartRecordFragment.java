/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * @author Petr HruĹˇka
 * 
 */
@SuppressLint("SimpleDateFormat")
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
	double orm = 0;
	ExerciseUnit exerciseU;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
				Calendar calendar = Calendar.getInstance();
				serie.setStart(dateFormat.format(calendar.getTime()));
				serie.setWeight(Integer.parseInt(sWeight.getText().toString()));
				serie.setRepetition(Integer.parseInt(sRepetition.getText()
						.toString()));
				serie.setPause(Integer.parseInt(sPause.getText().toString()));
				serie.setSync(false);
				serie.save();
				startBtn.setVisibility(View.GONE);
				stopBtn.setVisibility(View.VISIBLE);
			}
		});
		stopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (args.getBoolean("1rm") && currentSerie == 0) {
					ORMDFragment dialog = new ORMDFragment();
					dialog.setArguments(args);
					dialog.show(getFragmentManager(), "ORMD");

				} else {
					doOnStopClick();
				}
			}
		});
		finishBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exerciseU.setDone(true);
				exerciseU.setSync(false);
				exerciseU.save();
				showWorkoutFragment();
			}
		});
		return view;
	}

	public void doOnStopClick() {
		if (args.getBoolean("1rm") && currentSerie == 0) {
			Log.i("StartRecord", "Serie ID: " + args.getLong("serie"));
			serie = Serie.findById(Serie.class, args.getLong("serie"));
			Log.i("StartRecord", "Weight: " + serie.getWeight());
			Log.i("StartRecord", "Rep: " + serie.getRepetition());
			orm = serie.getWeight() / (1.0278 - 0.0278 * serie.getRepetition());
			Log.i("ORM", orm + "");
		}
		Calendar calendar = Calendar.getInstance();

		serie.setFinish(dateFormat.format(calendar.getTime()));
		serie.setSync(false);
		serie.save();
		startBtn.setVisibility(View.VISIBLE);
		stopBtn.setVisibility(View.GONE);
		currentSerie++;
		args.putInt("pause", serie.getPause());
		PauseFragment pauseD = new PauseFragment();
		pauseD.setArguments(args);
		pauseD.show(getFragmentManager(), "Pause");
		sWeight.setEnabled(true);
		sRepetition.setEnabled(true);
		sPause.setEnabled(true);
		setupRecordSerie();
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
		Toast.makeText(getActivity(), "PĹ™idej serii", Toast.LENGTH_SHORT)
				.show();
		AddSerieDFragment dialog = new AddSerieDFragment();
		args.remove("serie");
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "AddSerieD");
	}

	private void setupRecordSerie() {
		if (currentSerie < size) {
			if (series.get(currentSerie).getFinish() == "1970-01-01") {
				if (args.getBoolean("1rm") && currentSerie == 0) {
					// code for 1RM, to else moĹľnĂˇ smazat
					// alertdialog, upozorĹ�ujĂ­cĂ­ na 1RM
					AlertDialog.Builder alertDialogB = new AlertDialog.Builder(
							getActivity());
					alertDialogB
							.setTitle("One repeatable maximum")
							.setMessage(
									"Please try to do maximum repetitons with setted weight.")
							.setPositiveButton(R.string.ok_button,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
										}
									}).setCancelable(false);
					AlertDialog alerDialog = alertDialogB.create();
					alerDialog.show();
					lRep.setVisibility(View.INVISIBLE);
				} else {
					lRep.setVisibility(View.VISIBLE);
				}
				serie = series.get(currentSerie);
				args.remove("serie");
				args.putLong("serie", serie.getId());
				eSerie.setText(String.format(
						getResources().getString(R.string.currentSerie_text),
						"" + (currentSerie + 1)));
				if (orm != 0) {
					serie.setWeight((int) (orm * (1.0278 - 0.0278 * serie
							.getRepetition())));
					serie.setSync(false);
					serie.save();
					Log.i("StartRecord",
							"seting new weight to:" + serie.getWeight());
				}
				lWeight.setVisibility(View.VISIBLE);
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
				.replace(R.id.container, fragment, "Workout")
				.addToBackStack(null).commit();
	}
}
