package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.CarSales;
import com.example.demo.message.RequestMessage;
import com.example.demo.message.ResponseMessage;
import com.example.demo.service.SocketService;


@Controller
public class SocketController {
	@Autowired
	private SocketService service;
	private SqlSessionFactory sessionFactory;
    private SqlSession sqlSession;
	   @MessageMapping("/server")
	    @SendTo("/topic/client")
//	    @SendToUser("/msg")
	    public ResponseMessage greeting(RequestMessage message) throws Exception {
		   try {
				InputStream ins;
				ins = Resources.getResourceAsStream("Mybatis-config.xml");
				sessionFactory = new SqlSessionFactoryBuilder().build(ins);
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   String carType = message.getMessage();
		   List list = new ArrayList<CarSales>();
		   JSONObject json = new JSONObject(new LinkedHashMap());
		   sqlSession = sessionFactory.openSession();
		   list = sqlSession.selectList("selectSales", carType);
		   System.out.println(list.size());
		   json.put("name", ((CarSales) list.get(0)).getCarType());
		   for(int i = 0;i<9;i++) {
			   CarSales carSales = (CarSales) list.get(i);
			   if(!json.getString("name").equals(carSales.getCarType())) {
				   json.put("name", "error");
			   }
			   if(carSales.getDateType().equals("year")) {
				   json.put("datekey"+i, new DateTime(carSales.getDate()).toString("yyyy")+"年"); 
//				   new DateTime(carSales.getDate()).toString("yyyy")/carSales.getDate().toString().split(" ")[5]+"年"
			   }
			   else {
				   json.put("datekey"+i, new DateTime(carSales.getDate()).toString("MM")+"月");
			   }
			   json.put("valuekey"+i, carSales.getSales());
		   }
		   System.out.println(json.toString());
	        return new ResponseMessage(json.toString());
}
}