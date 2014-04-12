package cz.cvut.fjfi.kse.pft;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LoginActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if (savedInstanceState == null) {
			checkGooglePlayServicesAvailable();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new TrainingFragment(), "Training").commit();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkGooglePlayServicesAvailable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_training, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.add_button:
            showAddWorkoutDialog();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
		if (fragment instanceof LoginFragment)
		    ((LoginFragment) fragment).connect(requestCode, responseCode, intent);
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	/*public boolean hasActiveInternetConnection() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Internet connection error")
		.setMessage("You have no internet connection, please establish one.")
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int which) {
				 Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				 startActivity(intent);
				 }
				});
		AlertDialog alertDialog = builder.create();
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
	            alertDialog.show();
	        }
	    } else {
	        Log.d("Internet test", "No network available!");
	        alertDialog.show();
	    }
	    return false;
	}*/
	
	private void checkGooglePlayServicesAvailable() {
	    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (status != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
	            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, 0);
	            dialog.setCancelable(false);
	            dialog.show();
	        } else {
	            Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
	            finish();
	        }
	    }
	}
	
	public void showBirthdateDialog() {
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.container, new BirthdateDFragment()).commit();
	}
	
	public void showBasicInfoDialog() {
		Log.i("Start showBasicInfoDialog", "jede");
		BasicInfoDFragment dialog = new BasicInfoDFragment();
		dialog.show(getSupportFragmentManager(), "BasicInfoD");
	}
	
	public void showWorkoutDialog() {
		WorkoutDFragment dialog = new WorkoutDFragment();
		dialog.show(getSupportFragmentManager(), "WorkoutD");
	}
	
	public void showAddWorkoutDialog() {
		Toast.makeText(this, "Pøidej workout", Toast.LENGTH_SHORT).show();
		AddWorkoutDFragment dialog = new AddWorkoutDFragment();
		dialog.show(getSupportFragmentManager(), "AddWorkoutD");
	}
}
