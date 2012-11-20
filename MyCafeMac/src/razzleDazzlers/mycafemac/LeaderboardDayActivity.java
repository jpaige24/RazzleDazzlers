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
        
        ArrayList<String> bestDays = getIntent().getStringArrayListExtra("bestDayDates");
        ArrayList<String> temp1 = getIntent().getStringArrayListExtra("bestDayRatings");
        ArrayList<Float> bestRatings = new ArrayList<Float>();
        for(int i=0;i<temp1.size();i++){
        	bestRatings.add(Float.parseFloat(temp1.get(i)));
        }
        ArrayList<String> worstDays = getIntent().getStringArrayListExtra("worstDayDates");
        ArrayList<String> temp2 = getIntent().getStringArrayListExtra("worstDayRatings");
        ArrayList<Float> worstRatings = new ArrayList<Float>();
        for(int i=0;i<temp2.size();i++){
        	worstRatings.add(Float.parseFloat(temp2.get(i)));
        }
        
        TextView BD1= (TextView) findViewById(R.id.BestDay1);
        BD1.setText(bestDays.get(0));
		RatingBar bar1 = (RatingBar) findViewById(R.id.BestDayRating1);
		bar1.setRating((float) bestRatings.get(0));
		bar1.setFocusable(false);
		bar1.setFocusableInTouchMode(false);
		
		TextView BD2= (TextView) findViewById(R.id.BestDay2);
        BD2.setText(bestDays.get(1));
		RatingBar bar2 = (RatingBar) findViewById(R.id.BestDayRating2);
		bar2.setRating((float) bestRatings.get(1));
		bar2.setFocusable(false);
		bar2.setFocusableInTouchMode(false);
		
		TextView BD3= (TextView) findViewById(R.id.BestDay3);
        BD3.setText(bestDays.get(2));
		RatingBar bar3 = (RatingBar) findViewById(R.id.BestDayRating3);
		bar3.setRating((float) bestRatings.get(2));
		bar3.setFocusable(false);
		bar3.setFocusableInTouchMode(false);
		
		TextView BD4= (TextView) findViewById(R.id.BestDay4);
        BD4.setText(bestDays.get(3));
		RatingBar bar4 = (RatingBar) findViewById(R.id.BestDayRating4);
		bar4.setRating((float) bestRatings.get(3));
		bar4.setFocusable(false);
		bar4.setFocusableInTouchMode(false);
		
		TextView BD5= (TextView) findViewById(R.id.BestDay5);
        BD5.setText(bestDays.get(4));
		RatingBar bar5 = (RatingBar) findViewById(R.id.BestDayRating5);
		bar5.setRating((float) bestRatings.get(4));
		bar5.setFocusable(false);
		bar5.setFocusableInTouchMode(false);
		
		TextView WD1= (TextView) findViewById(R.id.WorstDay1);
        WD1.setText(worstDays.get(0));
		RatingBar bar6 = (RatingBar) findViewById(R.id.WorstDayRating1);
		bar6.setRating((float) worstRatings.get(0));
		bar6.setFocusable(false);
		bar6.setFocusableInTouchMode(false);
		
		TextView WD2= (TextView) findViewById(R.id.WorstDay2);
        WD2.setText(worstDays.get(1));
		RatingBar bar7 = (RatingBar) findViewById(R.id.WorstDayRating2);
		bar7.setRating((float) worstRatings.get(1));
		bar7.setFocusable(false);
		bar7.setFocusableInTouchMode(false);
		
		TextView WD3= (TextView) findViewById(R.id.WorstDay3);
        WD3.setText(worstDays.get(2));
		RatingBar bar8 = (RatingBar) findViewById(R.id.WorstDayRating3);
		bar8.setRating((float) worstRatings.get(2));
		bar8.setFocusable(false);
		bar8.setFocusableInTouchMode(false);
		
		TextView WD4= (TextView) findViewById(R.id.WorstDay4);
        WD4.setText(worstDays.get(3));
		RatingBar bar9 = (RatingBar) findViewById(R.id.WorstDayRating4);
		bar9.setRating((float) worstRatings.get(3));
		bar9.setFocusable(false);
		bar9.setFocusableInTouchMode(false);
		
		TextView WD5= (TextView) findViewById(R.id.WorstDay5);
        WD5.setText(worstDays.get(4));
		RatingBar bar10 = (RatingBar) findViewById(R.id.WorstDayRating5);
		bar10.setRating((float) worstRatings.get(4));
		bar10.setFocusable(false);
		bar10.setFocusableInTouchMode(false);       
        
    }
    
}