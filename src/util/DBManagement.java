package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


// JNDI를 통해 DBCP에서 connection 객체를 반환하는 클래스
public class DBManagement {
	
	private static Connection con = null;
	
	public static Connection getConnection() throws NamingException, SQLException {
		Context initContext;
		initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
		if (con == null) {
			return ds.getConnection();
		}
		
		return con;
	}
}
