package razzleDazzlers.util;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DishArrayAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final ArrayList<String> names;
	private final ArrayList<String> des;
	private final ArrayList userRating;
	private final ArrayList avg;
	private final String device;
	private final String date;
	private String dishName;
	private float r;
 
	public DishArrayAdapter(Context context, ArrayList<String> names, ArrayList<String> des, String tmDevice, String date,
			ArrayList userRating, ArrayList avg) {
		super(context, R.layout.activity_menuall, names);
		this.context = context;
		this.names = names;
		this.des = des;
		this.device = tmDevice;
		this.date = date;
		this.userRating = userRating;
		this.avg = avg;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_menuall, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.name);
		name.setText(names.get(position));
		RatingBar bar = (RatingBar) rowView.findViewById(R.id.rating);
		float temp = (Float) userRating.get(position);
		if(temp < 1){
			bar.setRating((Float) avg.get(position));
		}else{
			bar.setRating((Float) userRating.get(position));
		}
		bar.setTag(position);
		bar.setFocusable(false);
		bar.setFocusableInTouchMode(false);
		bar.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		    	int action = event.getAction() & MotionEvent.ACTION_MASK;
		    	if (action == MotionEvent.ACTION_DOWN){
		    		float x = event.getX();
		    		float y = event.getY();
		    		System.out.println("****"+x+"****"+y);
		    		float star = (float) 0.0;
		    		if(x > 12 && x < 75)star = (float) 1.0;
		    		if(x > 97 && x < 160)star = (float) 2.0;
		    		if(x > 185 && x < 249)star = (float) 3.0;
		    		if(x > 270 && x < 330)star = (float) 4.0;
		    		if(x > 360 && x < 406)star = (float) 5.0;
		    		if(x > 12 && x < 406){
		    			((RatingBar) v).setRating(star);
			    		//System.out.println(((RatingBar) v).getRating());
			    		TextView text = (TextView) ((View) v.getParent()).findViewById(R.id.name);
			    		//System.out.println(text.getText().toString());
			    		dishName = text.getText().toString();
			    		r = ((RatingBar) v).getRating();
			    		userRating.set((Integer) v.getTag(), r);
			    		Thread t = new Thread(){
			    			public void run(){
			    				submitRating(dishName, device, r, date);
			    			}
			    		};
			    		t.start();
		    		}
		    	}
	    		return false;
		    }
		});
		//bar.setOnRatingBarChangeListener(new RatingBarListener(name.getText().toString(), device));
		TextView description = (TextView) rowView.findViewById(R.id.description);
		description.setText(des.get(position));
		return rowView;
	}
	
	
	
	public static void submitRating(String dishName, String deviceID, float rating, String date){
    	
    	Server serv = new Server();
    	if(serv.check(deviceID, date, dishName)){
    		serv.update(deviceID, date, dishName, rating);
    	}else{
    		serv.insert(deviceID, date, dishName, rating);
    	}
        System.out.println("rating--->" + rating);
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
