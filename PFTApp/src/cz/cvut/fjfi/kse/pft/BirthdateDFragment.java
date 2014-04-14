/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.Calendar;
import java.util.Date;

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
	int mYear, mMonth, mDay, gender;
	Bundle args;
	DatePickerDialog dpd;
	//DatePicker dp = dpd.getDatePicker();
	//nechápu proč se onDataSet spouští 2x, pomocí této proměnné je ošetřeno dvojité provedení kódu
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
		args = this.getArguments();
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		dpd = new DatePickerDialog(getActivity(),
		        new DatePickerDialog.OnDateSetListener() {
		 
		            @Override
		            public void onDateSet(DatePicker view, int year,
		                    int monthOfYear, int dayOfMonth) {
		            	//ošetřující podmínka dvojitého spuštění onDataSet
		            	if(wantToStartDialog) {
		            		args.putString("birth", dpd.getDatePicker().getYear()+"-"+dpd.getDatePicker().getMonth()+"-"+dpd.getDatePicker().getDayOfMonth());
		            		//((LoginActivity) getActivity()).showBasicInfoDialog();
		            		BasicInfoDFragment dialog = new BasicInfoDFragment();
		            	    dialog.setArguments(args);
		            		dialog.show(getFragmentManager(), "BasicInfoD");
		            		wantToStartDialog = false;
		            	}
		            }
		        }, mYear, mMonth, mDay);
		dpd.setTitle(R.string.title_fragmentd_birthdate);
		long date = new Date().getTime();
		Log.i("Datum", ""+date);
		dpd.getDatePicker().setMaxDate(date);
		dpd.setCancelable(false);
		dpd.show();
	}
}
