/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Serie;

/**
 * @author Petr Hruška
 * 
 */
@SuppressLint("SimpleDateFormat")
public class ShowWeightGraphFragment extends Fragment {
	View view;
	Bundle args = new Bundle();
	List<ExerciseUnit> exerciseU;
	List<Serie> series;
	List<Integer> pauses = new ArrayList<Integer>();
	double helper, orm;
	int axisX;
	String oDate, nDate;
	String[] labelsH;
	long diff;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 */
	public ShowWeightGraphFragment() {
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
		view = inflater.inflate(R.layout.fragment_showweightgraph, null);
		args = getArguments();
		exerciseU = ExerciseUnit.find(ExerciseUnit.class,
				"exercise = ? and done = ?", "" + args.getLong("exercise"),
				"true");

		GraphViewData[] graphData = new GraphViewData[exerciseU.size()];
		labelsH = new String[exerciseU.size()];
		Log.i("ShowWeightGraph", "před forem");
		for (int i = 0; i < exerciseU.size(); i++) {
			// exercise = exerciseU.get(i);
			orm = 0;
			Log.i("ShowWeightGraph", "začátek for");
			series = Serie.find(Serie.class, "exerciseunit = ?", ""
					+ exerciseU.get(i).getId());
			for (int j = 0; j < series.size(); j++) {
				Log.i("ShowWeightGraph", "začátek for #2");
				helper = series.get(j).getWeight()
						/ (1.0278 - 0.0278 * series.get(j).getRepetition());
				if (helper > orm) {
					Log.i("ShowWeightGraph", "ORM inicializace: "+helper);
					orm = helper;
					nDate = series.get(j).getFinish();
				}

			}
			if (i != 0) {
				Log.i("ShowWeightGraph", "před try");
				try {
					Log.i("ShowWeightGraph", "výpočet diff");
					diff = dateFormat.parse(nDate).getTime()
							- dateFormat.parse(oDate).getTime();
					axisX += (int) (diff / (24 * 60 * 60 * 1000))+1;
					Log.i("ShowWeightGraph", "axisX: "+axisX);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.i("ShowWeightGraph", "inicializace graphData");
			Log.i("ShowWeightGraph", "axisX: "+axisX+" a orm: "+orm);
			graphData[i] = new GraphViewData(axisX, orm);
			Log.i("ShowWeightGraph", "inicializace osy");
			labelsH[i] = nDate;
			Log.i("ShowWeightGraph", "uložení starého data");
			oDate = nDate;
		}
		Log.i("ShowWeightGraph", "po všech forech");
		Exercise ex = Exercise.findById(Exercise.class,
				args.getLong("exercise"));
		GraphViewSeries weightSeries = new GraphViewSeries(graphData);
		GraphView graphView = new LineGraphView(getActivity(), ex.getName()
				+ " 1RM progress");
		graphView.addSeries(weightSeries);
		graphView.setHorizontalLabels(labelsH);

		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.weight_linearLayout);
		layout.addView(graphView);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().finish();
	}
}
