package razzleDazzlers.mycafemac;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.Server;
import android.app.Activity;
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
        
        RatingBar ratingBar = (RatingBar) findViewById(R.id.DishInfo_ratingBar);
        ratingBar.setRating(rating);
        
        TextView dishNameView = (TextView) findViewById(R.id.DishInfo_name);
        dishNameView.setText(dishName);
        
        TextView dishDescriptionView = (TextView) findViewById(R.id.DishInfo_description);
        dishDescriptionView.setText(dishDescription);
        
        ratingBar.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		    	int action = event.getAction() & MotionEvent.ACTION_MASK;
		    	if (action == MotionEvent.ACTION_DOWN){
		    		float x = event.getX();
		    		float y = event.getY();
		    		System.out.println("****"+x+"****"+y);
		    		float star = (float) 0.0;
		    		if(x > 8 && x < 63)star = (float) 1.0;
		    		if(x > 83 && x < 140)star = (float) 2.0;
		    		if(x > 158 && x < 212)star = (float) 3.0;
		    		if(x > 232 && x < 289)star = (float) 4.0;
		    		if(x > 308 && x < 364)star = (float) 5.0;
		    		if(x > 8 && x < 364){
		    			((RatingBar) v).setRating(star);
			    		//System.out.println(((RatingBar) v).getRating());
			    		TextView text = (TextView) ((View) v.getParent()).findViewById(R.id.DishInfo_name);
			    		//System.out.println(text.getText().toString());
			    		name = text.getText().toString();
			    		r = ((RatingBar) v).getRating();
			    		Thread t = new Thread(){
			    			public void run(){
			    				submitRating(name, device, r, date);
			    			}
			    		};
			    		t.start();
		    		}
		    	}
	    		return false;
		    }
		});
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
	
	 @Override
	 protected void onPause(){
		 super.onPause();
		 Toast.makeText(this, "Refreshing CafeMac ratings...", Toast.LENGTH_SHORT).show();
	 }
}
