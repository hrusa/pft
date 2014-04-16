/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.MuscleGroup;

/**
 * @author Petr Hruška
 * 
 */
public class ShowExerciseFragment extends ListFragment {
	private ArrayAdapter<MuscleGroup> adapterGroup;
	private ArrayAdapter<Exercise> adapterExercise;
	Bundle args = new Bundle();
	boolean groupClicked = false;

	/**
	 * 
	 */
	public ShowExerciseFragment() {
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

		List<MuscleGroup> groups = MuscleGroup.listAll(MuscleGroup.class);
		adapterGroup = new ArrayAdapter<MuscleGroup>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, groups);
		// možná nebude potřeba
		args = this.getArguments();
		setListAdapter(adapterGroup);
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

		if (!groupClicked) {
			MuscleGroup group = adapterGroup.getItem(position);
			groupClicked = true;
			List<Exercise> exercises = Exercise.find(Exercise.class,
					"musclegroup = ?", group.getId().toString());
			adapterExercise = new ArrayAdapter<Exercise>(getActivity(),
					android.R.layout.simple_list_item_activated_1,
					android.R.id.text1, exercises);
			setListAdapter(adapterExercise);
			adapterExercise.notifyDataSetChanged();
		} else {
			Exercise exercise = adapterExercise.getItem(position);
			args.putLong("exercise", exercise.getId());
			ShowExerciseInfoFragment fragment = new ShowExerciseInfoFragment();
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "ShowExerciseDetail").commit();
		}
	}

}
