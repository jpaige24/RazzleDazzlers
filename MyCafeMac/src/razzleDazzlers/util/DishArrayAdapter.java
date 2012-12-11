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
	ViewGroup parentGroup;
 
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
		parentGroup = parent;
		View rowView = inflater.inflate(R.layout.activity_menuall, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.name);
		name.setText(names.get(position));
		final RatingBar bar = (RatingBar) rowView.findViewById(R.id.rating);
		RatingBar barblue = (RatingBar) rowView.findViewById(R.id.ratingblue);
		float temp = (Float) userRating.get(position);
		if(temp < 1){
			//bar.setProgressDrawable(rowView.getResources().getDrawable(R.drawable.custom_rating_blue));
			barblue.setRating((Float) avg.get(position));
			bar.setVisibility(rowView.GONE);
		}else{
			//bar.setProgressDrawable(rowView.getResources().getDrawable(R.drawable.custom_rating_bar));
			bar.setRating((Float) userRating.get(position));
			barblue.setVisibility(rowView.GONE);
		}
		RatingBar ratedBar;
//		if (bar.getVisibility() == rowView.GONE){
//			ratedBar = barblue;
//		}else{
//			ratedBar = bar;
//		}
		barblue.setTag(position);
		barblue.setFocusable(false);
		barblue.setFocusableInTouchMode(false);
		barblue.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		    	int action = event.getAction() & MotionEvent.ACTION_MASK;
		    	if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP){
		    		float x = event.getX();
		    		float y = event.getY();
		    		System.out.println("****"+x+"****"+y);
		    		float star = (float) 0.0;
		    		if(x < 56)star = (float) 1.0;
		    		if(x > 69 && x < 122)star = (float) 2.0;
		    		if(x > 134 && x < 188)star = (float) 3.0;
		    		if(x > 199 && x < 253)star = (float) 4.0;
		    		if(x > 264)star = (float) 5.0;
		    		if(star > 0){
			    		v.setVisibility(View.GONE);
		    			bar.setVisibility(View.VISIBLE);
		    			bar.setRating(star);
			    		//System.out.println(((RatingBar) v).getRating());
			    		TextView text = (TextView) ((View) v.getParent()).findViewById(R.id.name);
			    		//System.out.println(text.getText().toString());
			    		dishName = text.getText().toString();
			    		r = bar.getRating();
			    		userRating.set((Integer) v.getTag(), r);
			    		Thread t = new Thread(){
			    			public void run(){
			    				submitRating(dishName, device, r, date, context);
			    			}
			    		};
			    		t.start();
		    		}
		    	}
	    		return false;
		    }
		});
		barblue.setOnRatingBarChangeListener(new RatingBarBlueListener(name.getText().toString(), device, bar));
		
		bar.setTag(position);
		bar.setFocusable(false);
		bar.setFocusableInTouchMode(false);
		bar.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		    	int action = event.getAction() & MotionEvent.ACTION_MASK;
		    	if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP){
		    		float x = event.getX();
		    		float y = event.getY();
		    		System.out.println("****"+x+"****"+y);
		    		float star = (float) 0.0;
		    		if(x < 56)star = (float) 1.0;
		    		if(x > 69 && x < 122)star = (float) 2.0;
		    		if(x > 134 && x < 188)star = (float) 3.0;
		    		if(x > 199 && x < 253)star = (float) 4.0;
		    		if(x > 264)star = (float) 5.0;
		    		if(star > 0){
		    			((RatingBar) v).setRating(star);
			    		//System.out.println(((RatingBar) v).getRating());
			    		TextView text = (TextView) ((View) v.getParent()).findViewById(R.id.name);
			    		//System.out.println(text.getText().toString());
			    		dishName = text.getText().toString();
			    		r = ((RatingBar) v).getRating();
			    		userRating.set((Integer) v.getTag(), r);
			    		Thread t = new Thread(){
			    			public void run(){
			    				submitRating(dishName, device, r, date, context);
			    			}
			    		};
			    		t.start();
		    		}
		    	}
	    		return false;
		    }
		});
		bar.setOnRatingBarChangeListener(new RatingBarListener(name.getText().toString(), device));
		TextView description = (TextView) rowView.findViewById(R.id.description);
		description.setText(des.get(position));
		description.setVisibility(View.GONE);
		return rowView;
	}
	
	
	
	public static void submitRating(String dishName, String deviceID, float rating, String date, Context context){
    	
    	Server serv = new Server(context);
    	if(serv.check(deviceID, date, dishName)){
    		serv.update(deviceID, date, dishName, rating);
    	}else{
    		serv.insert(deviceID, date, dishName, rating);
    	}
        System.out.println("rating--->" + rating);
	}
	
	private class RatingBarBlueListener implements RatingBar.OnRatingBarChangeListener{
		
		public String dishName = "";
		public String deviceID = "";
		private final RatingBar bar;
		
		public RatingBarBlueListener(String dishName, String device, RatingBar bar){
			this.dishName = dishName;
			this.deviceID = device;
			this.bar = bar;
		}
		  
        public void onRatingChanged(RatingBar ratingBar, float rating,  
                boolean fromUser) {
        	
        	Time today = new Time(Time.getCurrentTimezone());
        	today.setToNow();
        	final String date = Integer.toString(today.month) + Integer.toString(today.monthDay) + Integer.toString(today.year);
        	
        	ratingBar.setVisibility(View.GONE);
        	bar.setVisibility(View.VISIBLE);
        	
            bar.setRating(rating);
    		Thread t = new Thread(){
    			public void run(){
    				DishArrayAdapter.submitRating(dishName, device, r, date, context);
    			}
    		};
    		t.start();
        }  
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
        	final String date = Integer.toString(today.month) + Integer.toString(today.monthDay) + Integer.toString(today.year);
        	
            ratingBar.setRating(rating);
    		Thread t = new Thread(){
    			public void run(){
    				DishArrayAdapter.submitRating(dishName, device, r, date, context);
    			}
    		};
    		t.start();
        }  
    }
	
}
