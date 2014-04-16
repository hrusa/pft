/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Petr Hruška
 * 
 */
public class MenuFragment extends Fragment {
	View view;
	Button startBtn, createBtn, exerciseBtn, statisticBtn, exitBtn;
	Bundle args = new Bundle();

	/**
	 * 
	 */
	public MenuFragment() {
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
		view = inflater.inflate(R.layout.fragment_menu, null);
		args = getArguments();
		startBtn = (Button) view.findViewById(R.id.start_button);
		createBtn = (Button) view.findViewById(R.id.create_button);
		exerciseBtn = (Button) view.findViewById(R.id.exercise_button);
		statisticBtn = (Button) view.findViewById(R.id.statistic_button);
		exitBtn = (Button) view.findViewById(R.id.exit_button);
		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				args.putBoolean("record", true);
				TrainingListFragment fragment = new TrainingListFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction().replace(R.id.container, fragment, "TrainingList").commit();
			}
		});
		createBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChooseTrainingDFragment dialog = new ChooseTrainingDFragment();
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), "ChooseTrainingD");
			}
		});
		exerciseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//vytvořit nový list fragment, který bude pouze zobrazovat cviky, po kliknutí na jednotlivý cvik je uživateli zobrazen popis
				ShowExerciseFragment fragment = new ShowExerciseFragment();
				fragment.setArguments(args);
				getFragmentManager().beginTransaction().replace(R.id.container, fragment, "ShowExercise").commit();
			}
		});
		statisticBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Statistiky je třeba ještě dodělat.", Toast.LENGTH_SHORT).show();
			}
		});
		exitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		return view;
	}
}
