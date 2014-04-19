package cz.cvut.fjfi.kse.pft;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LoginActivity extends FragmentActivity {
	public MenuItem addTraining, addWorkout, addExercise, addSerie;
	Bundle args = new Bundle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		if (savedInstanceState == null) {
			checkGooglePlayServicesAvailable();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new LoginFragment(), "Login").addToBackStack(null).commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkGooglePlayServicesAvailable();
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
}
