package com.leo.carspider.carsales;

import com.alibaba.fastjson.JSONObject;

public class CarSales {
	private int id;
	private String carType;
	private int field1;
	private int field2;
	private int field3;
	private int field4;
	private int field5;
	private int field6;
	private int field7;
	private int field8;
	private int field9;
	public CarSales() {}
	public CarSales(JSONObject json) {
		this.id = json.getIntValue("0");
		this.carType = json.getString("1");
		this.field1 = json.getIntValue("2");
		this.field2 = json.getIntValue("3");
		this.field3 = json.getIntValue("4");
		this.field4 = json.getIntValue("5");
		this.field5 = json.getIntValue("6");
		this.field6 = json.getIntValue("7");
		this.field7 = json.getIntValue("8");
		this.field8 = json.getIntValue("9");
		this.field9 = json.getIntValue("10");
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
	public int getField1() {
		return field1;
	}
	public void setField1(int field1) {
		this.field1 = field1;
	}
	public int getField2() {
		return field2;
	}
	public void setField2(int field2) {
		this.field2 = field2;
	}
	public int getField3() {
		return field3;
	}
	public void setField3(int field3) {
		this.field3 = field3;
	}
	public int getField4() {
		return field4;
	}
	public void setField4(int field4) {
		this.field4 = field4;
	}
	public int getField5() {
		return field5;
	}
	public void setField5(int field5) {
		this.field5 = field5;
	}
	public int getField6() {
		return field6;
	}
	public void setField6(int field6) {
		this.field6 = field6;
	}
	public int getField7() {
		return field7;
	}
	public void setField7(int field7) {
		this.field7 = field7;
	}
	public int getField8() {
		return field8;
	}
	public void setField8(int field8) {
		this.field8 = field8;
	}
	public int getField9() {
		return field9;
	}
	public void setField9(int field9) {
		this.field9 = field9;
	}
	@Override
	public String toString() {
		return "CarSales [id=" + id + ", carType=" + carType + ", field1=" + field1 + ", field2=" + field2 + ", field3="
				+ field3 + ", field4=" + field4 + ", field5=" + field5 + ", field6=" + field6 + ", field7=" + field7
				+ ", field8=" + field8 + ", field9=" + field9 + "]";
	}
}
