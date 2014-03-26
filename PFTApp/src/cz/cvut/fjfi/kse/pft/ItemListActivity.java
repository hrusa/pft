package cz.cvut.fjfi.kse.pft;

import cz.cvut.fjfi.kse.pft.db.Attribute;
import cz.cvut.fjfi.kse.pft.db.Difficulty;
import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Measure;
import cz.cvut.fjfi.kse.pft.db.MuscleBlock;
import cz.cvut.fjfi.kse.pft.db.MuscleGroup;
import cz.cvut.fjfi.kse.pft.db.MusclePart;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Trainee;
import cz.cvut.fjfi.kse.pft.db.Training;
import cz.cvut.fjfi.kse.pft.db.Workout;
import cz.cvut.fjfi.pft.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity implements
		ItemListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.


		//test vkládání hodnot do DB
		Difficulty diff = new Difficulty(getApplicationContext(), "Beginner");
		diff.save();
		Log.i("DB insert: ", "Difficulty ID " + diff.getId() + " inserted as " + diff.getName());
		MuscleGroup muscleG = new MuscleGroup(getApplicationContext(), "Chest");
		muscleG.save();
		Log.i("DB insert: ", "MuscleGroup ID " + muscleG.getId() + " inserted as " + muscleG.getName());
		Trainee trainee = new Trainee(getApplicationContext(), "1989-07-06");
		trainee.save();
		Log.i("DB insert: ", "Trainee ID " + trainee.getId() + " inserted as " + trainee.getBirth());
		Attribute attribute = new Attribute(getApplicationContext(), "Weight");
		attribute.save();
		Log.i("DB insert: ", "Attribute ID " + attribute.getId() + " inserted as " + attribute.getName());

		Measure measure = new Measure(getApplicationContext(), trainee, attribute, "2014-03-20", 73);
		measure.save();
		Log.i("DB insert: ", "Trainee "+measure.getTrainee().getId()+" with "+measure.getAttribute().getName()+measure.getValue());
		Training training = new Training(getApplicationContext(), trainee, "Total body training");
		training.save();
		Log.i("DB insert: ", "Trainee "+training.getTrainee().getId()+" choosed "+training.getName());
		Workout workout = new Workout(getApplicationContext(), training, "Chest workout", "2014-03-20");
		workout.save();
		Log.i("DB insert: ", workout.getTraining().getName()+" has on "+workout.getDate()+workout.getName());
		Exercise exercise = new Exercise(getApplicationContext(), "Bench press", diff, "Work hard on bench press.", "https://www.youtube.com/watch?v=rT7DgCr-3pg");
		exercise.save();
		Log.i("DB insert: ", exercise.getName()+" with "+exercise.getDifficulty().getName()+" difficulty, so "+exercise.getDescription()+" or "+exercise.getVideo());
		ExerciseUnit exerciseU = new ExerciseUnit(getApplicationContext(), exercise, workout);
		exerciseU.save();
		Log.i("DB insert: ", exerciseU.getWorkout().getName()+" contains "+exerciseU.getExercise().getName());
		Serie serie = new Serie(getApplicationContext(), exerciseU, 60, 10, 90, "2014-03-20-19:00:00","2014-03-20-19:01:00");
		serie.save();
		Log.i("DB insert: ", "Serie of "+serie.getExerciseUnit().getExercise().getName()+" with weight "+serie.getWeight()+" and "+serie.getRepetition()+" repetition ended at "+serie.getFinish());
		MusclePart muscleP = new MusclePart(getApplicationContext(), muscleG, "Pectoralis major");
		muscleP.save();
		Log.i("DB insert: ", muscleP.getMuscleGroup().getName()+" muscle group contains "+muscleP.getName());
		MuscleBlock muscleB = new MuscleBlock(getApplicationContext(), exercise, muscleP, "Primary");
		muscleB.save();
		Log.i("DB insert: ", muscleB.getExercise().getName()+" is "+muscleB.getBlock()+" focused on "+muscleB.getMusclePart().getName());
    }

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
