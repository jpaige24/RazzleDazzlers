package razzleDazzlers.util;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DishArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> names;
	private final ArrayList<String> des;
	private final String device;
 
	public DishArrayAdapter(Context context, ArrayList<String> names, ArrayList<String> des, String tmDevice) {
		super(context, R.layout.activity_menuall, names);
		this.context = context;
		this.names = names;
		this.des = des;
		this.device = tmDevice;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_menuall, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.name);
		name.setText(names.get(position));
		RatingBar bar = (RatingBar) rowView.findViewById(R.id.rating);
		bar.setRating((float) 3.0);
		bar.setFocusable(false);
		bar.setFocusableInTouchMode(false);
		bar.setOnRatingBarChangeListener(new RatingBarListener(name.getText().toString(), device));
		TextView description = (TextView) rowView.findViewById(R.id.description);
		description.setText(des.get(position));
 
		return rowView;
	}
	
	private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{
		
		public String dishName = "";
		public String deviceID = "";
		
		public RatingBarListener(String dishName, String device){
			this.dishName = dishName;
			this.deviceID = device;
		}
		  
        public void onRatingChanged(RatingBar ratingBar, float rating,  
                boolean fromUser) {
        	
        	Time today = new Time(Time.getCurrentTimezone());
        	today.setToNow();
        	String date = Integer.toString(today.month) + Integer.toString(today.monthDay) + Integer.toString(today.year);
        	
        	Server serv = new Server();
        	if(serv.check(deviceID, date, dishName)){
        		serv.update(deviceID, date, dishName, rating);
        	}else{
        		serv.insert(deviceID, date, dishName, rating);
        	}
            System.out.println("rating--->" + rating);
            ratingBar.setRating(rating);
        }  
    }
	
}
