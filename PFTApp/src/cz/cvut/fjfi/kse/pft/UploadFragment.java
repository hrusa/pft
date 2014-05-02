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
import cz.cvut.fjfi.kse.pft.db.Attribute;

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
		List<Attribute> attrs = Attribute.listAll(Attribute.class);
		for (Attribute attribute : attrs) {
			Log.i("Attribute", attribute.toString());
		}
		attrs.get(0).setName("Vyska");
		Log.i("Zmena jmena na", ""+attrs.get(0).getName());
		attrs.get(0).save();
		List<Attribute> attrs2 = Attribute.listAll(Attribute.class);
		for (Attribute attribute : attrs2) {
			Log.i("Attribute", attribute.toString());
		}

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_upload, null);
		text = (TextView) view.findViewById(R.id.resultText);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
		
		public static String POST(String url) {
	        InputStream inputStream = null;
	        String result = "";
	        try {
	 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost(url);
	            String json = "";
	            Attribute attribute = Attribute.findById(Attribute.class, Long.parseLong("1"));
	            attribute.setId(Long.parseLong("25"));
	            attribute.save();
	            String attr = attribute.JSONString();
	            Log.i("Test JSONString", attr);
	            // 3. build jsonObject
	            //String attr = "{\"id\":3,\"name\":\"Pupek\"}";
	            JSONObject jsonObject = new JSONObject(attr);

	            // 4. convert JSONObject to JSON to String
	            json = jsonObject.toString();
	            Log.i("Test json", json);
	            // 5. set json to StringEntity
	            StringEntity se = new StringEntity(json);
	 
	            // 6. set httpPost Entity
	            httpPost.setEntity(se);
	 
	            // 7. Set some headers to inform server about the type of the content   
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
	 
	            // 8. Execute POST request to the given URL
	            HttpResponse httpResponse = httpclient.execute(httpPost);
	 
	            // 9. receive response as inputStream
	            inputStream = httpResponse.getEntity().getContent();
	 
	            // 10. convert inputstream to string
	            if(inputStream != null) {
	                result = convertInputStreamToString(inputStream);
	                Log.i("Upload", "proslo to");
	                Log.i("Upload", "result:"+result);
	            }
	            else {
	                result = "Did not work!";
	                Log.i("Upload", "neproslo to");
	            }
	 
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	        // 11. return result
	        
	        return result;
	    }	
	   
	    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	            return POST(urls[0]);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        /*@Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(getActivity(), "Data Sent!", Toast.LENGTH_LONG).show();
	       }*/
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
