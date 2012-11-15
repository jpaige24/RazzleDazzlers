package razzleDazzlers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Server {
	
	private static final String url = "jdbc:mysql://mathcs.macalester.edu:3306/test";
    private static final String user = "jshan";
    private static final String pass = "razzleDazzlers";
    private static Connection connection; 
	
	public Server(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Worst Days");
		}
		return board;
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Worst Days");
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Worst Days");
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get Best Days");
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get avg dish rating");
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get user dish rating");
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Get avg day rating");
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
				st.executeUpdate(insertLine);
				System.out.println("server insert");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Insert");
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
				st.executeUpdate(line);
				System.out.println("server update");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Update");
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
				e.printStackTrace();
			}
		}else{
			System.out.println("Database Server Error: Check");
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

}
