package com.leo.carspider;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception, IOException
    {
//         Date date = Date.valueOf(LocalDate.of(2018, 6, 1));
//    	 Date date = Date.valueOf("2018-06-01+08:00");
//    	 System.out.println(date);
//    	 SimpleDateFormat aDate=new SimpleDateFormat("yyyy");
//         SimpleDateFormat bDate=new SimpleDateFormat("yyyy-mmmmmm-dddddd");
//         long now=System.currentTimeMillis();
//         System.out.println(aDate.format(now));
//         System.out.println(bDate.format(now));
    	HttpClient h = HttpClientBuilder.create().build();
//    	String url="http://localhost:9200/carsale/date/_search?q=date:2018";
//    	HttpGet hg = new HttpGet(url);
//    	HttpResponse response = h.execute(hg); 
    	HttpPut hp = new HttpPut("http://localhost:9200/test/data/1");
    	JSONObject json =new JSONObject();
    	json.put("id", 1);
    	json.put("value", 10);
    	hp.setEntity(new StringEntity(json.toString()));
    	hp.setHeader("content-type", "application/json;charset=utf-8");
    	HttpResponse response = h.execute(hp);
    	System.out.println(EntityUtils.toString(response.getEntity()));
//    	System.out.println(EntityUtils.toString(response.getEntity()));
//         DateTime d = new DateTime(2018,1,1,0,0,0,100);
//         System.out.println(d.plusDays(5));
    }
}
