package razzleDazzlers.mycafemac;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.DishArrayAdapter;
import razzleDazzlers.util.Server;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

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
		String date = getIntent().getStringExtra("date");
		float r = getIntent().getFloatExtra("r", (float)0.0);
		ArrayList userRating = (ArrayList) getIntent().getExtras().getSerializable("userRating");
		//System.out.println(avg);
		ArrayList avgRating = (ArrayList) getIntent().getExtras().getSerializable("avgRating");
		
		String tmDevice = getIntent().getStringExtra("device");
		
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.activity_menuheader, lv, false);
		
		RatingBar bar = (RatingBar) header.findViewById(R.id.head_rating);
		bar.setStepSize((float) 1.0);
		bar.setRating(r);
		
		lv.addHeaderView(header, null, false);
		//System.out.println("%%%%"+date);
		setListAdapter(new DishArrayAdapter(this, names, des, tmDevice, date, userRating, avgRating));
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
