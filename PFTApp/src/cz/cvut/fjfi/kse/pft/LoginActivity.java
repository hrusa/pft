package cz.cvut.fjfi.kse.pft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import cz.cvut.fjfi.kse.pft.db.Trainee;

public class LoginActivity extends FragmentActivity {
	Bundle args = new Bundle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		/*Odkomentovat az budu na linuxu a muzeme spustit login via google acc
		if (savedInstanceState == null) {
			checkGooglePlayServicesAvailable();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new LoginFragment(), "Login").addToBackStack(null).commit();
		}*/
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new UploadFragment(), "Upload").commit();
		
		//Docasne reseni bez Google loginu, pak smazat
		/*List<Trainee> trainee = Trainee.find(Trainee.class, "email = ?",
				"hruskapetr89@gmail.com");
		if (trainee.isEmpty()) {
			args.putString("name", "Hrusa");
			args.putString("email", "hruskapetr89@gmail.com");
			BirthdateDFragment dialog = new BirthdateDFragment();
			dialog.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, dialog).commit();
		} else {
			args.clear();
			args.putLong("trainee", trainee.get(0).getId());
			MenuFragment fragment = new MenuFragment();
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "Menu").commit();
		}*/
		//konec mazani

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
