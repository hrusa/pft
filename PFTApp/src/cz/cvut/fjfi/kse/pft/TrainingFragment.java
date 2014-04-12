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
import cz.cvut.fjfi.kse.pft.db.Difficulty;
import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Mastah
 *
 */
public class TrainingFragment extends ListFragment{
	ListView workouts;
	private ArrayAdapter<Workout> adapter;
	Bundle args;
	
	public TrainingFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		insertDB();
		List<Workout> workouts = Workout.listAll(Workout.class);
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
		args = new Bundle();
		args.putLong("id", workout.getId());
		WorkoutFragment fragment = new WorkoutFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}
	
	private void insertDB() {
		List<Difficulty> diffs = Difficulty.listAll(Difficulty.class);
		if (diffs.isEmpty()) {
			Difficulty diff = new Difficulty(getActivity(), "Beginner");
			diff.save();
			Exercise exercise = new Exercise(getActivity(), "Lat pulldown", diff, "Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.", "https://www.youtube.com/watch?v=JEb-dwU3VF4");
			exercise.save();
			exercise = new Exercise(getActivity(), "Tricep pushdown", diff, "Attach a straight or angled bar to a high pulley and grab with an overhand grip (palms facing down) at shoulder width.", "https://www.youtube.com/watch?v=2-LAMcpzODU");
			exercise.save();
			exercise = new Exercise(getActivity(), "Straight bar curls", diff, "Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.", "https://www.youtube.com/watch?v=LY1V6UbRHFM");
			exercise.save();
		}
	}
	
	public void updateList(Workout workout) {
		adapter.add(workout);
		adapter.notifyDataSetChanged();
	}
}
