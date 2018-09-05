package com.redis_echarts.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;

public class MybatisPipe implements Pipeline{
	private ExecutorService fixedThreadPool;
	private JSONObject json;
	public void process(ResultItems resultItems, Task task) {

		// TODO Auto-generated method stub
		List<String> name = new ArrayList<String>();
		for (Selectable selectable : (List<Selectable>)resultItems.get("nameNodes")) {
			name.add(selectable.get().replaceAll("</?[^>]+>|年", ""));			//.replaceAll("年", "")
		}
		try {
			InputStream ins;
			ins = Resources.getResourceAsStream("Mybatis-config.xml");
			MybatisRunnable.setSessionFactory(new SqlSessionFactoryBuilder().build(ins));
			ins.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0;
		int d = name.size();
		json = new JSONObject();
		fixedThreadPool = Executors.newFixedThreadPool(6);
		String carType = null;
		int id = 1;
		for (Selectable selectable : (List<Selectable>)resultItems.get("numNodes")) {
			String value = selectable.get().replaceAll("</?[^>]+>", "");
			if(count%d == 1) {
				carType = value;				
			}
			if(count%d >1) {
				json.put("carType", carType);
				String dformat = null;
				if(name.get(count%d).contains("-")) {
					json.put("dateType", "month");
					String[] date = name.get(count%d).split("-");
					if(date[1].length()>1) {
						//										dformat = name.get(count%d)+"-01T00:00:00+08:00";
						dformat = name.get(count%d)+"-01";
//						dformat = name.get(count%d);
					}
					else
					{
						//										dformat = date[0]+"-0"+date[1]+"-01T00:00:00+08:00";
						dformat = date[0]+"-0"+date[1]+"-01";
//						dformat = date[0]+"-0"+date[1];
					}
					json.put("date", dformat);
				}
				else {
					json.put("dateType", "year");
					//									dformat = name.get(count%d)+"-01-01T00:00:00+08:00";
					dformat = name.get(count%d)+"-01-01";
//					dformat = name.get(count%d);
					json.put("date", dformat);
				}					
				json.put("carSales",Integer.parseInt(value));					
				System.out.println(json);
				fixedThreadPool.execute(new MybatisRunnable(json,id++));
				json = new JSONObject();
			}
			count++;					
		}
		while(!fixedThreadPool.isTerminated()) {
			fixedThreadPool.shutdown();				
		};

	}

}
