package com.carrank;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class CarRank implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		List<Selectable> nodes = page.getHtml()
				.xpath("//table[@id='sortTable']/tbody/tr[1]/td[1]|//table[@id='sortTable']/tbody/tr/th|//table[@id='sortTable']/tbody/tr/td[@class!='tdwid1']")
				.nodes();
		List<Selectable> nodes2 = page.getHtml()
				.xpath("//table[@id='sortTable2']/tbody/tr[1]/td[1]|//table[@id='sortTable2']/tbody/tr/th|//table[@id='sortTable2']/tbody/tr/td[@class!='tdwid1']")
				.nodes();
		List<Selectable> nodes3 = page.getHtml()
				.xpath("//table[@id='sortTable3']/tbody/tr[1]/td[1]|//table[@id='sortTable3']/tbody/tr/th|//table[@id='sortTable3']/tbody/tr/td[@class!='tdwid1']")
				.nodes();
//		for (Selectable selectable : nodes2) {
//			System.out.println(selectable.get().replaceAll("</?[^>]+>", ""));
//		}
		File file = new File(".//rank.xls");
		try {
			file.createNewFile();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("品牌销量排行榜");
			HSSFSheet sheet2 = workbook.createSheet("企业销量排行榜");
			HSSFSheet sheet3 = workbook.createSheet("车型销量排行榜");
			int i = 0;
			HSSFRow nrow1=sheet.createRow(i);
			for (Selectable selectable : nodes) {
				if(i%11==0) {
					nrow1=sheet.createRow(i/11);
				}
				String value = selectable.get().replaceAll("</?[^>]+>", "");
				//System.out.println(value);
				HSSFCell ncell=nrow1.createCell(i%11);
				ncell.setCellValue(value);
				i++;
			}
			i = 0;
			HSSFRow nrow2=sheet2.createRow(i);
			for (Selectable selectable : nodes2) {
				if(i%11==0) {
					nrow2=sheet2.createRow(i/11);
				}
				String value = selectable.get().replaceAll("</?[^>]+>", "");
				//System.out.println(value);
				HSSFCell ncell=nrow2.createCell(i%11);
				ncell.setCellValue(value);
				i++;
			}
			i = 0;
			HSSFRow nrow3=sheet3.createRow(i);
			for (Selectable selectable : nodes3) {
				if(i%11==0) {
					nrow3=sheet3.createRow(i/11);
				}
				String value = selectable.get().replaceAll("</?[^>]+>", "");
				//System.out.println(value);
				HSSFCell ncell=nrow3.createCell(i%11);
				ncell.setCellValue(value);
				i++;
			}
			FileOutputStream stream= FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
			workbook.close();
        }catch (Exception e) {
            e.printStackTrace();
        } 

	}
	
	public static void main(String[] args) {
		Spider.create(new CarRank())        
        .addUrl("http://db.auto.sohu.com/cxdata/iframe.html")
        //.addPipeline(new ExcelPipeline())
        .run();
	}
}
