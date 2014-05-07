/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.orm.SugarRecord;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import cz.cvut.fjfi.kse.pft.db.Exercise;
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
		trainee = SugarRecord.findById(Trainee.class, args.getLong("trainee"));

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
	public void generateTraining(Trainee trainee) {
		// TODO Auto-generated method stub
		String[] experiences = getResources().getStringArray(
				R.array.experience_array);
		String[] goals = getResources().getStringArray(R.array.goal_array);

		List<Training> trainings = SugarRecord.find(
				Training.class,
				"name = ?",
				experiences[trainee.getExperience()] + " "
						+ goals[trainee.getGoal()] + " routine");
		if (trainings.isEmpty()) {

			training = new Training(getActivity(), trainee.getId(),
					experiences[trainee.getExperience()] + " "
							+ goals[trainee.getGoal()] + " routine");
			training.save();
			switch (trainee.getExperience()) {
			case 1:
				advancedTraining();
				break;
			case 2:
				experiencedTraining();
				break;
			default:
				beginnersTraining();
				break;
			}
		}

	}

	private void beginnersTraining() {
		Calendar calendar = Calendar.getInstance();
		int rep, pause;
		workout = new Workout(getActivity(), training.getId(), "Day 1",
				dateFormat.format(calendar.getTime()));
		workout.save();
		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(5)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 30, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 35, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 35, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(23)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(20)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 10, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 12, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 12, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(30)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(27)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();

		calendar.add(Calendar.DATE, 2);
		workout = new Workout(getActivity(), training.getId(), "Day 2",
				dateFormat.format(calendar.getTime()));
		workout.save();
		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(6)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 30, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 35, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 35, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(14)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 20, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 25, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 30, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(1)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 10, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(31)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 10, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 15, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(28)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 1, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 1, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 1, rep, pause);
		serie.save();

		calendar.add(Calendar.DATE, 2);
		workout = new Workout(getActivity(), training.getId(), "Day 3",
				dateFormat.format(calendar.getTime()));
		workout.save();
		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(32)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 40, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 50, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 50, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(13)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 25, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 30, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 35, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(2)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 5, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 5, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 5, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(7)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 40, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 50, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 50, rep, pause);
		serie.save();

		exerciseU = new ExerciseUnit(getActivity(), Long.parseLong(String
				.valueOf(29)), workout.getId());
		exerciseU.save();
		rep = setRepetition(trainee.getGoal());
		pause = setPause(trainee.getGoal());
		serie = new Serie(getActivity(), exerciseU.getId(), 1, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 1, rep, pause);
		serie.save();
		serie = new Serie(getActivity(), exerciseU.getId(), 1, rep, pause);
		serie.save();
	}

	private void advancedTraining() {
		Calendar calendar = Calendar.getInstance();
		List<Exercise> exerciseChest = SugarRecord.find(Exercise.class, "musclegroup =? and difficulty is not ?", "3", "3");
		List<Exercise> exerciseBack = SugarRecord.find(Exercise.class, "musclegroup =? and difficulty is not ?", "2", "3");
		List<Exercise> exerciseLegs = SugarRecord.find(Exercise.class, "musclegroup =? and difficulty is not ?", "7", "3");
		List<Exercise> exerciseArms = SugarRecord.find(Exercise.class, "musclegroup =? or musclegroup =?", "4", "5");
		List<Exercise> exerciseAS = SugarRecord.find(Exercise.class, "musclegroup =? or musclegroup =?", "1", "6");
		for(int i = 0;i<3;i++) {
			workout = new Workout(getActivity(), training.getId(), "Day "+i,
					dateFormat.format(calendar.getTime()));
			workout.save();
			Random generate = new Random();
			exerciseU = new ExerciseUnit(getActivity(), exerciseChest.get(generate.nextInt(exerciseChest.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseBack.get(generate.nextInt(exerciseBack.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseLegs.get(generate.nextInt(exerciseLegs.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseArms.get(generate.nextInt(exerciseArms.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseAS.get(generate.nextInt(exerciseAS.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			calendar.add(Calendar.DATE, 2);
		}
	}

	private void experiencedTraining() {
		Calendar calendar = Calendar.getInstance();
		List<Exercise> exerciseChest = SugarRecord.find(Exercise.class, "musclegroup =?", "3");
		List<Exercise> exerciseBack = SugarRecord.find(Exercise.class, "musclegroup =?", "2");
		List<Exercise> exerciseLegs = SugarRecord.find(Exercise.class, "musclegroup =?", "7");
		List<Exercise> exerciseArms = SugarRecord.find(Exercise.class, "musclegroup =? or musclegroup =?", "4", "5");
		List<Exercise> exerciseAS = SugarRecord.find(Exercise.class, "musclegroup =? or musclegroup =?", "1", "6");
		for(int i = 0;i<3;i++) {
			workout = new Workout(getActivity(), training.getId(), "Day "+i,
					dateFormat.format(calendar.getTime()));
			workout.save();
			Random generate = new Random();
			exerciseU = new ExerciseUnit(getActivity(), exerciseChest.get(generate.nextInt(exerciseChest.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseBack.get(generate.nextInt(exerciseBack.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseLegs.get(generate.nextInt(exerciseLegs.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseArms.get(generate.nextInt(exerciseArms.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			
			exerciseU = new ExerciseUnit(getActivity(), exerciseAS.get(generate.nextInt(exerciseAS.size())).getId(), workout.getId());
			exerciseU.save();
			setUpSeries(exerciseU.getId(), trainee.getGoal());
			calendar.add(Calendar.DATE, 2);
		}
	}
	
	private void setUpSeries(long id, int goal) {
		int rep, pause;
		for(int i =0;i<4;i++) {
			rep = setRepetition(goal);
			pause = setPause(goal);
			serie = new Serie(getActivity(), id, 1, rep, pause);
			serie.save();
		}
	}

	private int setRepetition(int goal) {
		int min, max;
		Random generate = new Random();

		switch (goal) {
		case 1:
			min = 1;
			max = 4;
			break;
		case 2:
			min = 5;
			max = 10;
			break;
		case 3:
			min = 12;
			max = 15;
			break;
		default:
			min = 10;
			max = 12;
			break;
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
			break;
		case 2:
			min = 120;
			max = 180;
			break;
		case 3:
			min = 30;
			max = 60;
			break;
		default:
			min = 60;
			max = 120;
			break;
		}
		return generate.nextInt(max - min + 1) + min;
	}
}
