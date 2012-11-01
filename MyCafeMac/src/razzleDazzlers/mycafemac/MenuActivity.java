package razzleDazzlers.mycafemac;

import java.io.IOException;
import java.util.ArrayList;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MenuActivity extends TabActivity{
	
	private static final String url = "jdbc:mysql://mathcs.macalester.edu:3306/test";
    private static final String user = "jshan";
    private static final String pass = "razzleDazzlers";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        InitNews initNews = new InitNews();
		initNews.execute();
    }
    
    private class InitNews extends AsyncTask<String, Void, String>{

		ProgressDialog progressDialog;
		ArrayList<ArrayList<ArrayList<String>>> menu = new ArrayList<ArrayList<ArrayList<String>>>();

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(MenuActivity.this, "",
                    "Loading Menu...");
        }
		
		@Override
		protected String doInBackground(String... arg0) {
			menu = readMenu();
			
			return null;
		}
		
		@Override
        protected void onPostExecute(String result) {
			progressDialog.dismiss();
			TabHost tabHostMenu = getTabHost();
	        
	        // Tab for All Menu
	        TabSpec menuAllSpec = tabHostMenu.newTabSpec("All");
	        // setting Title and Icon for the Tab
	        menuAllSpec.setIndicator("All", getResources().getDrawable(R.drawable.icon_menuall_tab));
	        Intent menuAllIntent = new Intent(MenuActivity.this, MenuAllActivity.class);
	        menuAllIntent.putExtra("allMenu", menu.get(0).get(0));
	        menuAllSpec.setContent(menuAllIntent);
	 
	        // Tab for Vege Menu
	        TabSpec menuVegeSpec = tabHostMenu.newTabSpec("Vege");
	        menuVegeSpec.setIndicator("Vege", getResources().getDrawable(R.drawable.icon_menuvege_tab));
	        Intent menuVegeIntent = new Intent(MenuActivity.this, MenuVegeActivity.class);
	        menuVegeIntent.putExtra("vegeMenu", menu.get(1).get(0));
	        menuVegeSpec.setContent(menuVegeIntent);
	 
	        // Adding all TabSpec to TabHost
	        tabHostMenu.addTab(menuAllSpec); // Adding all menu tab
	        tabHostMenu.addTab(menuVegeSpec); // Adding vege menu tab
	        
	        for (int i = 0; i < tabHostMenu.getTabWidget().getTabCount(); i++) {
	            tabHostMenu.getTabWidget().getChildAt(i).getLayoutParams().height = 50;
	        }
	        testDB();
        }
		
	}
    
    public void testDB(){
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            /* System.out.println("Database connection success"); */

            String result = "Database connection success\n";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from test");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()) {
            	result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
            }
            System.out.println(result);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
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
					!rawMenu.get(i).equals("Pasta") && !rawMenu.get(i).equals("Soup of the Day")) {
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
    
    
}

