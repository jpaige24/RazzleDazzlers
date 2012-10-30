package razzleDazzlers.mycafemac;

import razzleDazzlers.ratecafemac.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class LeaderboardActivity extends TabActivity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        
        TabHost tabHostLeaderboard = getTabHost();
        
        // Tab for Leaderboard Day
        TabSpec leaderboardDaySpec = tabHostLeaderboard.newTabSpec("Day");
        // setting Title and Icon for the Tab
        leaderboardDaySpec.setIndicator("Day", getResources().getDrawable(R.drawable.icon_leaderboardday_tab));
        Intent leaderboardDayIntent = new Intent(LeaderboardActivity.this, LeaderboardDayActivity.class);
        leaderboardDaySpec.setContent(leaderboardDayIntent);
 
        // Tab for Leaderboard Dish
        TabSpec leaderboardDishSpec = tabHostLeaderboard.newTabSpec("Dish");
        leaderboardDishSpec.setIndicator("Dish", getResources().getDrawable(R.drawable.icon_leaderboarddish_tab));
        Intent leaderboardDishIntent = new Intent(LeaderboardActivity.this, LeaderboardDishActivity.class);
        leaderboardDishSpec.setContent(leaderboardDishIntent);
 
        // Adding all TabSpec to TabHost
        tabHostLeaderboard.addTab(leaderboardDaySpec); // Adding leaderboard day tab
        tabHostLeaderboard.addTab(leaderboardDishSpec); // Adding leaderboard dish menu tab
        
        for (int i = 0; i < tabHostLeaderboard.getTabWidget().getTabCount(); i++) {
            tabHostLeaderboard.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
        } 
    }
	
}
