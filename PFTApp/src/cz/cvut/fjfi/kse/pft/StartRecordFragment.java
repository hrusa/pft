/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @author Petr Hruška
 *
 */
public class StartRecordFragment extends Fragment{
	View view;
	/**
	 * 
	 */
	public StartRecordFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_startrecord, null);
		Toast.makeText(getActivity(), "frčíme", Toast.LENGTH_SHORT).show();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
