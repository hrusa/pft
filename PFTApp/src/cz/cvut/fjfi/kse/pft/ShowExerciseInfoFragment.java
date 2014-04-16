/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cz.cvut.fjfi.kse.pft.db.Exercise;

/**
 * @author Petr Hruška
 *
 */
public class ShowExerciseInfoFragment extends Fragment{
	Bundle args = new Bundle();
	Exercise exercise;
	TextView eName, eDesc;
	Button videoBtn;
	View view;
	/**
	 * 
	 */
	public ShowExerciseInfoFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_showexerciseinfo, null);
		args = getArguments();
		exercise = Exercise.findById(Exercise.class, args.getLong("exercise"));
		eName = (TextView) view.findViewById(R.id.eName_textView);
		eDesc = (TextView) view.findViewById(R.id.eDesc_textView);
		videoBtn = (Button) view.findViewById(R.id.video_button);
		eName.setText(exercise.getName());
		eDesc.setText(exercise.getDescription());
		videoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(exercise.getVideo())));
			}
		});
		return view;
	}
}
