/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

/**
 * @author Petr Hruška
 *
 */
public class BirthdateDFragment extends DialogFragment {
	int mYear, mMonth, mDay;
	//nechápu proč se onDataSet spouští 2x, pomocí této proměnné je ošetřeno dvojité provedení kódus
	boolean wantToStartDialog = true;	
	/**
	 * 
	 */
	public BirthdateDFragment() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("onCreate BD", "spuštěno");
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(getActivity(),
		        new DatePickerDialog.OnDateSetListener() {
		 
		            @Override
		            public void onDateSet(DatePicker view, int year,
		                    int monthOfYear, int dayOfMonth) {
		            	//ošetřující podmínka dvojitého spuštění onDataSet
		            	if(wantToStartDialog) {	
		            		((LoginActivity) getActivity()).showBasicInfoDialog();
		            		wantToStartDialog = false;
		            	}
		            }
		        }, mYear, mMonth, mDay);
		dpd.setTitle(R.string.title_fragmentd_birthdate);
		dpd.setCancelable(false);
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
