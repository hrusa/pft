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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import cz.cvut.fjfi.kse.pft.db.Attribute;
import cz.cvut.fjfi.kse.pft.db.Difficulty;
import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Measure;
import cz.cvut.fjfi.kse.pft.db.MuscleGroup;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Trainee;
import cz.cvut.fjfi.kse.pft.db.Training;
import cz.cvut.fjfi.kse.pft.db.Workout;

/**
 * @author Petr Hruška
 * 
 */
public class LoginFragment extends Fragment implements ConnectionCallbacks,
		OnConnectionFailedListener, OnClickListener,
		ResultCallback<People.LoadPeopleResult> {
	Bundle args = new Bundle();
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;
	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;
	/*
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;
	/*
	 * Track whether the sign-in button has been clicked so that we know to
	 * resolve all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;
	/*
	 * Store the connection result from onConnectionFailed callbacks so that we
	 * can resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;
	private PendingIntent mSignInIntent;
	private TextView mUser;
	private SignInButton mSignInButton;
	private Button mSignOutButton;
	private String email, name = "";

	private AccountManager mAccountManager;

	/**
	 * 
	 */
	public LoginFragment() {
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
		List<Difficulty> diffs = Difficulty.listAll(Difficulty.class);
		if (diffs.isEmpty()) {
			new difficultyDL()
					.execute("http://192.168.1.100:1188/api/difficulties/");
			Log.i("login", "po startu asyncu");
		}
		Log.i("onCreate: ", "started");

		mGoogleApiClient = buildGoogleApiClient();
		Log.i("onCreate: ", "init");
	}

	private GoogleApiClient buildGoogleApiClient() {
		return new GoogleApiClient.Builder(getActivity())
				// možná změníme parametr na getActivity().getBaseContext()
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API, null)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
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
		View rootView = inflater.inflate(R.layout.fragment_login, container,
				false);
		mUser = (TextView) rootView.findViewById(R.id.sign_in_status);
		mSignInButton = (SignInButton) rootView
				.findViewById(R.id.sign_in_button);
		mSignOutButton = (Button) rootView.findViewById(R.id.sign_out_button);
		rootView.findViewById(R.id.sign_in_button).setOnClickListener(this);
		rootView.findViewById(R.id.sign_out_button).setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	public void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	// nemůžu to dát na ondestroy
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.
	 * OnConnectionFailedListener
	 * #onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (!mIntentInProgress) {
			// Store the ConnectionResult so that we can use it later when the
			// user clicks
			// 'sign-in'.
			mConnectionResult = result;
			mSignInIntent = result.getResolution();

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
		mUser.setText(R.string.status_sign_out);
		mSignInButton.setEnabled(true);
		mSignOutButton.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
	 * #onConnected(android.os.Bundle)
	 */
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		mSignInClicked = false;
		Toast.makeText(getActivity(), "User is connected!", Toast.LENGTH_LONG)
				.show();
		Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		mUser.setText(String.format(
				getResources().getString(R.string.status_sign_in),
				currentUser.getDisplayName()));
		mSignInButton.setEnabled(false);
		mSignOutButton.setEnabled(true);
		List<Trainee> trainee = Trainee.find(Trainee.class, "email = ?",
				getAccountNames());
		if (trainee.isEmpty()) {
			// args.putString("name", currentUser.getDisplayName());
			// args.putString("email", getAccountNames().split("@")[0]);
			name = currentUser.getDisplayName();
			email = getAccountNames().split("@")[0];
			new traineeDL().execute("http://192.168.1.100:1188/api/trainees/"
					+ email);
		} else {
			args.clear();
			args.putLong("trainee", trainee.get(0).getId());
			MenuFragment fragment = new MenuFragment();
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "Menu").commit();
		}
	}

	private String getAccountNames() {
		mAccountManager = AccountManager.get(getActivity());
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		return accounts[0].name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
	 * #onConnectionSuspended(int)
	 */
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
	}

	public void connect(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != Activity.RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				getActivity().startIntentSenderForResult(
						mSignInIntent.getIntentSender(), RC_SIGN_IN, null, 0,
						0, 0);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (((LoginActivity) getActivity()).isNetworkAvailable()) {
			if (!mGoogleApiClient.isConnecting()) {
				switch (view.getId()) {
				case R.id.sign_in_button:
					mSignInClicked = true;
					resolveSignInError();
					break;
				case R.id.sign_out_button:
					Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
					Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
							.setResultCallback(new ResultCallback<Status>() {

								public void onResult(Status status) {
									// mGoogleApiClient is now disconnected and
									// access has been revoked.
									// Trigger app logic to comply with the
									// developer policies
								}
							});
					mGoogleApiClient.connect();
					mUser.setText(R.string.status_sign_out);
					mSignInButton.setEnabled(true);
					mSignOutButton.setEnabled(false);
					break;
				}
			}

		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Internet connection error")
					.setMessage(
							"You have no internet connection, please establish one.")
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											Settings.ACTION_WIFI_SETTINGS);
									startActivity(intent);
								}
							});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.common.api.ResultCallback#onResult(com.google.
	 * android.gms.common.api.Result)
	 */
	@Override
	public void onResult(LoadPeopleResult arg0) {
		// TODO Auto-generated method stub

	}

	// AsyncTask

	private class difficultyDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			Log.i("async diff", "started");
			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				Log.i("async diff", "tvorba json arraye");
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			JSONObject jsonObject = null;
			for (int i = 0; i < json.length(); i++) {
				try {
					Log.i("async diff", "tvorba json objectu");
					jsonObject = json.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("async diff", "inicializace diff");
				Difficulty difficulty;
				try {
					difficulty = new Difficulty(getActivity(),
							jsonObject.getInt("id"),
							jsonObject.getString("name"));
					difficulty.save();
					Log.i("Difficulty:", difficulty.JSONString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			new attributeDL()
					.execute("http://192.168.1.100:1188/api/attributes");
		}
	}

	private class attributeDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			JSONObject jsonObject = null;
			for (int i = 0; i < json.length(); i++) {
				try {
					jsonObject = json.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Attribute attribute;
				try {
					attribute = new Attribute(getActivity(),
							jsonObject.getInt("id"),
							jsonObject.getString("name"));
					attribute.save();
					//Log.i("Attribute:", attribute.JSONString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			new muscleGroupDL()
					.execute("http://192.168.1.100:1188/api/musclegroups");
		}
	}

	private class muscleGroupDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			JSONObject jsonObject = null;
			for (int i = 0; i < json.length(); i++) {
				try {
					jsonObject = json.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MuscleGroup muscleGroup;
				try {
					muscleGroup = new MuscleGroup(getActivity(),
							jsonObject.getInt("id"),
							jsonObject.getString("name"));
					muscleGroup.save();
					//Log.i("MuscleGroup:", muscleGroup.JSONString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			new exerciseDL().execute("http://192.168.1.100:1188/api/exercises");
		}
	}

	private class exerciseDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			JSONObject jsonObject = null;
			for (int i = 0; i < json.length(); i++) {
				try {
					jsonObject = json.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Exercise exercise;
				try {
					exercise = new Exercise(getActivity(),
							jsonObject.getInt("id"),
							jsonObject.getString("name"),
							jsonObject.getLong("difficultyId"),
							jsonObject.getString("description"),
							jsonObject.getString("video"),
							jsonObject.getLong("musclegroupId"));
					exercise.save();
					Log.i("Exercise:", exercise.JSONString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			new traineeDL().execute("http://192.168.1.100:1188/api/trainees/hruskapetr89");
		}
	}

	private class traineeDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONObject json = null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			if (json == null) {
				args.putString("name", name);
				args.putString("email", email);
				BirthdateDFragment dialog = new BirthdateDFragment();
				dialog.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, dialog).commit();
			} else {
				Trainee trainee;
				try {
					trainee = new Trainee(getActivity(), json.getInt("id"),
							json.getString("name"), json.getString("email"),
							json.getString("birth").split("T")[0],
							json.getInt("gender"), json.getInt("experience"),
							json.getInt("goal"));
					trainee.save();
					args.clear();
					args.putLong("trainee", trainee.getId());
					new trainingDL()
							.execute("http://192.168.1.100:1188/api/trainings/"
									+ trainee.getWebId());
					Log.i("SAve trainee", trainee.JSONString());
					List<Attribute> attrs = Attribute.listAll(Attribute.class);
					for (Attribute attribute : attrs) {
						new measureDL()
								.execute("http://192.168.1.100:1188/api/measures/"
										+ trainee.getWebId()
										+ "/"
										+ attribute.getWebId());
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	private class measureDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONObject json = null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			if (json == null) {

			} else {
				Measure measure;
				try {
					measure = new Measure(getActivity(), json.getInt("id"),
							args.getLong("trainee"),
							json.getLong("attributeId"),
							json.getString("date"), json.getInt("value"));
					measure.save();
					Log.i("SAve measure", measure.JSONString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	private class trainingDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
				JSONArray json = null;
				try {
					json = new JSONArray(result);
				} catch (JSONException e) {
					Log.e("JSON Parser", "Error parsing data " + e.toString());
				}
				if(json==null) {
					Log.i("Save training result:", result);
					MenuFragment fragment = new MenuFragment();
					fragment.setArguments(args);
					getFragmentManager().beginTransaction()
							.replace(R.id.container, fragment, "Menu").commit();
				} else {
				JSONObject jsonObject = null;
				for (int i = 0; i < json.length(); i++) {
					try {
						jsonObject = json.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Training training;
					try {
						training = new Training(getActivity(),
								jsonObject.getInt("id"),
								args.getLong("trainee"),
								jsonObject.getString("name"));
						training.save();
						args.remove("training");
						args.putLong("training", training.getId());
						new workoutDL()
								.execute("http://192.168.1.100:1188/api/workouts/"
										+ training.getWebId());
						Log.i("Save training", training.JSONString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}

	private class workoutDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			if(json==null) {
				Log.i("Save training result:", result);
				MenuFragment fragment = new MenuFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "Menu").commit();
			} else {
				JSONObject jsonObject = null;
				for (int i = 0; i < json.length(); i++) {
					try {
						jsonObject = json.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Workout workout;
					try {
						workout = new Workout(getActivity(),
								jsonObject.getInt("id"),
								args.getLong("training"),
								jsonObject.getString("name"), jsonObject
										.getString("date").split("T")[0]);
						workout.save();
						args.remove("workout");
						args.putLong("workout", workout.getId());
						Log.i("Save workout", workout.JSONString());
						new exerciseUnitDL()
								.execute("http://192.168.1.100:1188/api/exerciseunits/"
										+ workout.getWebId());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

	}

	private class exerciseUnitDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			if(json==null) {
				Log.i("Save serie result:", result);
				MenuFragment fragment = new MenuFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "Menu").commit();
			} else {
				JSONObject jsonObject = null;
				for (int i = 0; i < json.length(); i++) {
					try {
						jsonObject = json.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ExerciseUnit exerciseUnit;
					try {
						exerciseUnit = new ExerciseUnit(getActivity(),
								jsonObject.getInt("id"),
								jsonObject.getInt("exerciseId"),
								args.getLong("workout"));
						exerciseUnit.save();
						args.remove("exerciseunit");
						args.putLong("exerciseunit", exerciseUnit.getId());
						Log.i("Save exerciseunit", exerciseUnit.JSONString());
						new serieDL()
								.execute("http://192.168.1.100:1188/api/series/"
										+ exerciseUnit.getWebId());
						Log.i("Pokus o pridani serii z unitu:", exerciseUnit.getWebId()+"");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

	}

	private class serieDL extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			JSONArray json = null;
			try {
				json = new JSONArray(result);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			if(json==null) {
				Log.i("Save training result:", result);
				/*MenuFragment fragment = new MenuFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction()
						.replace(R.id.container, fragment, "Menu").commit();*/
				getFragmentManager().beginTransaction()
				.replace(R.id.container, new UploadFragment(), "Upload")
				.commit();
			} else {
				JSONObject jsonObject = null;
				for (int i = 0; i < json.length(); i++) {
					try {
						jsonObject = json.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Serie serie;
					try {
						serie = new Serie(getActivity(),
								jsonObject.getInt("id"),
								args.getLong("exerciseunit"),
								jsonObject.getInt("weight"),
								jsonObject.getInt("repetition"),
								jsonObject.getInt("pause"));
						serie.save();
						Log.i("Save serie", serie.JSONString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			/*MenuFragment fragment = new MenuFragment();
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "Menu").commit();*/
			
		}

	}

	public static String GET(String url) {
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
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}
}
