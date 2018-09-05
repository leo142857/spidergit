package com.carsale.spider.restrunnable;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.alibaba.fastjson.JSONObject;

public class RunES5 implements Runnable{
	public final static String HOST = "127.0.0.1";  
	public final static int PORT = 9200;
	public final static String INDEXNAME = "carsale";
	public final static String CLASSNAME = "date";
	JSONObject json;
	int id;
	String dateType;
	public RunES5(JSONObject json,int id,String dateType) {
		this.json = json;
		this.id = id;
		this.dateType = dateType;
	}
	public void run() {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut httpPut = new HttpPut("http://"+HOST+":"+PORT+"/"+INDEXNAME+"/"+dateType+"/"+id);
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
