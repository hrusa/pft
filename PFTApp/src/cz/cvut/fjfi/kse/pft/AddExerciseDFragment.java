/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import com.orm.SugarRecord;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.MuscleGroup;

/**
 * @author Petr Hruška
 *
 */
public class AddExerciseDFragment extends DialogFragment{
	View view;
	ListView lv;
	Boolean groupClicked = false;
	ExerciseUnit exerciseU;
	Bundle args = new Bundle();
	private ArrayAdapter<MuscleGroup> adapterGroup;
	private ArrayAdapter<Exercise> adapterExercise;
	private Exercise exercise;
	/**
	 * 
	 */
	public AddExerciseDFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		args = this.getArguments();
		view = inflater.inflate(R.layout.fragmentd_addexercise, null);
		lv = (ListView) view.findViewById(R.id.exercise_listView);
		List<MuscleGroup> groups = SugarRecord.listAll(MuscleGroup.class);
		adapterGroup = new ArrayAdapter<MuscleGroup>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, groups);
		
		lv.setAdapter(adapterGroup);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("", "lv click");
				if(!groupClicked) {
					groupClicked = true;
					MuscleGroup group = adapterGroup.getItem(arg2);
					//List<Exercise> exercises = Exercise.find(Exercise.class, "muscleGroup = ?", group.getName());
					//List<Exercise> exercises = Exercise.listAll(Exercise.class);
					List<Exercise> exercises = SugarRecord.find(Exercise.class, "musclegroup =?", group.getId().toString());
					adapterExercise = new ArrayAdapter<Exercise>(getActivity(), android.R.layout.simple_list_item_activated_1,
						android.R.id.text1, exercises);
					lv.setAdapter(adapterExercise);
					adapterExercise.notifyDataSetChanged();
				} else {
					//groupClicked = false;
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
					exercise = adapterExercise.getItem(arg2);
					alertDialogBuilder.setTitle("Add exercise?")
					.setMessage("Do you really want to add "+exercise.getName()+" to your workout?")
					.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dismiss();
						}
					})
					.setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							List<ExerciseUnit> exerciseUs = SugarRecord.find(ExerciseUnit.class, "workout = ? and exercise = ?", ""+args.getLong("workout"), ""+exercise.getId());
							if(exerciseUs.isEmpty()) {
							exerciseU = new ExerciseUnit(getActivity(), exercise.getId(), args.getLong("workout"));
							exerciseU.save();
							((WorkoutFragment) getFragmentManager().findFragmentByTag("Workout")).updateList(exerciseU);
							} else {
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
								alertDialogBuilder.setTitle("Exercise already added!")
								.setMessage(exercise.getName()+" is already in your workout plan.")
								.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										//getFragmentManager().findFragmentByTag("Workout");
									}
								})
								.setCancelable(false);
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
							}
							dismiss();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
					
				}
			}
			
		});
		setCancelable(false);
		return view;
	}
}
