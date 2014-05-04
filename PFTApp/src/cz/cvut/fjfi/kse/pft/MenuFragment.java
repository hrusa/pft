/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Measure;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Trainee;
import cz.cvut.fjfi.kse.pft.db.Training;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruška
 * 
 */
public class MenuFragment extends Fragment {
	View view;
	Button startBtn, createBtn, measureBtn, exerciseBtn, statisticBtn, exitBtn;
	Bundle args = new Bundle();
	Exercise exercise;
	List<Training> trainings;

	/**
	 * 
	 */
	public MenuFragment() {
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
		view = inflater.inflate(R.layout.fragment_menu, null);
		args = getArguments();
		//insertDB();

		trainings = Training.listAll(Training.class);

		startBtn = (Button) view.findViewById(R.id.start_button);
		createBtn = (Button) view.findViewById(R.id.create_button);
		measureBtn = (Button) view.findViewById(R.id.measure_button);
		exerciseBtn = (Button) view.findViewById(R.id.exercise_button);
		statisticBtn = (Button) view.findViewById(R.id.statistic_button);
		exitBtn = (Button) view.findViewById(R.id.exit_button);
		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (trainings.isEmpty()) {
					ChooseTrainingDFragment dialog = new ChooseTrainingDFragment();
					dialog.setArguments(args);
					dialog.show(getFragmentManager(), "ChooseTrainingD");
				} else {
					args.remove("record");
					args.putBoolean("record", true);
					TrainingListFragment fragment = new TrainingListFragment();
					fragment.setArguments(args);
					getFragmentManager().beginTransaction()
							.replace(R.id.container, fragment, "TrainingList")
							.addToBackStack(null).commit();
				}
			}
		});
		createBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChooseTrainingDFragment dialog = new ChooseTrainingDFragment();
				args.remove("record");
				args.putBoolean("record", false);
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), "ChooseTrainingD");
			}
		});
		measureBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddMeasureDFragment dialog = new AddMeasureDFragment();
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), "AddMeasureD");
			}
		});
		exerciseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// vytvořit nový list fragment, který bude pouze zobrazovat
				// cviky, po kliknutí na jednotlivý cvik je uživateli zobrazen
				// popis
				args.putBoolean("statistics", false);
				ShowExerciseFragment fragment = new ShowExerciseFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "ShowExercise")
						.addToBackStack(null).commit();
			}
		});
		statisticBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StatisticsMenuFragment fragment = new StatisticsMenuFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "StatisticsMenu")
						.addToBackStack(null).commit();
			}
		});
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		
		if (((LoginActivity) getActivity()).isNetworkAvailable()) {
			new dataUL().execute();
		}
		return view;
	}

	/*private void insertDB() {
		List<Difficulty> diffs = Difficulty.listAll(Difficulty.class);
		if (diffs.isEmpty()) {
			Difficulty diff = new Difficulty(getActivity(), "Beginner");
			diff.save();

			// CHEST EXERCISES
			MuscleGroup group = new MuscleGroup(getActivity(), "Chest");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Bench press - Smith maschine",
					diff,
					"Lie back on a flat bench. Using a medium width grip (a grip that creates a 90-degree angle in the middle of the movement between the forearms and the upper arms), lift the bar from the rack and hold it straight over you with your arms locked. This will be your starting position.\nFrom the starting position, breathe in and begin coming down slowly until the bar touches your middle chest.\nAfter a brief pause, push the bar back to the starting position as you breathe out. Focus on pushing the bar using your chest muscles. Lock your arms and squeeze your chest in the contracted position at the top of the motion, hold for a second and then start coming down slowly again. Tip: Ideally, lowering the weight should take about twice as long as raising it.\nRepeat the movement for the prescribed amount of repetitions.\nWhen you are done, place the bar back in the rack.",
					"https://www.youtube.com/watch?v=z_r6hDOYtO0", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Incline bench press – smith mashine",
					diff,
					"Place an incline bench underneath the smith machine. Place the barbell at a height that you can reach when lying down and your arms are almost fully extended. Once the weight you need is selected, lie down on the incline bench and make sure your upper chest is aligned with the barbell. Using a pronated grip (palms facing forward) that is wider than shoulder width, unlock the bar from the rack and hold it straight over you with your arms locked. This will be your starting position.\nAs you breathe in, come down slowly until you feel the bar on your upper chest.\nAfter a second pause, bring the bar back to the starting position as you breathe out and push the bar using your chest muscles. Lock your arms in the contracted position, hold for a second and then start coming down slowly again. Tip: It should take at least twice as long to go down than to come up.\nRepeat the movement for the prescribed amount of repetitions.\nWhen you are done, place the bar back in the rack.",
					"https://www.youtube.com/watch?v=b8DqTO6ak0k", group
							.getId());
			exercise.save();

			// BACK EXERCISES
			group = new MuscleGroup(getActivity(), "Back");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Lat pulldown",
					diff,
					"Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.\nGrab the bar with the palms facing forward using the prescribed grip. Note on grips: For a wide grip, your hands need to be spaced out at a distance wider than shoulder width. For a medium grip, your hands need to be spaced out at a distance equal to your shoulder width and for a close grip at a distance smaller than your shoulder width.\nAs you have both arms extended in front of you holding the bar at the chosen grip width, bring your torso back around 30 degrees or so while creating a curvature on your lower back and sticking your chest out. This is your starting position.\nAs you breathe out, bring the bar down until it touches your upper chest by drawing the shoulders and the upper arms down and back. Tip: Concentrate on squeezing the back muscles once you reach the full contracted position. The upper torso should remain stationary and only the arms should move. The forearms should do no other work except for holding the bar; therefore do not try to pull down the bar using the forearms.\nAfter a second at the contracted position squeezing your shoulder blades together, slowly raise the bar back to the starting position when your arms are fully extended and the lats are fully stretched. Inhale during this portion of the movement.\nRepeat this motion for the prescribed amount of repetitions.",
					"https://www.youtube.com/watch?v=JEb-dwU3VF4", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Seated cable rows",
					diff,
					"For this exercise you will need access to a low pulley row machine with a V-bar. Note: The V-bar will enable you to have a neutral grip where the palms of your hands face each other. To get into the starting position, first sit down on the machine and place your feet on the front platform or crossbar provided making sure that your knees are slightly bent and not locked.\nLean over as you keep the natural alignment of your back and grab the V-bar handles.\nWith your arms extended pull back until your torso is at a 90-degree angle from your legs. Your back should be slightly arched and your chest should be sticking out. You should be feeling a nice stretch on your lats as you hold the bar in front of you. This is the starting position of the exercise.\nKeeping the torso stationary, pull the handles back towards your torso while keeping the arms close to it until you touch the abdominals. Breathe out as you perform that movement. At that point you should be squeezing your back muscles hard. Hold that contraction for a second and slowly go back to the original position while breathing in.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=GZbfZ033f74", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Hyperextensions",
					diff,
					"Lie face down on a hyperextension bench, tucking your ankles securely under the footpads.\nAdjust the upper pad if possible so your upper thighs lie flat across the wide pad, leaving enough room for you to bend at the waist without any restriction.\nWith your body straight, cross your arms in front of you (my preference) or behind your head. This will be your starting position. Tip: You can also hold a weight plate for extra resistance in front of you under your crossed arms.\nStart bending forward slowly at the waist as far as you can while keeping your back flat. Inhale as you perform this movement. Keep moving forward until you feel a nice stretch on the hamstrings and you can no longer keep going without a rounding of the back. Tip: Never round the back as you perform this exercise. Also, some people can go farther than others. The key thing is that you go as far as your body allows you to without rounding the back.\nSlowly raise your torso back to the initial position as you inhale. Tip: Avoid the temptation to arch your back past a straight line. Also, do not swing the torso at any time in order to protect the back from injury.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=ph3pddpKzzw", group
							.getId());
			exercise.save();

			// LEG EXERCISES
			group = new MuscleGroup(getActivity(), "Legs");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Leg extensions",
					diff,
					"For this exercise you will need to use a leg extension machine. First choose your weight and sit on the machine with your legs under the pad (feet pointed forward) and the hands holding the side bars. This will be your starting position. Tip: You will need to adjust the pad so that it falls on top of your lower leg (just above your feet). Also, make sure that your legs form a 90-degree angle between the lower and upper leg. If the angle is less than 90-degrees then that means the knee is over the toes which in turn creates undue stress at the knee joint. If the machine is designed that way, either look for another machine or just make sure that when you start executing the exercise you stop going down once you hit the 90-degree angle.\nUsing your quadriceps, extend your legs to the maximum as you exhale. Ensure that the rest of the body remains stationary on the seat. Pause a second on the contracted position.\nSlowly lower the weight back to the original position as you inhale, ensuring that you do not go past the 90-degree angle limit.\nRepeat for the recommended amount of times.",
					"https://www.youtube.com/watch?v=YyvSfVjQeL0", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Leg curls",
					diff,
					"Adjust the machine lever to fit your height and lie face down on the leg curl machine with the pad of the lever on the back of your legs (just a few inches under the calves).\nKeeping the torso flat on the bench, ensure your legs are fully stretched and grab the side handles of the machine. Position your toes straight (or you can also use any of the other two stances described on the foot positioning section). This will be your starting position.\nAs you exhale, curl your legs up as far as possible without lifting the upper legs from the pad. Once you hit the fully contracted position, hold it for a second.\nAs you inhale, bring the legs back to the initial position. Repeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=1Tq3QdYUuHs", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Leg press",
					diff,
					"Using a leg press machine, sit down on the machine and place your legs on the platform directly in front of you at a medium (shoulder width) foot stance.\nLower the safety bars holding the weighted platform in place and press the platform all the way up until your legs are fully extended in front of you. Tip: Make sure that you do not lock your knees. Your torso and the legs should make a perfect 90-degree angle. This will be your starting position.\nAs you inhale, slowly lower the platform until your upper and lower legs make a 90-degree angle.\nPushing mainly with the heels of your feet and using the quadriceps go back to the starting position as you exhale.\nRepeat for the recommended amount of repetitions and ensure to lock the safety pins properly once you are done. You do not want that platform falling on you fully loaded.",
					"https://www.youtube.com/watch?v=IZxyjW7MPJQ", group
							.getId());
			exercise.save();

			// SHOULDERS EXERCISES
			group = new MuscleGroup(getActivity(), "Shoulders");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Shoulder press – smith mashine",
					diff,
					"To begin, place a flat bench (or preferably one with back support) underneath a smith machine. Position the barbell at a height so that when seated on the flat bench, the arms must be almost fully extended to reach the barbell.\nOnce you have the correct height, sit slightly in behind the barbell so that there is an imaginary straight line from the tip of your nose to the barbell. Your feet should be stationary. Grab the barbell with the palms facing forward, unlock it and lift it up so that your arms are fully extended. This is the starting position.\nSlowly begin to lower the barbell until it is level with your chin while inhaling.\nThen lift the barbell back to the starting position using your shoulders while exhaling.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=wQ6OIcC1mQ0", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Lateral raise",
					diff,
					"Pick a couple of dumbbells and stand with a straight torso and the dumbbells by your side at arms length with the palms of the hand facing you. This will be your starting position.\nWhile maintaining the torso in a stationary position (no swinging), lift the dumbbells to your side with a slight bend on the elbow and the hands slightly tilted forward as if pouring water in a glass. Continue to go up until you arms are parallel to the floor. Exhale as you execute this movement and pause for a second at the top.\nLower the dumbbells back down slowly to the starting position as you inhale.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=3VcKaXpzqRo", group
							.getId());
			exercise.save();

			// BICEPS EXERCISES
			group = new MuscleGroup(getActivity(), "Biceps");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Straight bar curls",
					diff,
					"Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.\nWhile holding the upper arms stationary, curl the weights forward while contracting the biceps as you breathe out. Tip: Only the forearms should move.\nContinue the movement until your biceps are fully contracted and the bar is at shoulder level. Hold the contracted position for a second and squeeze the biceps hard.\nSlowly begin to bring the bar back to starting position as your breathe in.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=LY1V6UbRHFM", group
							.getId());
			exercise.save();

			// TRICEPS EXERCISES
			group = new MuscleGroup(getActivity(), "Triceps");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Tricep pushdown",
					diff,
					"Attach a straight or angled bar to a high pulley and grab with an overhand grip (palms facing down) at shoulder width.\nStanding upright with the torso straight and a very small inclination forward, bring the upper arms close to your body and perpendicular to the floor. The forearms should be pointing up towards the pulley as they hold the bar. This is your starting position.\nUsing the triceps, bring the bar down until it touches the front of your thighs and the arms are fully extended perpendicular to the floor. The upper arms should always remain stationary next to your torso and only the forearms should move. Exhale as you perform this movement.\nAfter a second hold at the contracted position, bring the bar slowly up to the starting point. Breathe in as you perform this step.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=2-LAMcpzODU", group
							.getId());
			exercise.save();

			// ABS EXERCISES
			group = new MuscleGroup(getActivity(), "Abdominal");
			group.save();
			exercise = new Exercise(
					getActivity(),
					"Cable crunch",
					diff,
					"Kneel below a high pulley that contains a rope attachment.\nGrasp cable rope attachment and lower the rope until your hands are placed next to your face.\nFlex your hips slightly and allow the weight to hyperextend the lower back. This will be your starting position.\nWith the hips stationary, flex the waist as you contract the abs so that the elbows travel towards the middle of the thighs. Exhale as you perform this portion of the movement and hold the contraction for a second.\nSlowly return to the starting position as you inhale. Tip: Make sure that you keep constant tension on the abs throughout the movement. Also, do not choose a weight so heavy that the lower back handles the brunt of the work.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=2fbujeH3F0E", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Bent knee raises",
					diff,
					"Lay flat on the floor with your arms next to your sides.\nNow bend your knees at around a 75 degree angle and lift your feet off the floor by around 2 inches.\nUsing your lower abs, bring your knees in towards you as you maintain the 75 degree angle bend in your legs. Continue this movement until you raise your hips off of the floor by rolling your pelvis backward. Breathe out as you perform this portion of the movement. Tip: At the end of the movement your knees will be over your chest.\nSqueeze your abs at the top of the movement for a second and then return to the starting position slowly as you breathe in. Tip: Maintain a controlled motion at all times.\nRepeat for the recommended amount of repetitions.",
					"https://www.youtube.com/watch?v=lMhAj27-n3w", group
							.getId());
			exercise.save();
			exercise = new Exercise(
					getActivity(),
					"Cross body crunch",
					diff,
					"Lie flat on your back and bend your knees about 60 degrees.\nKeep your feet flat on the floor and place your hands loosely behind your head. This will be your starting position.\nNow curl up and bring your right elbow and shoulder across your body while bring your left knee in toward your left shoulder at the same time. Reach with your elbow and try to touch your knee. Exhale as you perform this movement. Tip: Try to bring your shoulder up towards your knee rather than just your elbow and remember that the key is to contract the abs as you perform the movement; not just to move the elbow.\nNow go back down to the starting position as you inhale and repeat with the left elbow and the right knee.\nContinue alternating in this manner until all prescribed repetitions are done.",
					"https://www.youtube.com/watch?v=cDIYH5rH0qU", group
							.getId());
			exercise.save();

		}
	}*/
	
	public static String POST() {
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            List<Trainee> trainees = Trainee.find(Trainee.class, "sync =?", "false");
            //List<Trainee> trainees = Trainee.listAll(Trainee.class);
            // 2. make POST request to the given URL
            if(!trainees.isEmpty()){
            	Log.i("Upload", "neni empty");
            	HttpPost httpPost = new HttpPost("http://192.168.1.100:1188/api/trainees");
            	String json = "";
            	for (Trainee trainee : trainees) {
            		JSONObject jsonObject = new JSONObject(trainee.JSONString());
            		json = jsonObject.toString();
            		StringEntity se = new StringEntity(json);
            		httpPost.setEntity(se);
            		httpPost.setHeader("Accept", "application/json");
    	            httpPost.setHeader("Content-type", "application/json");
    	            HttpResponse httpResponse = httpclient.execute(httpPost);
    	            inputStream = httpResponse.getEntity().getContent();
    	            if(inputStream != null) {
    	                result = convertInputStreamToString(inputStream);
    	                Log.i("Upload", "proslo to");
    	                Log.i("Upload", "result:"+result);
    	            }
    	            else {
    	                result = "null";
    	                Log.i("Upload", "neproslo to");
    	            }
    	            jsonObject = new JSONObject(result);
	            	if(jsonObject!=null) {
	            		trainee.setWebId(jsonObject.getInt("id"));
	            		trainee.setSync(true);
	            		trainee.save();
	            	}
				}
            	Log.i("Upload result", result);
            	
            }
            
            List<Measure> measures = Measure.find(Measure.class, "sync =?", "false");
            //List<Measure> measures = Measure.listAll(Measure.class);
            if(!measures.isEmpty()){
            	Log.i("Upload", "neni empty");
            	HttpPost httpPost = new HttpPost("http://192.168.1.100:1188/api/measures");
            	String json = "";
            	for (Measure measure : measures) {
            		JSONObject jsonObject = new JSONObject(measure.JSONString());
            		json = jsonObject.toString();
            		StringEntity se = new StringEntity(json);
            		httpPost.setEntity(se);
            		httpPost.setHeader("Accept", "application/json");
    	            httpPost.setHeader("Content-type", "application/json");
    	            HttpResponse httpResponse = httpclient.execute(httpPost);
    	            inputStream = httpResponse.getEntity().getContent();
    	            if(inputStream != null) {
    	                result = convertInputStreamToString(inputStream);
    	                Log.i("Upload", "proslo to");
    	                Log.i("Upload", "result:"+result);
    	            }
    	            else {
    	                result = "null";
    	                Log.i("Upload", "neproslo to");
    	            }
    	            jsonObject = new JSONObject(result);
	            	if(jsonObject!=null) {
	            		measure.setWebId(jsonObject.getInt("id"));
	            		measure.setSync(true);
	            		measure.save();
	            	}
				}
            	Log.i("Upload result", result);
            	
            }
            
            List<Training> trainings = Training.find(Training.class, "sync =?", "false");
            //List<Training> trainings = Training.listAll(Training.class);
            if(!measures.isEmpty()){
            	Log.i("Upload", "neni empty");
            	HttpPost httpPost = new HttpPost("http://192.168.1.100:1188/api/trainings");
            	String json = "";
            	for (Training training : trainings) {
            		JSONObject jsonObject = new JSONObject(training.JSONString());
            		json = jsonObject.toString();
            		StringEntity se = new StringEntity(json);
            		httpPost.setEntity(se);
            		httpPost.setHeader("Accept", "application/json");
    	            httpPost.setHeader("Content-type", "application/json");
    	            HttpResponse httpResponse = httpclient.execute(httpPost);
    	            inputStream = httpResponse.getEntity().getContent();
    	            if(inputStream != null) {
    	                result = convertInputStreamToString(inputStream);
    	                Log.i("Upload", "proslo to");
    	                Log.i("Upload", "result:"+result);
    	            }
    	            else {
    	                result = "null";
    	                Log.i("Upload", "neproslo to");
    	            }
    	            jsonObject = new JSONObject(result);
	            	if(jsonObject!=null) {
	            		training.setWebId(jsonObject.getInt("id"));
	            		training.setSync(true);
	            		training.save();
	            	}
				}
            	Log.i("Upload result", result);
            	
            }
            
            List<Workout> workouts = Workout.find(Workout.class, "sync =?", "false");
            //List<Workout> workouts = Workout.listAll(Workout.class);
            if(!measures.isEmpty()){
            	Log.i("Upload", "neni empty");
            	HttpPost httpPost = new HttpPost("http://192.168.1.100:1188/api/workouts");
            	String json = "";
            	for (Workout workout : workouts) {
            		JSONObject jsonObject = new JSONObject(workout.JSONString());
            		json = jsonObject.toString();
            		StringEntity se = new StringEntity(json);
            		httpPost.setEntity(se);
            		httpPost.setHeader("Accept", "application/json");
    	            httpPost.setHeader("Content-type", "application/json");
    	            HttpResponse httpResponse = httpclient.execute(httpPost);
    	            inputStream = httpResponse.getEntity().getContent();
    	            if(inputStream != null) {
    	                result = convertInputStreamToString(inputStream);
    	                Log.i("Upload", "proslo to");
    	                Log.i("Upload", "result:"+result);
    	            }
    	            else {
    	                result = "null";
    	                Log.i("Upload", "neproslo to");
    	            }
    	            jsonObject = new JSONObject(result);
	            	if(jsonObject!=null) {
	            		workout.setWebId(jsonObject.getInt("id"));
	            		workout.setSync(true);
	            		workout.save();
	            	}
				}
            	Log.i("Upload result", result);
            	
            }
            
            List<ExerciseUnit> exerciseUnits = ExerciseUnit.find(ExerciseUnit.class, "sync =?", "false");
            //List<ExerciseUnit> exerciseUnits = ExerciseUnit.listAll(ExerciseUnit.class);
            if(!measures.isEmpty()){
            	Log.i("Upload", "neni empty");
            	HttpPost httpPost = new HttpPost("http://192.168.1.100:1188/api/exerciseunits");
            	String json = "";
            	for (ExerciseUnit exerciseUnit : exerciseUnits) {
            		JSONObject jsonObject = new JSONObject(exerciseUnit.JSONString());
            		json = jsonObject.toString();
            		Log.i("Upload serie exerciseunit", json);
            		StringEntity se = new StringEntity(json);
            		httpPost.setEntity(se);
            		httpPost.setHeader("Accept", "application/json");
    	            httpPost.setHeader("Content-type", "application/json");
    	            HttpResponse httpResponse = httpclient.execute(httpPost);
    	            inputStream = httpResponse.getEntity().getContent();
    	            if(inputStream != null) {
    	                result = convertInputStreamToString(inputStream);
    	                Log.i("Upload", "proslo to");
    	                Log.i("Upload", "result:"+result);
    	            }
    	            else {
    	                result = "null";
    	                Log.i("Upload", "neproslo to");
    	            }
    	            jsonObject = new JSONObject(result);
	            	if(jsonObject!=null) {
	            		exerciseUnit.setWebId(jsonObject.getInt("id"));
	            		exerciseUnit.setSync(true);
	            		exerciseUnit.save();
	            	}
				}
            	Log.i("Upload result", result);
            	
            }
            
            List<Serie> series = Serie.find(Serie.class, "sync =?", "false");
            //List<Serie> series = Serie.listAll(Serie.class);
            if(!series.isEmpty()){
            	Log.i("Upload", "neni empty");
            	HttpPost httpPost = new HttpPost("http://192.168.1.100:1188/api/series");
            	String json = "";
            	for (Serie serie : series) {
            		JSONObject jsonObject = new JSONObject(serie.JSONString());
            		json = jsonObject.toString();
            		Log.i("Upload serie json", json);
            		StringEntity se = new StringEntity(json);
            		httpPost.setEntity(se);
            		httpPost.setHeader("Accept", "application/json");
    	            httpPost.setHeader("Content-type", "application/json");
    	            HttpResponse httpResponse = httpclient.execute(httpPost);
    	            inputStream = httpResponse.getEntity().getContent();
    	            if(inputStream != null) {
    	                result = convertInputStreamToString(inputStream);
    	                Log.i("Upload", "proslo to");
    	                Log.i("Upload", "result:"+result);
    	            }
    	            else {
    	                result = "null";
    	                Log.i("Upload", "neproslo to");
    	            }
    	            jsonObject = new JSONObject(result);
	            	if(jsonObject!=null) {
	            		serie.setWebId(jsonObject.getInt("id"));
	            		serie.setSync(true);
	            		serie.save();
	            	}
				}
            	Log.i("Upload result", result);
            	
            }
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        // 11. return result
        
        return result;
    }	
   
    private class dataUL extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST();
        }

    }
    
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
}
