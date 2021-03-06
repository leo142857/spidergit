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
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;
import com.leo.carspider.runnabledemo.DateRunnable;
import com.leo.carspider.runnabledemo.ES5Runnable;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;

public class CarPipelineES5 implements Pipeline{
	public final static String HOST = "127.0.0.1";  
	public final static int PORT = 9300;
	public final static String CLUSTERNAME = "my-application"; 
	private ExecutorService fixedThreadPool;
	private JSONObject json;
	public void process(ResultItems resultItems, Task task) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				List<String> name = new ArrayList<String>();
				for (Selectable selectable : (List<Selectable>)resultItems.get("nameNodes")) {
					name.add(selectable.get().replaceAll("</?[^>]+>|年", ""));			//.replaceAll("年", "")
				}
				int count = 0;
				int d = name.size();
				 try {
					 Settings settings = Settings.builder()   
				                .put("cluster.name", CLUSTERNAME)   
				                .put("client.transport.sniff", true).build();
					TransportClient client = new PreBuiltTransportClient(settings).addTransportAddresses(
							 new TransportAddress(InetAddress.getByName(HOST),PORT));
					json = new JSONObject(new LinkedHashMap());
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
							String dateType = null;
							if(name.get(count%d).contains("-")) {
								dateType ="month";
								String[] date = name.get(count%d).split("-");
								if(date[1].length()>1) {
//									dformat = name.get(count%d)+"-01T00:00:00+08:00";
									dformat = name.get(count%d)+"-01";
								}
								else
								{
//									dformat = date[0]+"-0"+date[1]+"-01T00:00:00+08:00";
									dformat = date[0]+"-0"+date[1]+"-01";
								}
								json.put("date", dformat);
							}
							else {
								dateType ="year";
//								dformat = name.get(count%d)+"-01-01T00:00:00+08:00";
								dformat = name.get(count%d)+"-01-01";
								json.put("date", dformat);
							}					
							json.put("carSales",Integer.parseInt(value));					
							System.out.println(json);
							fixedThreadPool.execute(new ES5Runnable(client,json,id++,dateType));
							json = new JSONObject(new LinkedHashMap());
						}
						count++;					
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
