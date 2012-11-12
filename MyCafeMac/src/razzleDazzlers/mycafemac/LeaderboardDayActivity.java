package razzleDazzlers.mycafemac;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class LeaderboardDayActivity extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboardday);
        
        ArrayList<String> days = new ArrayList<String>();
        ArrayList<Float> ratings = new ArrayList<Float>();
        
        TextView BD1= (TextView) findViewById(R.id.BestDay1);
        BD1.setText(days.get(0));
		RatingBar bar1 = (RatingBar) findViewById(R.id.BestDayRating1);
		bar1.setRating((float) ratings.get(0));
		bar1.setFocusable(false);
		bar1.setFocusableInTouchMode(false);
		
		TextView BD2= (TextView) findViewById(R.id.BestDay2);
        BD2.setText(days.get(1));
		RatingBar bar2 = (RatingBar) findViewById(R.id.BestDayRating2);
		bar2.setRating((float) ratings.get(1));
		bar2.setFocusable(false);
		bar2.setFocusableInTouchMode(false);
		
		TextView BD3= (TextView) findViewById(R.id.BestDay3);
        BD3.setText(days.get(2));
		RatingBar bar3 = (RatingBar) findViewById(R.id.BestDayRating3);
		bar3.setRating((float) ratings.get(2));
		bar3.setFocusable(false);
		bar3.setFocusableInTouchMode(false);
		
		TextView BD4= (TextView) findViewById(R.id.BestDay4);
        BD4.setText(days.get(3));
		RatingBar bar4 = (RatingBar) findViewById(R.id.BestDayRating4);
		bar4.setRating((float) ratings.get(3));
		bar4.setFocusable(false);
		bar4.setFocusableInTouchMode(false);
		
		TextView BD5= (TextView) findViewById(R.id.BestDay5);
        BD5.setText(days.get(4));
		RatingBar bar5 = (RatingBar) findViewById(R.id.BestDayRating5);
		bar5.setRating((float) ratings.get(4));
		bar5.setFocusable(false);
		bar5.setFocusableInTouchMode(false);
		
		TextView WD1= (TextView) findViewById(R.id.WorstDay1);
        WD1.setText(days.get(5));
		RatingBar bar6 = (RatingBar) findViewById(R.id.WorstDayRating1);
		bar6.setRating((float) ratings.get(5));
		bar6.setFocusable(false);
		bar6.setFocusableInTouchMode(false);
		
		TextView WD2= (TextView) findViewById(R.id.WorstDay2);
        WD2.setText(days.get(6));
		RatingBar bar7 = (RatingBar) findViewById(R.id.WorstDayRating2);
		bar7.setRating((float) ratings.get(6));
		bar7.setFocusable(false);
		bar7.setFocusableInTouchMode(false);
		
		TextView WD3= (TextView) findViewById(R.id.WorstDay3);
        WD3.setText(days.get(7));
		RatingBar bar8 = (RatingBar) findViewById(R.id.WorstDayRating3);
		bar8.setRating((float) ratings.get(7));
		bar8.setFocusable(false);
		bar8.setFocusableInTouchMode(false);
		
		TextView WD4= (TextView) findViewById(R.id.WorstDay4);
        WD4.setText(days.get(8));
		RatingBar bar9 = (RatingBar) findViewById(R.id.WorstDayRating4);
		bar9.setRating((float) ratings.get(8));
		bar9.setFocusable(false);
		bar9.setFocusableInTouchMode(false);
		
		TextView WD5= (TextView) findViewById(R.id.WorstDay1);
        WD5.setText(days.get(9));
		RatingBar bar10 = (RatingBar) findViewById(R.id.WorstDayRating5);
		bar10.setRating((float) ratings.get(9));
		bar10.setFocusable(false);
		bar10.setFocusableInTouchMode(false);        
        
    }
    
}