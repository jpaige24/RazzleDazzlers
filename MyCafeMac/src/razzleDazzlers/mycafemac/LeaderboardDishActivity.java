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
        
        ArrayList<String> bestDishes = getIntent().getStringArrayListExtra("bestDishNames");
        ArrayList<String> temp1 = getIntent().getStringArrayListExtra("bestDishRatings");
        ArrayList<Float> bestRatings = new ArrayList<Float>();
        for(int i=0;i<temp1.size();i++){
        	bestRatings.add(Float.parseFloat(temp1.get(i)));
        }
        ArrayList<String> worstDishes = getIntent().getStringArrayListExtra("worstDishNames");
        ArrayList<String> temp2 = getIntent().getStringArrayListExtra("worstDishRatings");
        ArrayList<Float> worstRatings = new ArrayList<Float>();
        for(int i=0;i<temp2.size();i++){
        	worstRatings.add(Float.parseFloat(temp2.get(i)));
        }
        
        TextView BD1= (TextView) findViewById(R.id.BestDish1);
        BD1.setText(bestDishes.get(0));
		RatingBar bar1 = (RatingBar) findViewById(R.id.BestDishRating1);
		bar1.setRating((float) bestRatings.get(0));
		bar1.setFocusable(false);
		bar1.setFocusableInTouchMode(false);
		
        TextView BD2= (TextView) findViewById(R.id.BestDish2);
        BD2.setText(bestDishes.get(1));
        RatingBar bar2 = (RatingBar) findViewById(R.id.BestDishRating2);
		bar2.setRating((float) bestRatings.get(1));
		bar2.setFocusable(false);
		bar2.setFocusableInTouchMode(false);
        
        TextView BD3= (TextView) findViewById(R.id.BestDish3);
        BD3.setText(bestDishes.get(2));
		RatingBar bar3 = (RatingBar) findViewById(R.id.BestDishRating3);
		bar3.setRating((float) bestRatings.get(2));
		bar3.setFocusable(false);
		bar3.setFocusableInTouchMode(false);
		
        TextView BD4= (TextView) findViewById(R.id.BestDish4);
        BD4.setText(bestDishes.get(3));
		RatingBar bar4 = (RatingBar) findViewById(R.id.BestDishRating4);
		bar4.setRating((float) bestRatings.get(3));
		bar4.setFocusable(false);
		bar4.setFocusableInTouchMode(false);
        
        TextView BD5= (TextView) findViewById(R.id.BestDish5);
        BD5.setText(bestDishes.get(4));
		RatingBar bar5 = (RatingBar) findViewById(R.id.BestDishRating5);
		bar5.setRating((float) bestRatings.get(4));
		bar5.setFocusable(false);
		bar5.setFocusableInTouchMode(false);
		
        TextView WD1= (TextView) findViewById(R.id.WorstDish1);
        WD1.setText(worstDishes.get(0));
        RatingBar bar6 = (RatingBar) findViewById(R.id.WorstDishRating1);
		bar6.setRating((float) worstRatings.get(0));
		bar6.setFocusable(false);
		bar6.setFocusableInTouchMode(false);
		
        TextView WD2= (TextView) findViewById(R.id.WorstDish2);
        WD2.setText(worstDishes.get(1));
        RatingBar bar7 = (RatingBar) findViewById(R.id.WorstDishRating2);
		bar7.setRating((float) worstRatings.get(1));
		bar7.setFocusable(false);
		bar7.setFocusableInTouchMode(false);
		
        TextView WD3= (TextView) findViewById(R.id.WorstDish3);
        WD3.setText(worstDishes.get(2));
        RatingBar bar8 = (RatingBar) findViewById(R.id.WorstDishRating3);
		bar8.setRating((float) worstRatings.get(2));
		bar8.setFocusable(false);
		bar8.setFocusableInTouchMode(false);
		
        TextView WD4= (TextView) findViewById(R.id.WorstDish4);
        WD4.setText(worstDishes.get(3));
        RatingBar bar9 = (RatingBar) findViewById(R.id.WorstDishRating4);
		bar9.setRating((float) worstRatings.get(3));
		bar9.setFocusable(false);
		bar9.setFocusableInTouchMode(false);
		
        TextView WD5= (TextView) findViewById(R.id.WorstDish5);
        WD5.setText(worstDishes.get(4));
        RatingBar bar10 = (RatingBar) findViewById(R.id.WorstDishRating5);
		bar10.setRating((float) worstRatings.get(4));
		bar10.setFocusable(false);
		bar10.setFocusableInTouchMode(false);
        
    }
     
}