package com.leo.spiderdemo.PipelineDemo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;
import com.leo.carspider.runnabledemo.CarRunnable;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;

public class CarPipelineDemo implements Pipeline{
	public final static String HOST = "127.0.0.1";  
	public final static int PORT = 9300;
	public final static String CLUSTERNAME = "my-application"; 
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
		 try {
			 Settings settings = Settings.builder()  
		                //指定集群名称  
		                .put("cluster.name", CLUSTERNAME)  
		                //探测集群中机器状态  
		                .put("client.transport.sniff", true).build();
			TransportClient client = new PreBuiltTransportClient(settings).addTransportAddresses(
					 new TransportAddress(InetAddress.getByName(HOST),PORT));
			json = new JSONObject(new LinkedHashMap());
			fixedThreadPool = Executors.newFixedThreadPool(6);
			for (Selectable selectable : (List<Selectable>)resultItems.get("numNodes")) {
				String value = selectable.get().replaceAll("</?[^>]+>", "");
				if(count%d == 1) {
					json.put(name.get(count%d),value);					
				}else {
					json.put(name.get(count%d),Integer.parseInt(value));
				}
				count++;
				if(count%d==0 && count>0) {					
					System.out.println(count/d+""+json);
					fixedThreadPool.execute(new CarRunnable(client,json,count/d));
//					fixedThreadPool.submit(new CarRunnable(client,json,count/d));
//					IndexResponse response = client.prepareIndex("car", "json").setId(count/d+"").setSource(json, XContentType.JSON).get();
//					client.prepareIndex("car", "json",count/d+"").setSource(JSONObject.parseObject(json.toString(), Map.class));
//					json.clear();
					json = new JSONObject(new LinkedHashMap());
				}
//				System.out.println(json);
			}
			while(!fixedThreadPool.isTerminated()) {
				fixedThreadPool.shutdown();				
			};
			client.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
