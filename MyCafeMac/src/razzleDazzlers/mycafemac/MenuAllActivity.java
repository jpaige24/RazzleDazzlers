package razzleDazzlers.mycafemac;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.DishArrayAdapter;
import razzleDazzlers.util.Server;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
	
	String tmDevice;
	String date;
	ArrayList<String> allMenu;
	ArrayList<String> names;
	ArrayList<String> des;
	int update = -1;
	String updateName = "";
	ArrayList userRating = new ArrayList();
	ArrayList avgRating = new ArrayList();
	RatingBar bar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        if (!isOnline()){
        	error(MenuAllActivity.this);
        }
        
		allMenu = getIntent().getStringArrayListExtra("allMenu");
		names = new ArrayList<String>();
		des = new ArrayList<String>();
		for(int i=0;i<allMenu.size();i++){
			if(i%2 == 0){
				names.add(allMenu.get(i));
			}else{
				des.add(allMenu.get(i));
			}
		}
		date = getIntent().getStringExtra("date");
		float r = getIntent().getFloatExtra("r", (float)0.0);
		
		Thread t = new Thread(){
			public void run(){
				Server serv = new Server(MenuAllActivity.this);
				for(int i=0;i<allMenu.size();i+=2){
					float temp = serv.getUserDishRating(allMenu.get(i), date, tmDevice);
					userRating.add(temp);
					if(temp < 1){
						avgRating.add(serv.getAvgDishRating(allMenu.get(i), date));
					}else{
						avgRating.add(serv.getAvgDishRating(allMenu.get(i), date));
					}
				}
			}
		};
		t.start();
		
		//userRating = (ArrayList) getIntent().getExtras().getSerializable("userRating");
		//System.out.println(avg);
		//avgRating = (ArrayList) getIntent().getExtras().getSerializable("avgRating");
		
		tmDevice = getIntent().getStringExtra("device");
		
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.activity_menuheader, lv, false);
		
		bar = (RatingBar) header.findViewById(R.id.head_rating);
		//bar.setStepSize((float) 1.0);
		bar.setRating(r);
		
		//Calendar c = Calendar.getInstance();
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentday = new SimpleDateFormat("MM/dd/yyy").format(new Date());
		//Date d = new Date();
		//CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());
		TextView head_day = (TextView) header.findViewById(R.id.header_date);
		head_day.setText(currentday);
		
		lv.addHeaderView(header, null, false);
		//System.out.println("%%%%"+date);
		setListAdapter(new DishArrayAdapter(this, names, des, tmDevice, date, userRating, avgRating));
	}
	
	@Override
	public void onResume(){
	    super.onResume();
	    
//	    ArrayList refreshUserRating = new ArrayList();
//	    ArrayList refreshAvgRating = new ArrayList();
//	    Server serv = new Server();
//		for(int i=0;i<allMenu.size();i+=2){
//			float temp = serv.getUserDishRating(allMenu.get(i), date, tmDevice);
//			refreshUserRating.add(temp);
//			if(temp < 1){
//				refreshAvgRating.add(serv.getAvgDishRating(allMenu.get(i), date));
//			}else{
//				refreshAvgRating.add((float) 0.0);
//			}
//		}
	    Server serv = new Server(MenuAllActivity.this);
	    float r = (float) serv.getDayRating(date);
	    bar.setRating(r);
	    float temp = serv.getUserDishRating(updateName, date, tmDevice);
	    if (temp > 0) userRating.set(update, temp);
	    //System.out.println("!!!!"+temp+"!!!!"+update);
	    setListAdapter(new DishArrayAdapter(this, names, des, tmDevice, date, userRating, avgRating));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		
		update = position-1;
		
		if (!isOnline()){
        	error(MenuAllActivity.this);
        }
		
		Intent dishIntent = new Intent(MenuAllActivity.this, DishActivity.class);
		//System.out.println("*****");
		//get selected items
		TextView name = (TextView) v.findViewById(R.id.name);
		String nameText = name.getText().toString();
		dishIntent.putExtra("dishName", nameText);
		
		updateName = nameText;
		
		TextView description = (TextView) v.findViewById(R.id.description);
		String desText = description.getText().toString();
		dishIntent.putExtra("dishDescription", desText);
		
		RatingBar rating = (RatingBar) v.findViewById(R.id.rating);
		Float ratingFloat = rating.getRating();
		dishIntent.putExtra("dishRating", ratingFloat);
		
		RatingBar ratingblue = (RatingBar) v.findViewById(R.id.ratingblue);
		//Float ratingblueFloat = ratingblue.getRating();
		Float ratingblueFloat = (Float) avgRating.get(update);
		dishIntent.putExtra("avgRating", ratingblueFloat);
		//System.out.println("%%%%%%%%" + ratingblueFloat);
		
		dishIntent.putExtra("device", tmDevice);
		dishIntent.putExtra("date", date);
		//System.out.println(text);
		//String selectedValue = (String) getListAdapter().getItem(position);
		//Toast.makeText(this, nameText, Toast.LENGTH_SHORT).show();
		MenuAllActivity.this.startActivity(dishIntent);
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public static void error(final Context context){
    	new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(context).setTitle("No Internet?").setCancelable(false)
                        .setMessage("Oops. WiFi not connected or server not responding. Please check back later.").setNeutralButton("Ok", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            	System.exit(0);
                            }
                        })
                        .create().show();
                Looper.loop();
            }
        }.start();
    }
	
	/*@Override
	 protected void onPause(){
		 super.onPause();
		 Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
	 }*/
}
