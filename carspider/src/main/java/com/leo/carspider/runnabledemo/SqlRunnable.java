package com.leo.carspider.runnabledemo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


import com.alibaba.fastjson.JSONObject;

public class SqlRunnable implements Runnable{
	private JSONObject json;
	private Connection conn;
	public SqlRunnable(JSONObject json,Connection conn) {
		this.json = json;
		this.conn = conn;
	}
	public String json2Sql() {
		String Sql = "REPLACE INTO carsales VALUES ("+json.getString("0")+",'"+json.getString("1")+"',"
				+json.getString("2")+","+json.getString("3")+","+json.getString("4")+","
				+json.getString("5")+","+json.getString("6")+","+json.getString("7")+","
				+json.getString("8")+","+json.getString("9")+","+json.getString("10")+");";		
		return Sql;		
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			Statement stmt = conn.createStatement();
			String sql = json2Sql();
			stmt.execute(sql);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
