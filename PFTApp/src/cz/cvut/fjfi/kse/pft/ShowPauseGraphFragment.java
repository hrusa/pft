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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.ValueDependentColor;
import com.orm.SugarRecord;

import cz.cvut.fjfi.kse.pft.db.Exercise;
import cz.cvut.fjfi.kse.pft.db.ExerciseUnit;
import cz.cvut.fjfi.kse.pft.db.Serie;
import cz.cvut.fjfi.kse.pft.db.Trainee;

/**
 * @author Petr Hruška
 * 
 */
@SuppressLint("SimpleDateFormat")
public class ShowPauseGraphFragment extends Fragment {
	View view;
	Bundle args = new Bundle();
	List<ExerciseUnit> exerciseU;
	List<Serie> series;
	Trainee trainee;
	List<Integer> pauses = new ArrayList<Integer>();
	long diff;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// ExerciseUnit exercise;
	/**
	 * 
	 */
	public ShowPauseGraphFragment() {
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
		view = inflater.inflate(R.layout.fragment_showpausegraph, null);
		getActivity().getActionBar().setTitle("Statistics");
		args = getArguments();
		paintGraph(args);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().finish();
	}

	private void paintGraph(Bundle args) {
		exerciseU = SugarRecord.find(ExerciseUnit.class,
				"exercise = ? and done = ?", "" + args.getLong("exercise"),
				"true");

		for (int i = 0; i < exerciseU.size(); i++) {
			// exercise = exerciseU.get(i);
			series = SugarRecord.find(Serie.class, "exerciseunit = ?", ""
					+ exerciseU.get(i).getId());
			for (int j = 1; j < series.size(); j++) {
				try {
					Log.i("ShowPauseGraph", "Serie s indexe j=" + j
							+ " obsahuje: " + series.get(j).getStart());
					diff = dateFormat.parse(series.get(j).getStart()).getTime()
							- dateFormat.parse(series.get(j - 1).getFinish())
									.getTime();
					Log.i("ShowPauseGraph", "Toto vkládám do pause listu: "
							+ (int) (diff / 1000 % 60));
					pauses.add((int) (diff / 1000 % 60));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		GraphViewData[] graphData = new GraphViewData[pauses.size()];

		for (int i = 0; i < pauses.size(); i++) {
			graphData[i] = new GraphViewData(i, pauses.get(i).intValue());
		}

		Exercise ex = SugarRecord.findById(Exercise.class,
				args.getLong("exercise"));
		trainee = SugarRecord.findById(Trainee.class, args.getLong("trainee"));
		GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
		seriesStyle.setValueDependentColor(new ValueDependentColor() {
			@Override
			public int get(GraphViewDataInterface data) {
				// the higher the more red
				Interval interval = getInterval(trainee.getGoal());
				if (data.getY() < interval.min) {
					return Color.rgb(255, 0, 0);
				}

				if (data.getY() >= interval.min && data.getY() <= interval.max) {
					return Color.rgb(0, 255, 0);
				} else
					return Color.rgb(0, 0, 255);
			}
		});
		GraphViewSeries pauseSeries = new GraphViewSeries("Pauses",
				seriesStyle, graphData);
		GraphView graphView = new BarGraphView(getActivity(), ex.getName()
				+ " pause progress");
		graphView.addSeries(pauseSeries);
		graphView.setManualYAxisBounds(20, 0);

		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.pause_linearLayout);
		layout.addView(graphView);
	}

	private class Interval {
		int min;
		int max;

		public Interval(int min, int max) {
			this.min = min;
			this.max = max;
		}
	}

	private Interval getInterval(int goal) {
		switch (goal) {
		case 1:
			return new Interval(180, 300);
		case 2:
			return new Interval(120, 180);
		case 3:
			return new Interval(30, 60);
		default:
			return new Interval(60, 120);
		}
	}
}
