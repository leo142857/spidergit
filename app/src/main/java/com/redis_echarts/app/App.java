package com.redis_echarts.app;



import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Hello world!
 *
 */
public class App implements PageProcessor
{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public static void main(String[] args) {
		Spider.create(new App())        
        .addUrl("http://db.auto.sohu.com/cxdata/iframe.html")
        .addPipeline(new MybatisPipe())
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
