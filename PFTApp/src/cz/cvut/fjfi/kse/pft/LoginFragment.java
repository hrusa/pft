/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
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

import cz.cvut.fjfi.kse.pft.db.Trainee;

/**
 * @author Petr Hruška
 * 
 */
public class LoginFragment extends Fragment implements ConnectionCallbacks,
		OnConnectionFailedListener, OnClickListener,
		ResultCallback<People.LoadPeopleResult> {
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
		Log.i("onCreateView: ", "layout init");
		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
		Log.i("onStart: ", "started");
	}

	@Override
	public void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
			Log.i("onStop: ", "disconnect");
		}
		Log.i("onStop: ", "ended");
	}

	// nemůžu to dát na ondestroy
	@Override
	public void onDestroy() {
		super.onDestroy();
		/*
		 * Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
		 * Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient); //smaže
		 * povolení k přístupu na google účet mGoogleApiClient =
		 * buildGoogleApiClient(); mGoogleApiClient.connect();
		 */
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
		Log.i("onCF: ", "started");
		if (!mIntentInProgress) {
			// Store the ConnectionResult so that we can use it later when the
			// user clicks
			// 'sign-in'.
			Log.i("onCF: ", "1 if");
			mConnectionResult = result;
			mSignInIntent = result.getResolution();

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
				Log.i("onCF: ", "2 if");
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
		Log.i("onConnected: ", "started");
		mSignInClicked = false;
		Toast.makeText(getActivity(), "User is connected!", Toast.LENGTH_LONG)
				.show();
		Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		mUser.setText(String.format(
				getResources().getString(R.string.status_sign_in),
				currentUser.getDisplayName()));
		mSignInButton.setEnabled(false);
		mSignOutButton.setEnabled(true);
		Log.i("Narozeniny: ", "" + currentUser.getBirthday()); // tyto informace
																// musejí být
																// veřejně
																// přístupné,
																// jinak je zde
																// uložena
																// hodnota null
		Log.i("Pozice: ", "" + currentUser.getCurrentLocation());
		// ((LoginActivity) getActivity()).showBirthdateDialog();
		List<Trainee> trainee = Trainee.find(Trainee.class, "email = ?",
				getAccountNames());
		if (trainee.isEmpty()) {
			Bundle args = new Bundle();
			args.putString("name", currentUser.getDisplayName());
			args.putString("email", getAccountNames());
			// Log.i("Has Gender", ""+currentUser.hasGender()); //píše true i
			// když je null, TODO: když zbyde čas tak nějak dodělat
			BirthdateDFragment dialog = new BirthdateDFragment();
			dialog.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, dialog).commit();
		} else {
			Toast.makeText(getActivity(), "Acc created", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private String getAccountNames() {
		mAccountManager = AccountManager.get(getActivity());
		Account[] accounts = mAccountManager
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		// Log.i("Mailik: ", accounts[1].name);
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
		Log.i("onConnectionSuspended: ", "started");
	}

	public void connect(int requestCode, int responseCode, Intent intent) {
		Log.i("connect: ", "started");
		if (requestCode == RC_SIGN_IN) {
			Log.i("connect: ", "1 if");
			if (responseCode != Activity.RESULT_OK) {
				mSignInClicked = false;
				Log.i("connect: ", "2.1 if");
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
				Log.i("connect: ", "2.2 if");
			}
		}
	}

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		Log.i("resolveSignInError: ", "started");
		if (mConnectionResult.hasResolution()) {
			Log.i("resolveSignInError: ", "if");
			try {
				mIntentInProgress = true;
				getActivity().startIntentSenderForResult(
						mSignInIntent.getIntentSender(), RC_SIGN_IN, null, 0,
						0, 0);
				Log.i("resolveSignInError: ", "try");
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
				Log.i("resolveSignInError: ", "catch");
			}
		}
	}

	@Override
	public void onClick(View view) {
		Log.i("onClick: ", "started");

		if (((LoginActivity) getActivity()).isNetworkAvailable()) {
			if (!mGoogleApiClient.isConnecting()) {
				switch (view.getId()) {
				case R.id.sign_in_button:
					mSignInClicked = true;
					resolveSignInError();
					Log.i("onClick: ", "sign in clicked");
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
}
