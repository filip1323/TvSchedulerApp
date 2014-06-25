/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Filip
 */
public class Database {

    private SQLiteDB db;

    static private Database instance = null;

    public static Database getInstance() {

	if (instance == null) {
	    instance = new Database();

	}
	return instance;

    }

    private Database() {
	try {
	    db = new SQLiteDB();
	} catch (SQLException ex) {
	    ex.printStackTrace();
	}
    }

    private class SQLiteDB {

	private final Connection connection;
	private final Statement statement;

	public SQLiteDB() throws SQLException {

	    connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	    statement = connection.createStatement();
	    statement.setQueryTimeout(30);

	}

	public ResultSet query(String query) {
	    try {
		if (query.contains("SELECT")) {
		    return statement.executeQuery(query);

		} else {
		    statement.execute(query);
		    return null;
		}
	    } catch (SQLException ex) {
		ex.printStackTrace();
	    }

	    return null;

	}

    }

    public void setConfigProperty(String key, String value) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getConfigProperty(String key) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public HashMap<String, String> getConfigProperties() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
