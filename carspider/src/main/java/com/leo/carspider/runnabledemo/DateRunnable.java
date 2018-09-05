package com.leo.carspider.runnabledemo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.alibaba.fastjson.JSONObject;

public class DateRunnable implements Runnable{
	public final static String INDEXNAME = "carsale";
	public final static String CLASSNAME = "date";
	TransportClient client;
	JSONObject json;
	int id;
	public DateRunnable(TransportClient client,JSONObject json,int id) {
		this.client = client;
		this.json = json;
		this.id = id;
	}
	public void run() {
		// TODO Auto-generated method stub
		client.prepareIndex(INDEXNAME, CLASSNAME,id+"").setSource(json, XContentType.JSON).get();
	}

}
