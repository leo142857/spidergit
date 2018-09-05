package com.carspider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyPipeline implements Pipeline{

	@Override
	//synchronized(this)
	public synchronized void process(ResultItems resultItems, Task task) {
//		Set<Entry<String, Object>> entry = resultItems.getAll().entrySet();
//		File file = new File("d://poi.xls");
		File file = new File(".//poi.xls");
        //FileOutputStream out = null;
        int rowId = 1;
        try {
            CarSpider.workbook = new HSSFWorkbook(new FileInputStream(file));
            CarSpider.sheet = CarSpider.workbook.getSheetAt(0);
            rowId = CarSpider.sheet.getLastRowNum() + 1; // 获取表格的总行数
        }catch (Exception e) {
            e.printStackTrace();
        } 
        System.out.println(rowId+"："+resultItems.getRequest().getUrl());
		HSSFRow nrow=CarSpider.sheet.createRow(rowId);
//		HSSFCell ncell=nrow.createCell(0);
		for(int i = 0;i<9;i++) {
			HSSFCell ncell=nrow.createCell(i);
			ncell.setCellValue(resultItems.get(CarSpider.title[i])+"");
		}
		//File file=new File("d://poi.xls");
		try {
		    //file.createNewFile();
		    //将excel写入
		    FileOutputStream stream= FileUtils.openOutputStream(file);
		    CarSpider.workbook.write(stream);
		    stream.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}


}
