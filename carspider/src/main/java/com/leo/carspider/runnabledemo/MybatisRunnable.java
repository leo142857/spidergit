package com.leo.carspider.runnabledemo;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.alibaba.fastjson.JSONObject;
import com.leo.carspider.carsales.CarSales;

public class MybatisRunnable implements Runnable{
	private CarSales cs;
	private static SqlSessionFactory sessionFactory;
    private SqlSession sqlSession;
	public MybatisRunnable(JSONObject json) {
		cs = new CarSales(json);
	}
//	public void init() {
//		try {
//			String config = "Mybatis-config.xml";
//			InputStream ins = Resources.getResourceAsStream(config);
//			sessionFactory = new SqlSessionFactoryBuilder().build(ins);
//			ins.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//	}
	public static void setSessionFactory(SqlSessionFactory sessionFactory) {
		MybatisRunnable.sessionFactory = sessionFactory;
	} 
	public void run() {
		// TODO Auto-generated method stub
//		init();
		sqlSession = sessionFactory.openSession();
		sqlSession.insert("Mapper.replaceAll",cs);
		sqlSession.commit();
		sqlSession.close();
//		System.out.println(cs);
	}

}
