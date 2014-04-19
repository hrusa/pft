/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Trainee;
import cz.cvut.fjfi.kse.pft.db.Training;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruška
 * 
 */
@SuppressLint("SimpleDateFormat")
public class GenerateTrainingFragment extends Fragment {
	Bundle args = new Bundle();
	Trainee trainee;
	Training training;
	Workout workout;
	ExerciseUnit exerciseU;
	Serie serie;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	public GenerateTrainingFragment() {
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
		args = getArguments();
		trainee = Trainee.findById(Trainee.class, args.getLong("trainee"));

		generateTraining(trainee);
		TrainingListFragment fragment = new TrainingListFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, "TrainingList")
				.addToBackStack(null).commit();
	}

	/**
	 * @param trainee
	 */
	private void generateTraining(Trainee trainee) {
		// TODO Auto-generated method stub
		String[] experiences = getResources().getStringArray(
				R.array.experience_array);
		String[] goals = getResources().getStringArray(R.array.goal_array);
		Calendar calendar = Calendar.getInstance();
		List<Training> trainings = Training.find(
				Training.class,
				"name = ?",
				experiences[trainee.getExperience()] + " "
						+ goals[trainee.getGoal()] + " routine");
		if (trainings.isEmpty()) {
			int rep, pause;
			training = new Training(getActivity(), trainee.getId(),
					experiences[trainee.getExperience()] + " "
							+ goals[trainee.getGoal()] + " routine");
			training.save();
			switch (trainee.getExperience()) {
			case 1:

			case 2:

			default:
				Log.i("DateFormat", dateFormat.format(calendar.getTime()));
				workout = new Workout(getActivity(), training.getId(), "Day 1",
						dateFormat.format(calendar.getTime()).toString());
				workout.save();
				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(3)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 30, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 35, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 35, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(12)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(11)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 10, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 12, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 12, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(6)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(13)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();

				workout = new Workout(getActivity(), training.getId(), "Day 2",
						dateFormat.format(calendar.getTime()));
				workout.save();
				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(4)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 30, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 35, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 35, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(2)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 20, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 25, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 30, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(9)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 10, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(7)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 10, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 15, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(15)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 1, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 1, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 1, rep,
						pause);
				serie.save();

				workout = new Workout(getActivity(), training.getId(), "Day 3",
						dateFormat.format(calendar.getTime()));
				workout.save();
				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(8)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 40, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 50, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 50, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(1)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 25, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 30, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 35, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(10)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 5, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 5, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 5, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(5)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 40, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 50, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 50, rep,
						pause);
				serie.save();

				exerciseU = new ExerciseUnit(getActivity(),
						Long.parseLong(String.valueOf(14)), workout.getId());
				exerciseU.save();
				rep = setRepetition(trainee.getGoal());
				pause = setPause(trainee.getGoal());
				serie = new Serie(getActivity(), exerciseU.getId(), 1, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 1, rep,
						pause);
				serie.save();
				serie = new Serie(getActivity(), exerciseU.getId(), 1, rep,
						pause);
				serie.save();
				Log.i("Dělení intů", "" + ((int) 1 / 2));
			}
		}

	}

	private int setRepetition(int goal) {
		int min, max;
		Random generate = new Random();

		switch (goal) {
		case 1:
			min = 1;
			max = 4;
		case 2:
			min = 5;
			max = 10;
		case 3:
			min = 12;
			max = 15;
		default:
			min = 10;
			max = 12;
		}
		return generate.nextInt(max - min + 1) + min;
	}

	private int setPause(int goal) {
		int min, max;
		Random generate = new Random();

		switch (goal) {
		case 1:
			min = 180;
			max = 300;
		case 2:
			min = 120;
			max = 180;
		case 3:
			min = 30;
			max = 60;
		default:
			min = 60;
			max = 120;
		}
		return generate.nextInt(max - min + 1) + min;
	}
}
