/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.orm.SugarRecord;

import cz.cvut.fjfi.kse.pft.db.Attribute;
import cz.cvut.fjfi.kse.pft.db.Measure;

/**
 * @author Petr Hruška
 * 
 */
@SuppressLint("SimpleDateFormat")
public class ShowMeasureGraphFragment extends Fragment {
	View view;
	List<Measure> measures;
	Measure nMeasure, oMeasure;
	int size, axisX = 0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	long diff;
	String[] labelsH;

	/**
	 * 
	 */
	public ShowMeasureGraphFragment() {
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
		view = inflater.inflate(R.layout.fragment_showmeasuregraph, null);
		getActivity().getActionBar().setTitle("Statistics");
		Bundle args = new Bundle();
		args = getArguments();
		paintGraph(args);
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
	
	private void paintGraph(Bundle args) {
		Attribute attr = SugarRecord.findById(Attribute.class, args.getLong("attribute"));
		measures = SugarRecord.find(Measure.class, "attribute = ?",
				"" + args.getLong("attribute"));
		size = measures.size();
		GraphViewData[] graphData = new GraphViewData[size];
		labelsH = new String[size];

		for (int i = 0; i < size; i++) {
			nMeasure = measures.get(i);
			if (i != 0) {
				try {
					diff = dateFormat.parse(nMeasure.getDate()).getTime()
							- dateFormat.parse(oMeasure.getDate()).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				axisX += (int) (diff / (24 * 60 * 60 * 1000));
			}
			graphData[i] = new GraphViewData(axisX, nMeasure.getValue());
			labelsH[i] = nMeasure.getDate();
			oMeasure = nMeasure;
		}

		GraphViewSeries measureSeries = new GraphViewSeries(graphData);
		GraphView graphView = new LineGraphView(getActivity(), attr.getName()+" progress");
		graphView.addSeries(measureSeries);
		graphView.setHorizontalLabels(labelsH);
		
		
		
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.measure_linearLayout);
		layout.addView(graphView);
	}
}
