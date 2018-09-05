package com.redis_echarts.app;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSONObject;
import com.redis_echarts.app.domain.CarSales;



public class MybatisRunnable implements Runnable{
	private CarSales cs;
	private static SqlSessionFactory sessionFactory;
    private SqlSession sqlSession;
    public MybatisRunnable(JSONObject json, int id) {
    	cs = new CarSales(json,id);
    }
	public void run() {
		// TODO Auto-generated method stub
		sqlSession = sessionFactory.openSession();
		sqlSession.insert("Mapper.replaceAll",cs);
		sqlSession.commit();
		sqlSession.close();
	}

	public static void setSessionFactory(SqlSessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		MybatisRunnable.sessionFactory = sessionFactory;
	}

}
