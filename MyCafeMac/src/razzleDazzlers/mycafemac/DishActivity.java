package razzleDazzlers.mycafemac;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.Server;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DishActivity extends Activity {
	
	String name;
	String date;
	String device;
	float r;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        
        date = getIntent().getStringExtra("date");
        device = getIntent().getStringExtra("device");
        
        String dishName = getIntent().getStringExtra("dishName");
        String dishDescription = getIntent().getStringExtra("dishDescription");
        float rating = getIntent().getFloatExtra("dishRating", 0);
        float ratingblue = getIntent().getFloatExtra("avgRating", 0);
        RatingBar ratingBarblue = (RatingBar) findViewById(R.id.DishInfo_ratingBarblue);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.DishInfo_ratingBar);
        
        if(rating < 1){
        	ratingBarblue.setRating(ratingblue);
        	ratingBar.setVisibility(View.GONE);
        }else{
            ratingBar.setRating(rating);
            ratingBarblue.setVisibility(View.GONE);
        }
        
        TextView dishNameView = (TextView) findViewById(R.id.DishInfo_name);
        dishNameView.setText(dishName);
        
        TextView dishDescriptionView = (TextView) findViewById(R.id.DishInfo_description);
        dishDescriptionView.setText(dishDescription);
        
        ratingBarblue.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		    	int action = event.getAction() & MotionEvent.ACTION_MASK;
		    	if (action == MotionEvent.ACTION_DOWN){
		    		float x = event.getX();
		    		float y = event.getY();
		    		System.out.println("****"+x+"****"+y);
		    		float star = (float) 0.0;
		    		if(x > 4 && x < 56)star = (float) 1.0;
		    		if(x > 69 && x < 122)star = (float) 2.0;
		    		if(x > 134 && x < 188)star = (float) 3.0;
		    		if(x > 199 && x < 253)star = (float) 4.0;
		    		if(x > 264 && x < 318)star = (float) 5.0;
		    		if(star > 0){
			    		v.setVisibility(View.GONE);
		    			ratingBar.setVisibility(View.VISIBLE);
		    			ratingBar.setRating(star);
			    		//System.out.println(((RatingBar) v).getRating());
			    		TextView text = (TextView) ((View) v.getParent()).findViewById(R.id.DishInfo_name);
			    		//System.out.println(text.getText().toString());
			    		name = text.getText().toString();
			    		r = ratingBar.getRating();
			    		Thread t = new Thread(){
			    			public void run(){
			    				submitRating(name, device, r, date, DishActivity.this);
			    			}
			    		};
			    		t.start();
		    		}
		    	}
	    		return false;
		    }
		});
        
        ratingBar.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		    	int action = event.getAction() & MotionEvent.ACTION_MASK;
		    	if (action == MotionEvent.ACTION_DOWN){
		    		float x = event.getX();
		    		float y = event.getY();
		    		System.out.println("****"+x+"****"+y);
		    		float star = (float) 0.0;
		    		if(x > 4 && x < 56)star = (float) 1.0;
		    		if(x > 69 && x < 122)star = (float) 2.0;
		    		if(x > 134 && x < 188)star = (float) 3.0;
		    		if(x > 199 && x < 253)star = (float) 4.0;
		    		if(x > 264 && x < 318)star = (float) 5.0;
		    		if(star > 0){
		    			((RatingBar) v).setRating(star);
			    		//System.out.println(((RatingBar) v).getRating());
			    		TextView text = (TextView) ((View) v.getParent()).findViewById(R.id.DishInfo_name);
			    		//System.out.println(text.getText().toString());
			    		name = text.getText().toString();
			    		r = ((RatingBar) v).getRating();
			    		Thread t = new Thread(){
			    			public void run(){
			    				submitRating(name, device, r, date, DishActivity.this);
			    			}
			    		};
			    		t.start();
		    		}
		    	}
	    		return false;
		    }
		});
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
	
	 @Override
	 protected void onPause(){
		 super.onPause();
		 Toast.makeText(this, "Refreshing CafeMac ratings...", Toast.LENGTH_SHORT).show();
	 }
}
