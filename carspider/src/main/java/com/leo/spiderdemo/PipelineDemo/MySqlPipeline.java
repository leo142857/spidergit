package com.leo.spiderdemo.PipelineDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import com.leo.carspider.runnabledemo.SqlRunnable;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;

public class MySqlPipeline implements Pipeline{
	private final static String USER="root";
    private final static String PASSWORD="password";
    private final static String URL="jdbc:mysql://localhost:3306/car?useSSL=false&serverTimezone=UTC";
    private ExecutorService fixedThreadPool;
	private JSONObject json;
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		Connection conn;
		Statement stmt;
		List<String> name = new ArrayList<String>();
		String sql;
		for (Selectable selectable : (List<Selectable>)resultItems.get("nameNodes")) {
			name.add(selectable.get().replaceAll("</?[^>]+>", ""));			
		}
//		SqlRunnable.setName(name);
		sql =   "CREATE TABLE `carsales` (\r\n" + 
				"  `"+name.get(0)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(1)+"`  VARCHAR(255) NOT NULL,\r\n" + 
				"  `"+name.get(2)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(3)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(4)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(5)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(6)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(7)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(8)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(9)+"`  INT DEFAULT NULL,\r\n" + 
				"  `"+name.get(10)+"`  INT DEFAULT NULL,\r\n" + 
				"  PRIMARY KEY (`"+name.get(1)+"`)" + 
				");";
		int count = 0;
		int d = name.size();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt=conn.createStatement();
			stmt.execute("DROP TABLE IF EXISTS `carsales`;");
			stmt.execute(sql);
			json = new JSONObject(new LinkedHashMap());
			fixedThreadPool = Executors.newFixedThreadPool(6);
			for (Selectable selectable : (List<Selectable>)resultItems.get("numNodes")) {
				String value = selectable.get().replaceAll("</?[^>]+>", "");
				if(count%d == 1) {
					json.put(count%d+"",value);					
				}else {
					json.put(count%d+"",Integer.parseInt(value));
				}
				count++;
				if(count%d==0 && count>0) {					
					fixedThreadPool.execute(new SqlRunnable(json,conn));
					json = new JSONObject(new LinkedHashMap());
				}
			}
			while(!fixedThreadPool.isTerminated()) {
				fixedThreadPool.shutdown();
			}
			System.out.println("sql插入完毕");
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
