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

import com.orm.SugarRecord;

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
		getActivity().getActionBar().setTitle("PFTApp");
		trainings = SugarRecord.listAll(Training.class);

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
		
		if (((MainActivity) getActivity()).isNetworkAvailable()) {
			new dataUL().execute();
		}
		return view;
	}

	
	
	public static String POST() {
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            List<Trainee> trainees = SugarRecord.find(Trainee.class, "sync =?", "false");
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
            
            List<Measure> measures = SugarRecord.find(Measure.class, "sync =?", "false");
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
            
            List<Training> trainings = SugarRecord.find(Training.class, "sync =?", "false");
            //List<Training> trainings = Training.listAll(Training.class);
            if(!trainings.isEmpty()){
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
            
            List<Workout> workouts = SugarRecord.find(Workout.class, "sync =?", "false");
            //List<Workout> workouts = Workout.listAll(Workout.class);
            if(!workouts.isEmpty()){
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
            
            List<ExerciseUnit> exerciseUnits = SugarRecord.find(ExerciseUnit.class, "sync =?", "false");
            //List<ExerciseUnit> exerciseUnits = ExerciseUnit.listAll(ExerciseUnit.class);
            if(!exerciseUnits.isEmpty()){
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
            
            List<Serie> series = SugarRecord.find(Serie.class, "sync =?", "false");
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
