package razzleDazzlers.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Looper;

public class Server {
	
	private static final String url = "jdbc:mysql://mathcs.macalester.edu:3306/test";
    private static final String user = "jshan";
    private static final String pass = "razzleDazzlers";
    private static Connection connection;
    private Context context;
	
	public Server(Context mcontext){
		this.context = mcontext;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error(context);
		}
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("no connection!!!"+"Server");
			//error(context);
			e.printStackTrace();
		}
	}
	
	public ArrayList<ArrayList<String>> getBestDishes(){
		ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"select avg(rating),dish from dishRating " +
						"group by dish order by avg(rating) DESC LIMIT 5;";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get avg_dish");
				ArrayList<String> ratings = new ArrayList<String>();
				ArrayList<String> dishes = new ArrayList<String>();
	            while(rs.next()) {
	            	ratings.add(rs.getString(1));
	            	dishes.add(rs.getString(2));
	            }
	            board.add(ratings);
	            board.add(dishes);
	            //System.out.println("Best Days = "+rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getBestDishes");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Best Dishes");
			error(context);
		}
		return board;
	}
	
	public ArrayList retrievePhoto(String dishName){
		ArrayList photos = new ArrayList();
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"select photo from dishPhoto where dish = '"+dishName+"' order by ID DESC limit 2;";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server retrieve photo");
	            while(rs.next()) {
	            	//Blob imageBlob = rs.getBlob(1);
	            	//InputStream binaryStream = imageBlob.getBinaryStream();
	            	InputStream binaryStream = rs.getBinaryStream(1);
	            	photos.add(binaryStream);
	            }
	            //System.out.println("Best Days = "+rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"retrieve photo");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: retrieve photo");
			error(context);
		}
		System.out.println("Retreive photo list length: "+photos.size());
		return photos;
	}
	
	public boolean submitPhoto(String date, String dishName, String filePath){
		Connection con = connection;
		if(con!=null){
			try {
				File file = new File(filePath);
				FileInputStream fis = new FileInputStream(file);
				String insertLine = "insert into dishPhoto (dish, date, photo) values (?, ?, ?);";
				PreparedStatement ps = null;
				ps = con.prepareStatement(insertLine);
				ps.setString(1, dishName);
				ps.setString(2, date);
				ps.setBinaryStream(3, fis, (int)file.length());
				System.out.println(insertLine);
				ps.setQueryTimeout(5);
				ps.executeUpdate();
				System.out.println("server submit photo");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"submit photo");
				//error(context);
				e.printStackTrace();
				return false;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not found in "+"submit photo");
				e.printStackTrace();
				return false;
			}
		}else{
			System.out.println("Database Server Error: Submit Photo");
			error(context);
			return false;
		}
	}
	
	public ArrayList<ArrayList<String>> getWorstDishes(){
		ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"select avg(rating),dish from dishRating " +
						"group by dish order by avg(rating) LIMIT 5;";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get avg_dish");
				ArrayList<String> ratings = new ArrayList<String>();
				ArrayList<String> dishes = new ArrayList<String>();
	            while(rs.next()) {
	            	ratings.add(rs.getString(1));
	            	dishes.add(rs.getString(2));
	            }
	            board.add(ratings);
	            board.add(dishes);
	            //System.out.println("Best Days = "+rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getWorstDishes");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Worst Dishes");
			error(context);
		}
		return board;
	}
	
	public ArrayList<ArrayList<String>> getWorstDays(){
		ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"select avg(rating),date from dishRating " +
						"group by date order by avg(rating) LIMIT 5;";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get avg_dish");
				ArrayList<String> ratings = new ArrayList<String>();
				ArrayList<String> dates = new ArrayList<String>();
	            while(rs.next()) {
	            	ratings.add(rs.getString(1));
	            	dates.add(rs.getString(2));
	            }
	            board.add(ratings);
	            board.add(dates);
	            //System.out.println("Best Days = "+rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getWorstDays");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Worst Days");
			error(context);
		}
		return board;
	}
	
	public ArrayList<ArrayList<String>> getBestDays(){
		ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"select avg(rating),date from dishRating " +
						"group by date order by avg(rating) DESC LIMIT 5;";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get avg_dish");
				ArrayList<String> ratings = new ArrayList<String>();
				ArrayList<String> dates = new ArrayList<String>();
	            while(rs.next()) {
	            	ratings.add(rs.getString(1));
	            	dates.add(rs.getString(2));
	            }
	            board.add(ratings);
	            board.add(dates);
	            //System.out.println("Best Days = "+rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getBestDays");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Best Days");
			error(context);
		}
		return board;
	}
	
	public float getAvgDishRating(String name, String date){
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"SELECT AVG(rating) from dishRating WHERE date='" + date +
						"' and dish= '"+ name+ "';";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get avg_dish");
				float r = (float) 0.0;
	            while(rs.next()) {
	            	r += rs.getFloat(1);
	            }
	            System.out.println("Dish avg rating = "+r);
	            return r;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getAvgDishRating");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get avg dish rating");
			error(context);
		}
		return (float) 0.0;
	}
	
	public float getUserDishRating(String name, String date, String user){
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"SELECT AVG(rating) from dishRating WHERE date='" + date +
						"' and dish= '"+ name+ "' and user = '" + user +"';";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get user_dish");
				float r = (float) 0.0;
	            while(rs.next()) {
	            	r += rs.getFloat(1);
	            }
	            System.out.println("Dish user rating = "+r);
	            return r;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getUserDishRating");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get user dish rating");
			error(context);
		}
		return (float) 0.0;
	}
	
	public float getDayRating(String date){
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"SELECT AVG(rating) from dishRating WHERE date='" + date +"';";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server get avg_day");
				float r = (float) 0.0;
	            while(rs.next()) {
	            	r += rs.getFloat(1);
	            }
	            System.out.println("Day avg rating = "+r);
	            return r;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"getDayRating");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get avg day rating");
			error(context);
		}
		return (float) 0.0;
	}
	
	public void insert(String user, String date, String dishName, float rating){
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String insertLine = 
						"INSERT INTO dishRating " + "VALUES " +
								"('" + user + "', '" + date + "', '"+dishName + "', "+rating+");";
				System.out.println(insertLine);
				st.setQueryTimeout(5);
				st.executeUpdate(insertLine);
				System.out.println("server insert");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"insert");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Insert");
			error(context);
		}
	}
	
	public void update(String user, String date, String dishName, float rating){
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"UPDATE dishRating SET rating =" +rating+" WHERE user="+
								"'" + user + "' and date= '" + date + "' and dish='"+dishName + "';";
				System.out.println(line);
				st.setQueryTimeout(5);
				st.executeUpdate(line);
				System.out.println("server update");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"update");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Update");
			error(context);
		}
	}
	
	public boolean check(String user, String date, String dishName){
		Connection con = connection;
		if(con!=null){
			Statement st;
			try {
				st = con.createStatement();
				String line = 
						"SELECT * FROM dishRating WHERE user="  +
								"'" + user + "' and date='" + date + "' and dish= '"+dishName+"';";
				System.out.println(line);
				st.setQueryTimeout(5);
				ResultSet rs = st.executeQuery(line);
				System.out.println("server check");
				/*String result = "";
				while(rs.next()) {
	            	result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
	            }
	            System.out.println("****"+result+"*****");*/
				if(rs.isBeforeFirst()){
					return true;
				}else{
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no connection!!!"+"check");
				//error(context);
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Check");
			error(context);
		}
		return false;
	}
	
	public void testDB(){
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            /* System.out.println("Database connection success"); */

            String result = "Database connection success\n";
            Statement st = con.createStatement();
            st.setQueryTimeout(5);
            ResultSet rs = st.executeQuery("select * from test");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()) {
            	result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
            }
            System.out.println(result);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("no connection!!!");
            System.out.println(e.toString());
            //error(context);
        } 
    }
	
	private static void error(final Context context){
		new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(context).setTitle("No Internet?").setCancelable(false)
                        .setMessage("Oops. WiFi not connected or server not responding. Please check back later.").setNeutralButton("Ok", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            	System.exit(0);
                            }
                        })
                        .create().show();
                Looper.loop();
            }
        }.start();
	}

}
