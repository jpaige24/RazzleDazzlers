package razzleDazzlers.mycafemac;

import razzleDazzlers.ratecafemac.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class DishActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        String dishName = getIntent().getStringExtra("dishName");
        String dishDescription = getIntent().getStringExtra("dishDescription");
        int rating = getIntent().getIntExtra("dishRating", 0);
        
        RatingBar ratingBar = (RatingBar) findViewById(R.id.DishInfo_ratingBar);
        ratingBar.setNumStars(rating);
        
        TextView dishNameView = (TextView) findViewById(R.id.DishInfo_name);
        dishNameView.setText(dishName);
        
        TextView dishDescriptionView = (TextView) findViewById(R.id.DishInfo_description);
        dishDescriptionView.setText(dishDescription);
	}
}
