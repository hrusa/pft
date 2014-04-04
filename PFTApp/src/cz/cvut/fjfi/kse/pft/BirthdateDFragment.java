/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * @author Petr Hruška
 *
 */
public class BirthdateDFragment extends DialogFragment {
	Context mContext;
	int mYear, mMonth, mDay;
	/**
	 * 
	 */
	public BirthdateDFragment() {
		// TODO Auto-generated constructor stub
		mContext = getActivity();
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(getActivity(),
		        new DatePickerDialog.OnDateSetListener() {
		 
		            @Override
		            public void onDateSet(DatePicker view, int year,
		                    int monthOfYear, int dayOfMonth) {
		                Toast.makeText(getActivity(), dayOfMonth + "-"
		                        + (monthOfYear + 1) + "-" + year, Toast.LENGTH_LONG).show();
		 
		            }
		        }, mYear, mMonth, mDay);
		dpd.setTitle(R.string.title_fragmentd_birthdate);
		dpd.show();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle(R.string.title_fragmentd_birthdate);
		View view = inflater.inflate(R.layout.fragmentd_birthdate, null);
		return view;
	}*/
}
