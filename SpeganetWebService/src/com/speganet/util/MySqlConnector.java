package com.speganet.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {
	
	
	public static MySqlConnector sqlConnector=new MySqlConnector();
	
	public Connection getDataBase()throws Exception{
	 
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
					Constants.DBURL.getString(),Constants.DBUSERNAME.getString(),Constants.DBPASSWORD.getString());  
			return con;
 			 
	}
	
	
 
	public static MySqlConnector getSqlConnector() {
		return sqlConnector;
	}





	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	System.out.println(	MySqlConnector.sqlConnector.getDataBase());;

	}

}
