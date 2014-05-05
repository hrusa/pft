/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Petr Hruška
 * 
 */
public class PauseFragment extends DialogFragment {
	View view;
	TextView countdownText;
	Bundle args = new Bundle();
	private CountDownTimer countDownTimer;
	boolean helper = false;

	/**
	 * 
	 */
	public PauseFragment() {
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
		view = inflater.inflate(R.layout.fragment_pause, null);
		countdownText = (TextView) view.findViewById(R.id.countdown_textView);
		args = this.getArguments();
		setCancelable(false);
		getDialog().setTitle("Take a break");
		countDownTimer = new MyCountDownTimer(args.getInt("pause")*1000, 1000);
		countDownTimer.start();
		return view;
	}

	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			if(!helper) {
			countdownText.setText("Break is over!");
			countDownTimer = new MyCountDownTimer(3000, 1000);
			countDownTimer.start();
			helper = true;
			} else {
				Log.i("PauseFragment", "začlo druhé odpočítávání");
				dismiss();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if(!helper) {
			countdownText.setText(millisUntilFinished / 1000+"s");
			}
		}
	}
}
