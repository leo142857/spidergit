package com.githubprocessor;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://meishi.qq.com/beijing/c/all[\\-p2]*")
@ExtractBy(value = "//ul[@id=\"promos_list2\"]/li",multi = true)
public class QQMeiShi {
	 @ExtractBy("//div[@class=info]/a[@class=title]/h4/text()")
	    private String shopName;

	    @ExtractBy("//div[@class=info]/a[@class=title]/text()")
	    private String promo;
	    public static void main(String[] args) {
	    	OOSpider.create(Site.me(), new ConsolePageModelPipeline(), QQMeiShi.class).addUrl("http://meishi.qq.com/beijing/c/all").thread(4).run();
		}
}
