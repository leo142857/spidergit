package com.leo.carspider.runnabledemo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.alibaba.fastjson.JSONObject;

public class ES5Runnable implements Runnable{
	public final static String INDEXNAME = "carsale";
	public final static String CLASSNAME = "date";
	TransportClient client;
	JSONObject json;
	int id;
	String dateType;
	public ES5Runnable(TransportClient client,JSONObject json,int id,String dateType) {
		this.client = client;
		this.json = json;
		this.id = id;
		this.dateType = dateType;
	}
	public void run() {
		// TODO Auto-generated method stub
		client.prepareIndex(INDEXNAME, dateType,id+"").setSource(json, XContentType.JSON).get();
	}
}
