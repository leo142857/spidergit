package com.carspider;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;



public class CarSpider implements PageProcessor{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public static HSSFWorkbook workbook;
	public static HSSFSheet sheet;
	public static String[] title={"项目","2018-6","2018-5","2018-4","2018-6同比","2018-6环比","1-6月累计","累计同比","2017年"};
	public static void main(String[] args) {
		//String[] title={"项目","2018-6","2018-5","2018-4","2018-6同比","2018-6环比","1-6月累计","累计同比","2017年"};
		CarSpider.workbook=new HSSFWorkbook();
		CarSpider.sheet=workbook.createSheet();
		HSSFRow row=sheet.createRow(0);
		HSSFCell cell=null;
		for(int i=0;i<CarSpider.title.length;i++){
		    cell=row.createCell(i);
		    cell.setCellValue(title[i]);
		}
		File file=new File(".//poi.xls");
		try {
			file.createNewFile();
			//将excel写入
			FileOutputStream stream= FileUtils.openOutputStream(file);
			CarSpider.workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Spider.create(new CarSpider())
        
        .addUrl("http://db.auto.sohu.com/cxdata/")//http://db.auto.sohu.com/model_2251/salescar/iframe.html
        .addPipeline(new MyPipeline())        
        //开启5个线程抓取
        .thread(1)
        //启动爬虫
        .run();
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
//        System.out.println("抓取的内容："+
//                page.getHtml().xpath("//div[@class='area_box']//div[@class='tit']/h2/text()").toString()
//                );
        //System.out.println(page.getRawText());
		page.putField("name", page.getHtml().xpath("//div[@class='area_box']//div[@class='tit']/h2/text()").get());
		if (page.getResultItems().get("name")==null||page.getResultItems().get("name").toString().contains("份额")){
            //skip this page
            page.setSkip(true);
        }
//		page.putField(page.getHtml().xpath("//table[@class='salenum']/tbody/tr[1]/th[1]/text()").get(), 
//				page.getHtml().xpath("//table[@class='salenum']/tbody//tr[2]/td[1]/text()").get());
		for(int i = 1;i<10;i++) {
			page.putField(page.getHtml().xpath("//table[@class='salenum']/tbody/tr[1]/th["+i+"]/text()").get(), 
					page.getHtml().xpath("//table[@class='salenum']/tbody//tr[2]/td["+i+"]/text()").get());
		}
		//page.addTargetRequests(page.getHtml().links().regex("(http://db.auto.sohu.com/model_\\d+/salescar/iframe.html)|(http://db.auto.sohu.com/brand\\w+/\\w+/iframe.html)").all());
		page.addTargetRequests(page.getHtml().links().regex("((http://db.auto.sohu.com/brand\\w+/\\w+/iframe.html)|(http://db.auto.sohu.com/model_\\d+/salescar/iframe.html))").all());
//		page.putField(page.getHtml().xpath("//table[@class='salenum']/tbody/tr[1]/th[2]/text()").get(), 
//				page.getHtml().xpath("//table[@class='salenum']/tbody//tr[2]/td[2]/text()").get());
//		page.putField(page.getHtml().xpath("//table[@class='salenum']/tbody/tr[1]/th[3]/text()").get(), 
//				page.getHtml().xpath("//table[@class='salenum']/tbody//tr[2]/td[3]/text()").get());
//		page.putField(page.getHtml().xpath("//table[@class='salenum']/tbody/tr[1]/th[4]/text()").get(), 
//				page.getHtml().xpath("//table[@class='salenum']/tbody//tr[2]/td[4]/text()").get());
	}
}
