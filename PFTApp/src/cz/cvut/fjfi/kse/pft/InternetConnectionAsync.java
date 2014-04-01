/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

/**
 * @author Petr Hruška
 *
 */
public class InternetConnectionAsync extends AsyncTask<Void, Void, Boolean>{

	LoginActivity context;
	Boolean result;
	/**
	 * 
	 */
	public InternetConnectionAsync(LoginActivity context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		result = hasActiveInternetConnection();
		
		return null;
	}

	@Override
	protected void onPostExecute(Boolean res) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Internet connection error")
		.setMessage("You have no internet connection, please establish one.")
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int which) {
				 Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				 context.startActivity(intent);
				 }
				});
		AlertDialog alertDialog = builder.create();
		if (!result) {
			alertDialog.show();
		}
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	public boolean hasActiveInternetConnection() {
	    if (isNetworkAvailable()) {
	        try {
	            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
	            urlc.setRequestProperty("User-Agent", "Test");
	            urlc.setRequestProperty("Connection", "close");
	            urlc.setConnectTimeout(6000); 
	            urlc.connect();
	            return (urlc.getResponseCode() == 200);
	        } catch (IOException e) {
	            Log.e("Internet test", "Error checking internet connection");
	        }
	    } else {
	        Log.d("Internet test", "No network available!");
	    }
	    return false;
	}
}
