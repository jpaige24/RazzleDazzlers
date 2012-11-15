package razzleDazzlers.mycafemac;

import java.util.ArrayList;
import java.util.Calendar;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.Server;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class LeaderboardActivity extends TabActivity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        
         InitBoard initBoard = new InitBoard();
         initBoard.execute();
    }
	
	private class InitBoard extends AsyncTask<String, Void, String>{

		ProgressDialog progressDialog;
		ArrayList<ArrayList<String>> bestDays;
		ArrayList<ArrayList<String>> worstDays;
		ArrayList<ArrayList<String>> bestDishes;
		ArrayList<ArrayList<String>> worstDishes;
		
        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(LeaderboardActivity.this, "",
                    "Loading Rankings...");
            
        }
		
		@Override
		protected String doInBackground(String... arg0) {
			
	        
	        Server serv = new Server();
	        bestDays = serv.getBestDays();
	        //System.out.println(bestDays);
	        worstDays = serv.getWorstDays();
	        //System.out.println(worstDays);
	        bestDishes = serv.getBestDishes();
	        //System.out.println(bestDishes);
	        worstDishes = serv.getWorstDishes();
	        //System.out.println(worstDishes);
	        
			return null;
		}

		@Override
        protected void onPostExecute(String result) {
			progressDialog.dismiss();
TabHost tabHostLeaderboard = getTabHost();
	        
	        // Tab for Leaderboard Day
	        TabSpec leaderboardDaySpec = tabHostLeaderboard.newTabSpec("Day");
	        // setting Title and Icon for the Tab
	        leaderboardDaySpec.setIndicator("Day", getResources().getDrawable(R.drawable.icon_leaderboardday_tab));
	        Intent leaderboardDayIntent = new Intent(LeaderboardActivity.this, LeaderboardDayActivity.class);
	        leaderboardDayIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        leaderboardDayIntent.putExtra("bestDayRatings", bestDays.get(0));
	        leaderboardDayIntent.putExtra("bestDayDates", bestDays.get(1));
	        leaderboardDayIntent.putExtra("worstDayRatings", worstDays.get(0));
	        leaderboardDayIntent.putExtra("worstDayDates", worstDays.get(1));
	        leaderboardDaySpec.setContent(leaderboardDayIntent);
	 
	        // Tab for Leaderboard Dish
	        TabSpec leaderboardDishSpec = tabHostLeaderboard.newTabSpec("Dish");
	        leaderboardDishSpec.setIndicator("Dish", getResources().getDrawable(R.drawable.icon_leaderboarddish_tab));
	        Intent leaderboardDishIntent = new Intent(LeaderboardActivity.this, LeaderboardDishActivity.class);
	        leaderboardDishIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        leaderboardDishIntent.putExtra("bestDishRatings", bestDishes.get(0));
	        leaderboardDishIntent.putExtra("bestDishNames", bestDishes.get(1));
	        leaderboardDishIntent.putExtra("worstDishRatings", worstDishes.get(0));
	        leaderboardDishIntent.putExtra("worstDishNames", worstDishes.get(1));
	        leaderboardDishSpec.setContent(leaderboardDishIntent);
	 
	        // Adding all TabSpec to TabHost
	        tabHostLeaderboard.addTab(leaderboardDaySpec); // Adding leaderboard day tab
	        tabHostLeaderboard.addTab(leaderboardDishSpec); // Adding leaderboard dish menu tab
	        
	        for (int i = 0; i < tabHostLeaderboard.getTabWidget().getTabCount(); i++) {
	            tabHostLeaderboard.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
	        }
			
        }
		
	}
	
}
