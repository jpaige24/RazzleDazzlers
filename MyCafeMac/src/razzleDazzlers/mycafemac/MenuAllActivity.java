package razzleDazzlers.mycafemac;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.DishArrayAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuAllActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayList<String> allMenu = getIntent().getStringArrayListExtra("allMenu");
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> des = new ArrayList<String>();
		for(int i=0;i<allMenu.size();i++){
			if(i%2 == 0){
				names.add(allMenu.get(i));
			}else{
				des.add(allMenu.get(i));
			}
		}
		
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		String tmDevice = "" + tm.getDeviceId();
		
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.activity_menuheader, lv, false);
		lv.addHeaderView(header, null, false);
		setListAdapter(new DishArrayAdapter(this, names, des, tmDevice));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){

		//System.out.println("*****");
		//get selected items
		TextView textView = (TextView) v.findViewById(R.id.name);
		String text = textView.getText().toString();
		//System.out.println(text);
		//String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

	}
}
