package razzleDazzlers.mycafemac;

import java.util.ArrayList;

import razzleDazzlers.ratecafemac.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class LeaderboardDishActivity extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboarddish);
        
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Float> ratings = new ArrayList<Float>();
        
        TextView BD1= (TextView) findViewById(R.id.BestDish1);
        BD1.setText(names.get(0));
		RatingBar bar1 = (RatingBar) findViewById(R.id.BestDishRating1);
		bar1.setRating((float) ratings.get(0));
		bar1.setFocusable(false);
		bar1.setFocusableInTouchMode(false);
		
        TextView BD2= (TextView) findViewById(R.id.BestDish2);
        BD2.setText(names.get(1));
        RatingBar bar2 = (RatingBar) findViewById(R.id.BestDishRating2);
		bar2.setRating((float) ratings.get(1));
		bar2.setFocusable(false);
		bar2.setFocusableInTouchMode(false);
        
        TextView BD3= (TextView) findViewById(R.id.BestDish3);
        BD3.setText(names.get(2));
		RatingBar bar3 = (RatingBar) findViewById(R.id.BestDishRating3);
		bar3.setRating((float) ratings.get(2));
		bar3.setFocusable(false);
		bar3.setFocusableInTouchMode(false);
		
        TextView BD4= (TextView) findViewById(R.id.BestDish4);
        BD4.setText(names.get(3));
		RatingBar bar4 = (RatingBar) findViewById(R.id.BestDishRating4);
		bar4.setRating((float) ratings.get(3));
		bar4.setFocusable(false);
		bar4.setFocusableInTouchMode(false);
        
        TextView BD5= (TextView) findViewById(R.id.BestDish5);
        BD5.setText(names.get(4));
		RatingBar bar5 = (RatingBar) findViewById(R.id.BestDishRating5);
		bar5.setRating((float) ratings.get(4));
		bar5.setFocusable(false);
		bar5.setFocusableInTouchMode(false);
		
        TextView WD1= (TextView) findViewById(R.id.WorstDish1);
        WD1.setText(names.get(5));
        RatingBar bar6 = (RatingBar) findViewById(R.id.WorstDishRating1);
		bar6.setRating((float) ratings.get(5));
		bar6.setFocusable(false);
		bar6.setFocusableInTouchMode(false);
		
        TextView WD2= (TextView) findViewById(R.id.WorstDish2);
        WD2.setText(names.get(6));
        RatingBar bar7 = (RatingBar) findViewById(R.id.WorstDishRating2);
		bar7.setRating((float) ratings.get(6));
		bar7.setFocusable(false);
		bar7.setFocusableInTouchMode(false);
		
        TextView WD3= (TextView) findViewById(R.id.WorstDish3);
        WD3.setText(names.get(7));
        RatingBar bar8 = (RatingBar) findViewById(R.id.WorstDishRating3);
		bar8.setRating((float) ratings.get(7));
		bar8.setFocusable(false);
		bar8.setFocusableInTouchMode(false);
		
        TextView WD4= (TextView) findViewById(R.id.WorstDish4);
        WD4.setText(names.get(8));
        RatingBar bar9 = (RatingBar) findViewById(R.id.WorstDishRating4);
		bar9.setRating((float) ratings.get(8));
		bar9.setFocusable(false);
		bar9.setFocusableInTouchMode(false);
		
        TextView WD5= (TextView) findViewById(R.id.WorstDish5);
        WD5.setText(names.get(9));
        RatingBar bar10 = (RatingBar) findViewById(R.id.WorstDishRating5);
		bar10.setRating((float) ratings.get(9));
		bar10.setFocusable(false);
		bar10.setFocusableInTouchMode(false);
        
    }
    
    
    
}