/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;

/**
 * @author Petr Hruska
 * 
 */
public class DownloadFragment extends Fragment {
	EditText text;
	
	public DownloadFragment() {
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		/*Log.i("Download Fragment", "started");
		String readTwitterFeed = readTwitterFeed();
		try {
			JSONArray jsonArray = new JSONArray(readTwitterFeed);
			Log.i(DownloadFragment.class.getName(), "Number of entries "
					+ jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i(DownloadFragment.class.getName(),
						jsonObject.getString("text"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		new HttpAsyncTask().execute("http://192.168.1.100:1188/api/exerciseunits/8");
		//getActivity().finish();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_download, null);
		text = (EditText) view.findViewById(R.id.jsonText);
		return view;
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	if(result.contains("null")) {
        		Log.i("Save exerciseUnit", "Naprdet");
        	} else {
            JSONArray json = null;
            try {
                json = new JSONArray(result);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            
            if(json.length() == 0) {
            	Log.i("Save exerciseUnit", "Naprdet");
			} else {
				JSONObject jsonObject = null;
				for(int i =0;i<json.length();i++) {
					try {
						jsonObject = json.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ExerciseUnit exerciseUnit;
					try {
						text.setText(json.toString());
						exerciseUnit = new ExerciseUnit(getActivity(), jsonObject.getInt("id"), jsonObject.getInt("exerciseId"), Long.valueOf("9"));
						exerciseUnit.save();
						Log.i("Save exerciseUnit", exerciseUnit.JSONString());
						exerciseUnit.delete();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}				
			}
        	}
       }
        
        
    }

	
	public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
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

	public String readTwitterFeed() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		Log.i("Download Fragment", "before start");
		HttpGet httpGet = new HttpGet(
				"http://hmkcode.appspot.com/rest/controller/get.json");
		Log.i("Download Fragment", "JSON obtained");
		try {
			Log.i("Download Fragment", "try started");
			HttpResponse response = client.execute(httpGet);
			Log.i("Download Fragment", "response");
			StatusLine statusLine = response.getStatusLine();
			Log.i("Download Fragment", "status line");
			int statusCode = statusLine.getStatusCode();
			Log.i("Download Fragment", "before if");
			if (statusCode == 200) {
				Log.i("Download Fragment", "inside if");
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(DownloadFragment.class.toString(),
						"Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
