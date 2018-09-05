package com.carsale.spider.runnable;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.alibaba.fastjson.JSONObject;

public class ES5CarRunnable implements Runnable{
	public final static String INDEXNAME = "carsale";
	public final static String CLASSNAME = "date";
	TransportClient client;
	JSONObject json;
	int id;
	String dateType;
	public ES5CarRunnable(TransportClient client,JSONObject json,int id,String dateType) {
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
