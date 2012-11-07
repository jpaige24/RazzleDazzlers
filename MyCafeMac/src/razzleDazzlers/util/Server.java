package razzleDazzlers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
	
	private static final String url = "jdbc:mysql://mathcs.macalester.edu:3306/test";
    private static final String user = "jshan";
    private static final String pass = "razzleDazzlers";
    private static Connection connection; 
	
	public Server(){
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection connect(){
		try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = connection;
            /* System.out.println("Database connection success"); */

            return con;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
		return null; 
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
			System.out.println("Database Server Error");
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
			System.out.println("Database Server Error");
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
			System.out.println("Database Server Error");
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
