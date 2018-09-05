package com.leo.carspider.runnabledemo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import com.alibaba.fastjson.JSONObject;

public class CarRunnable implements Runnable{
	public final static String INDEXNAME = "car";
	public final static String CLASSNAME = "json";
	TransportClient client;
	JSONObject json;
	int id;
	public CarRunnable(TransportClient client,JSONObject json,int id) {
		this.client = client;
		this.json = json;
		this.id = id;
	}
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(json);
		//client.prepareIndex(INDEXNAME, CLASSNAME).setId(id+"").setSource(json, XContentType.JSON).get();
	}

}
