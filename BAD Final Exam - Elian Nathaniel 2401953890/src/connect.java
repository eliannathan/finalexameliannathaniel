import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

public class connect {
	
	@SuppressWarnings("unused")
	private Connection con;
	private java.sql.Statement stat;

	public connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/bike_store", "root", "");			
			stat = con.createStatement();		
			System.out.println("Connect Successful");
		} catch (Exception e) {
			System.out.println("Connect Failed");
			e.printStackTrace();
		}
	}

	public ResultSet runQuery(String query) {
		ResultSet rs = null;
		try {
			rs = stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public boolean runUpdate(String query) {
		try {
			stat.executeUpdate(query);
			
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
				
	}
}
