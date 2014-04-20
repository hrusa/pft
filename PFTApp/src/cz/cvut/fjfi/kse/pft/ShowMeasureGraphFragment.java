/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

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

/**
 * @author Petr Hruška
 *
 */
public class ShowMeasureGraphFragment extends Fragment{
	View view;
	/**
	 * 
	 */
	public ShowMeasureGraphFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_showmeasuregraph, null);
		
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		    new GraphViewData(1, 2.0d)
		    , new GraphViewData(2, 1.5d)
		    , new GraphViewData(3, 2.5d)
		    , new GraphViewData(4, 1.0d)
		});
		 
		GraphView graphView = new LineGraphView(
		    getActivity() // context
		    , "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries); // data
		
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.measure_linearLayout);
		layout.addView(graphView);
		return view;
	}
}
