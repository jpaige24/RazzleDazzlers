package razzleDazzlers.mycafemac;


import razzleDazzlers.ratecafemac.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TabHost tabHost = getTabHost();
        
        // Tab for Menu
        TabSpec menuSpec = tabHost.newTabSpec("Menu");
        // setting Title and Icon for the Tab
        menuSpec.setIndicator("Menu", getResources().getDrawable(R.drawable.icon_menu_tab));
        Intent menuIntent = new Intent(this, MenuActivity.class);
        menuSpec.setContent(menuIntent);
 
        // Tab for Leaderboard
        TabSpec leaderboardSpec = tabHost.newTabSpec("Leaderboard");
        leaderboardSpec.setIndicator("Leaderboard", getResources().getDrawable(R.drawable.icon_leaderboard_tab));
        Intent leaderboardIntent = new Intent(this, LeaderboardActivity.class);
        leaderboardSpec.setContent(leaderboardIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(menuSpec); // Adding menu tab
        tabHost.addTab(leaderboardSpec); // Adding leaderboard tab
    }
    

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
    
    
}
