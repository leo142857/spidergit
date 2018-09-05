package com.redis_echarts.app.domain;



import java.util.Date;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;




public class CarSales {
	private int id;
	private String carType;
	private String dateType;
	private Date date;
	private int sales;
	public CarSales() {}
	public CarSales(int id,String carType,String dateType,String date ) {
		this.id = id;
		this.carType = carType;
		this.dateType = dateType;
		this.date = new DateTime(date).toDate();
	}
	public CarSales(JSONObject json,int id) {
		this.id = id;
		this.carType = json.getString("carType");
		this.dateType = json.getString("dateType");
		this.date = new DateTime(json.getString("date")).toDate();
		this.sales = json.getInteger("carSales");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
