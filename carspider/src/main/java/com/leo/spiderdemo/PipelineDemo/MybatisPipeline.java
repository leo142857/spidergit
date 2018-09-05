package com.leo.spiderdemo.PipelineDemo;

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
import com.leo.carspider.runnabledemo.MybatisRunnable;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;

public class MybatisPipeline implements Pipeline{
	private ExecutorService fixedThreadPool;
	private JSONObject json;
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		List<String> name = new ArrayList<String>();
		for (Selectable selectable : (List<Selectable>)resultItems.get("nameNodes")) {
			name.add(selectable.get().replaceAll("</?[^>]+>", ""));			
		}
		int count = 0;
		int d = name.size();
		json = new JSONObject(new LinkedHashMap());
		fixedThreadPool = Executors.newFixedThreadPool(12);
		try {
			InputStream ins;
			ins = Resources.getResourceAsStream("Mybatis-config.xml");
			MybatisRunnable.setSessionFactory(new SqlSessionFactoryBuilder().build(ins));
			ins.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Selectable selectable : (List<Selectable>)resultItems.get("numNodes")) {
			String value = selectable.get().replaceAll("</?[^>]+>", "");
			if(count%d == 1) {
				json.put(count%d+"",value);					
			}else {
				json.put(count%d+"",Integer.parseInt(value));
			}
			count++;
			if(count%d==0 && count>0) {					
//				System.out.println(count/d+""+json);
				fixedThreadPool.execute(new MybatisRunnable(json));
				json = new JSONObject(new LinkedHashMap());
			}
		}
		while(!fixedThreadPool.isTerminated()) {
			fixedThreadPool.shutdown();
		}
		System.out.println("sql插入完毕");
	}

}
