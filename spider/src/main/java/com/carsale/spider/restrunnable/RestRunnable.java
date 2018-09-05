package com.carsale.spider.restrunnable;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.alibaba.fastjson.JSONObject;

public class RestRunnable implements Runnable{
	public final static String HOST = "127.0.0.1";  
	public final static int PORT = 9200;
	public final static String INDEXNAME = "carsale";
	public final static String CLASSNAME = "date";
	JSONObject json;
	int id;
	public RestRunnable(JSONObject json,int id) {
		this.json = json;
		this.id = id;
	}
	public void run() {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut httpPut = new HttpPut("http://"+HOST+":"+PORT+"/"+INDEXNAME+"/"+CLASSNAME+"/"+id);
		httpPut.setHeader("content-type", "application/json;charset=utf-8");
		try {
			httpPut.setEntity(new StringEntity(json.toJSONString(),"utf-8"));
			client.execute(httpPut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
