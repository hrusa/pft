/**
 * 
 */
package cz.cvut.fjfi.kse.pft;

import java.util.List;

import com.orm.SugarRecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.cvut.fjfi.kse.pft.db.Attribute;

/**
 * @author Petr Hruška
 *
 */
public class MeasuresListFragment extends ListFragment{
	Bundle args = new Bundle();
	ArrayAdapter<Attribute> adapter;
	/**
	 * 
	 */
	public MeasuresListFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		args = this.getArguments();
		List<Attribute> attribute = SugarRecord.listAll(Attribute.class);
		adapter = new ArrayAdapter<Attribute>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, attribute);
		setListAdapter(adapter);
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Attribute attr = adapter.getItem(position);
		args.putLong("attribute", attr.getId());
		startActivity(new Intent(getActivity(), GraphActivity.class).putExtras(args));
	}
}
