package razzleDazzlers.mycafemac;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.Server;
import razzleDazzlers.util.Cache;
import razzleDazzlers.util.CrashHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MenuActivity extends TabActivity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Calendar calendar = Calendar.getInstance();
		
    	if(calendar.get(Calendar.DAY_OF_WEEK)==1||calendar.get(Calendar.DAY_OF_WEEK)==7){
    		
    		setContentView(R.layout.activity_menuallsatsun);
    		/*TextView SatSun = (TextView) findViewById(R.id.MenuAllSatSun);
    		SatSun.setText("No Menu For Today");*/
    		
    	}else{
    		
        setContentView(R.layout.activity_menu);
        
        CrashHandler crashHandler = CrashHandler.getInstance();    
        crashHandler.init(this);
        
        InitNews initNews = new InitNews();
		initNews.execute();
		
    	}
    }
    
    private class InitNews extends AsyncTask<String, Void, String>{

		ProgressDialog progressDialog;
		//ArrayList userDishRatingAll = new ArrayList();
		//ArrayList userDishRatingVege = new ArrayList();
		//ArrayList avgDishRatingAll = new ArrayList();
		//ArrayList avgDishRatingVege = new ArrayList();
		float r = (float) 0.0;
		String date = "";
		String device = "";
		int day = 0;
		ArrayList<String> todayAll = new ArrayList();
		ArrayList<String> todayVege = new ArrayList();
		ArrayList<ArrayList<ArrayList<String>>> menu = new ArrayList<ArrayList<ArrayList<String>>>();

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(MenuActivity.this, "",
                    "Loading Menu...");
            Calendar calendar = Calendar.getInstance();
	    	day = calendar.get(Calendar.DAY_OF_WEEK)-2;
	    	if (day < 0 || day > 4) day = 4; 
            
        }
		
		@Override
		protected String doInBackground(String... arg0) {
			final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
			device += tm.getDeviceId();
			Time today = new Time(Time.getCurrentTimezone());
	    	today.setToNow();
	    	date = Integer.toString(today.month)+'/'+Integer.toString(today.monthDay)+'/'+Integer.toString(today.year);
	    	
	    	boolean update = needUpdate();
	    	System.out.println("update? "+update);
	    	if(update){
	    		menu = readMenu();
	    		System.out.println("===== Saving data... ");
	    		saveData(menu);
	    	}else{
	    		System.out.println("===== Reading data... ");
	    		ArrayList<String> keyAll = new ArrayList<String>();
	    		keyAll.add("MondayAll");
	    		keyAll.add("TuesdayAll");
	    		keyAll.add("WednesdayAll");
	    		keyAll.add("ThursdayAll");
	    		keyAll.add("FridayAll");
	    		ArrayList<String> keyVege = new ArrayList<String>();
	    		keyVege.add("MondayVege");
	    		keyVege.add("TuesdayVege");
	    		keyVege.add("WednesdayVege");
	    		keyVege.add("ThursdayVege");
	    		keyVege.add("FridayVege");
	    		if(day <= 4){
	    			todayAll = getData(keyAll.get(day));
	    			todayVege = getData(keyVege.get(day));
	    		}else{
	    			todayAll = getData(keyAll.get(4));
	    			todayVege = getData(keyVege.get(4));
	    		}
	    		if(todayAll.size() == 0 || todayVege.size() == 0){
	    			System.out.println("===== Reading failed... ");
	    			menu = readMenu();
	    		}
	    	}
	    	
			Server serv = new Server(MenuActivity.this);
			r = (float) serv.getDayRating(date);
			/*for(int i=0;i<menu.get(0).get(day).size();i+=2){
				float temp = serv.getUserDishRating(menu.get(0).get(day).get(i), date, device);
				userDishRatingAll.add(temp);
				if(temp < 1){
					avgDishRatingAll.add(serv.getAvgDishRating(menu.get(0).get(day).get(i), date));
				}else{
					avgDishRatingAll.add((float) 0.0);
				}
			}
			for(int i=0;i<menu.get(1).get(day).size();i+=2){
				float tempV = serv.getUserDishRating(menu.get(1).get(day).get(i), date, device);
				userDishRatingVege.add(tempV);
				if(tempV < 1){
					avgDishRatingVege.add(serv.getAvgDishRating(menu.get(1).get(day).get(i), date));
				}else{
					avgDishRatingVege.add((float) 0.0);
				}
			}*/
			//System.out.println("userDishRatingAll" + userDishRatingAll);
			//System.out.println("avgDishRatingAll"+avgDishRatingAll);
			//System.out.println("userDishRatingVege"+userDishRatingVege);
			//System.out.println("avgDishRatingVege"+avgDishRatingVege);
			return null;
		}

		@Override
        protected void onPostExecute(String result) {
			progressDialog.dismiss();
			final TabHost tabHostMenu = getTabHost();
	        
	        // Tab for All Menu
	        TabSpec menuAllSpec = tabHostMenu.newTabSpec("All");
	        // setting Title and Icon for the Tab
	        menuAllSpec.setIndicator("All", getResources().getDrawable(R.drawable.icon_menuall_tab));
	        Intent menuAllIntent = new Intent(MenuActivity.this, MenuAllActivity.class);
	        menuAllIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        if(todayAll.size() > 0 && todayVege.size() > 0){
	        	menuAllIntent.putExtra("allMenu", todayAll);
	        }else{
	        	menuAllIntent.putExtra("allMenu", menu.get(0).get(day));
	        }
	        menuAllIntent.putExtra("r", r);
	        menuAllIntent.putExtra("date", date);
	        menuAllIntent.putExtra("device", device);
	        //menuAllIntent.putExtra("userRating", userDishRatingAll);
	        //menuAllIntent.putExtra("avgRating", avgDishRatingAll);
	        menuAllSpec.setContent(menuAllIntent);
	 
	        // Tab for Vege Menu
	        TabSpec menuVegeSpec = tabHostMenu.newTabSpec("Veggie");
	        menuVegeSpec.setIndicator("Veggie", getResources().getDrawable(R.drawable.icon_menuvege_tab));
	        Intent menuVegeIntent = new Intent(MenuActivity.this, MenuVegeActivity.class);
	        menuVegeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        if(todayAll.size() > 0 && todayVege.size() > 0){
	        	menuVegeIntent.putExtra("vegeMenu", todayVege);
	        }else{
	        	menuVegeIntent.putExtra("vegeMenu", menu.get(1).get(day));
	        }
	        menuVegeIntent.putExtra("r", r);
	        menuVegeIntent.putExtra("date", date);
	        menuVegeIntent.putExtra("device", device);
	        //menuVegeIntent.putExtra("userRating", userDishRatingVege);
	        //menuVegeIntent.putExtra("avgRating", avgDishRatingVege);
	        menuVegeSpec.setContent(menuVegeIntent);
	 
	        // Adding all TabSpec to TabHost
	        tabHostMenu.addTab(menuAllSpec); // Adding all menu tab
	        tabHostMenu.addTab(menuVegeSpec); // Adding vege menu tab
	        
	        tabHostMenu.setOnTabChangedListener(new OnTabChangeListener(){
	        	public void onTabChanged(String tabId) {
	        		Toast.makeText(getApplicationContext(), "Refreshing CafeMac ratings...", Toast.LENGTH_SHORT).show();
	        }});
	        
	        for (int i = 0; i < tabHostMenu.getTabWidget().getTabCount(); i++) {
	            tabHostMenu.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
	        }
        }
		
	}
    
    public ArrayList<ArrayList<ArrayList<String>>> readMenu(){
    	ArrayList data = new ArrayList();
    	LinkedList<String> rawMenu = new LinkedList<String>();
    	LinkedList<String> rawDescription = new LinkedList<String>();
    	try {
			Document doc = Jsoup.connect("http://www.cafebonappetit.com/menu/your-cafe/macalester/cafes/details/159/caf-mac").get();
			Elements dishes = doc.select("tbody strong");
			for (Element strong : dishes){
			    //System.out.println(strong.text());
			    rawMenu.add(strong.text());
			}
			/*Elements des = doc.select("tbody p");
			for (Element td : des){
			    //System.out.println(strong.text());
				if (td.text().length()>1) {
					rawDescription.add(td.text());
				}
			}*/
			Elements des = doc.select("tbody td");
			for (Element td : des){
				if (td.text().length()>2) {
					rawDescription.add(td.text());
				}
			}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*System.out.println(rawDescription);
    	for(int i=0; i<rawDescription.size(); i++){
    		System.out.println(rawDescription.get(i));
    	}
    	System.out.println(rawMenu);*/
    	
    	int i = 2;
		//int j = 0;
		boolean addDay = true;
		ArrayList fullMenu = new ArrayList();
		ArrayList vegeMenu = new ArrayList();
		ArrayList day = new ArrayList();
		int dishCount = 0;
		int totalDish = 0;
		while( i<rawMenu.size()
				//&& j<rawDescription.size()
				){
			if(rawMenu.get(i).equals("Lunch")){
				addDay = true;
				if (day.size()<totalDish){
					vegeMenu.add(day);
					day = new ArrayList();
					dishCount = 0;
					if(fullMenu.size()>1) totalDish = 0;
				}else{
					fullMenu.add(day);
					day = new ArrayList();
					if (fullMenu.size()-vegeMenu.size() == 2){
						ArrayList empty = new ArrayList();
						vegeMenu.add(empty);
					}
				}
			}else if(rawMenu.get(i).equals("Dinner")) {
				addDay = false;
				totalDish = dishCount;
			}else if(!rawMenu.get(i).equals("Lunch") && !rawMenu.get(i).equals("Dinner") &&
					!rawMenu.get(i).equals("Pizza") && !rawMenu.get(i).equals("Grill") &&
					!rawMenu.get(i).equals("Global") && !rawMenu.get(i).equals("Wok") &&
					!rawMenu.get(i).equals("Pasta") && !rawMenu.get(i).equals("Soup of the Day")
					&& !rawMenu.get(i).equals("Soup of the Week")&& !rawMenu.get(i).equals("Chili of the Week")) {
				if (addDay){
						//System.out.println("****"+rawMenu.get(i)+"****");
						//System.out.println("****"+rawDescription.get(j)+"****");
						day.add(rawMenu.get(i));
						day.add(rawDescription.get(i));
						dishCount +=1;
						//j++;
				}else{
					//j++;
				}
			}
			
			i++;
			
		}
		vegeMenu.add(day);
		if (vegeMenu.size() != 5) {
			ArrayList empty = new ArrayList();
			vegeMenu.add(empty);
		}
		System.out.println(fullMenu.size());
		System.out.println(vegeMenu.size());
		for (int k=0; k<fullMenu.size(); k++) {
			System.out.println("Full Menu: ");
			System.out.println(fullMenu.get(k));
			System.out.println("Vegetarian Menu: ");
			System.out.println(vegeMenu.get(k));
		}
    	data.add(fullMenu);
    	data.add(vegeMenu);
    	return data;
    }

    public ArrayList<String> getData(String key){
    	Cache cache = new Cache();
    	return cache.getStringArrayPref(MenuActivity.this, key); 
    }
    
	public void saveData(ArrayList<ArrayList<ArrayList<String>>> menu) {
		Calendar today = Calendar.getInstance();
		String saveDate = today.get(Calendar.YEAR)+"/"+today.get(Calendar.MONTH)+"/"+today.get(Calendar.DATE);
		System.out.println("=====saveDate In"+saveDate);
		Cache cache = new Cache();
		cache.setDatePref(MenuActivity.this, "saveDate", saveDate);
		cache.setStringArrayPref(MenuActivity.this, "MondayAll", menu.get(0).get(0));
		cache.setStringArrayPref(MenuActivity.this, "TuesdayAll", menu.get(0).get(1));
		cache.setStringArrayPref(MenuActivity.this, "WednesdayAll", menu.get(0).get(2));
		cache.setStringArrayPref(MenuActivity.this, "ThursdayAll", menu.get(0).get(3));
		cache.setStringArrayPref(MenuActivity.this, "FridayAll", menu.get(0).get(4));
		cache.setStringArrayPref(MenuActivity.this, "MondayVege", menu.get(1).get(0));
		cache.setStringArrayPref(MenuActivity.this, "TuesdayVege", menu.get(1).get(1));
		cache.setStringArrayPref(MenuActivity.this, "WednesdayVege", menu.get(1).get(2));
		cache.setStringArrayPref(MenuActivity.this, "ThursdayVege", menu.get(1).get(3));
		cache.setStringArrayPref(MenuActivity.this, "FridayVege", menu.get(1).get(4));
	}
    
	public boolean needUpdate() {
		Calendar today = Calendar.getInstance();
		if(today.get(Calendar.DAY_OF_WEEK) == 2){
			return true;
		}
		Cache cache = new Cache();
		String saveDate = cache.getDatePref(MenuActivity.this, "saveDate");
		System.out.println("=====saveDate Out"+saveDate);
		if(saveDate!=null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			Date d = null;
			try {
			   d = formatter.parse(saveDate);//catch exception
			} catch (ParseException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			}
			Calendar thatDay = Calendar.getInstance();
			thatDay.setTime(d);
			thatDay.set(Calendar.MONTH, thatDay.get(Calendar.MONTH)+1);
			long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();
			//System.out.println("====== today" +today+"===== that day "+thatDay);
			long days = diff/(24*60*60*1000);
			System.out.println("=====Diff"+days);
			int threshold = 0;
			if(today.get(Calendar.DAY_OF_WEEK) == 1){
				threshold = 6;
			}else{
				threshold = today.get(Calendar.DAY_OF_WEEK)-2;
			}
			if(threshold-days < 0){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
		
	}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
    
}



