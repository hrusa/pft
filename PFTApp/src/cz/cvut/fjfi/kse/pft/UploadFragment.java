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
import android.view.ViewGroup;
import android.widget.TextView;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Measure;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Trainee;
import cz.cvut.fjfi.kse.pft.db.Training;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruska
 *
 */
public class UploadFragment extends Fragment{
	
	TextView text;
	
	public UploadFragment() {
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new HttpAsyncTask().execute("http://192.168.1.100:1188/api/restexercises");
	/*	List<Attribute> attrs = Attribute.listAll(Attribute.class);
		for (Attribute attribute : attrs) {
			Log.i("Attribute", attribute.toString());
		}
		attrs.get(0).setName("Vyska");
		Log.i("Zmena jmena na", ""+attrs.get(0).getName());
		attrs.get(0).save();
		List<Attribute> attrs2 = Attribute.listAll(Attribute.class);
		for (Attribute attribute : attrs2) {
			Log.i("Attribute", attribute.toString());
		}*/

		//new HttpAsyncTask().execute();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_upload, null);
		text = (TextView) view.findViewById(R.id.resultText);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
		
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
