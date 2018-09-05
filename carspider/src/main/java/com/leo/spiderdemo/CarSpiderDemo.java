package com.leo.spiderdemo;

import com.leo.spiderdemo.PipelineDemo.CarPipeline;
import com.leo.spiderdemo.PipelineDemo.CarPipelineDemo;
import com.leo.spiderdemo.PipelineDemo.CarPipelineES5;
import com.leo.spiderdemo.PipelineDemo.MySqlPipeline;
import com.leo.spiderdemo.PipelineDemo.MybatisPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


public class CarSpiderDemo implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public static void main(String[] args) {
		Spider.create(new CarSpiderDemo())        
        .addUrl("http://db.auto.sohu.com/cxdata/iframe.html")
        .addPipeline(new CarPipelineES5())
//        .addPipeline(new CarPipeline())
//        .addPipeline(new CarPipelineDemo())
//        .addPipeline(new MySqlPipeline())
//        .addPipeline(new MybatisPipeline())
        .run();
	}
	
	public void process(Page page) {

		page.putField("nameNodes",page.getHtml()
				.xpath("//table[@id='sortTable3']/tbody/tr[1]/td[1]|//table[@id='sortTable3']/tbody/tr/th")
				.nodes());
		page.putField("numNodes",page.getHtml()
				.xpath("//table[@id='sortTable3']/tbody/tr/td[@class!='tdwid1']")
				.nodes());
	}

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
}
