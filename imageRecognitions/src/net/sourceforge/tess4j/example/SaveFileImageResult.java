package net.sourceforge.tess4j.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SaveFileImageResult {
	public static void saveResult(String resultFilePath,ArrayList<String> fileTextResult) throws IOException {
		//清空存储结果的文件夹
		File ResultFile=new File(resultFilePath);
    	File[] files = ResultFile.listFiles();
        for (File tempfile : files) {
        	tempfile.delete();
        }
        //新建txt文档
        String fileTextResultTxtPath=resultFilePath+"/textFileResult.txt";
        File FileTextResultTxt=new File(fileTextResultTxtPath);
        FileTextResultTxt.createNewFile();
        //将内容写入到txt文档
        System.out.println("OCR成果空运到txt文档中...！");
        BufferedWriter txtFw=new BufferedWriter(new FileWriter(FileTextResultTxt));
        if(FileTextResultTxt.exists()) {
            for(int i=0;i<fileTextResult.size();i++) {
            	txtFw.write(fileTextResult.get(i)+"\n");
            }
            txtFw.close();
        }
        System.out.println("OCR成果空运到txt文档完成！");
        //将结果写入到Excel表格，首先创建表格
        System.out.println("OCR成果空运到excel表格中...！");
        String fileTextResultExcelPath=resultFilePath+"/textFileResult1.xls";
        //System.out.println(fileTextResultExcelPath);
        HSSFWorkbook ExcelResultWorkBook = new HSSFWorkbook();
        HSSFSheet ExcelResultSheet = ExcelResultWorkBook.createSheet("result");
        //增加表头数据
        HSSFRow rowhead = ExcelResultSheet.createRow(0);
        rowhead.createCell(0).setCellValue("序号");
        rowhead.createCell(1).setCellValue("企业名称");
        rowhead.createCell(2).setCellValue("企业序列号");
        //读取每行数据，写入到Excel表格
        try {
			BufferedReader txtFr= new BufferedReader(new FileReader(FileTextResultTxt));
			String line=null;
			int rowNum=0;
			HSSFRow currentRow=null;
			while((line=txtFr.readLine())!=null) {
	            //将结果写入到每个Excel表格单元
				if(line.contains(".jpg")) {
					//System.out.println(line);
					rowNum=rowNum+1;
					HSSFRow tempRow = ExcelResultSheet.createRow(rowNum);;
					tempRow.createCell(0).setCellValue(rowNum);	
					currentRow=tempRow;
				}
				if(line.contains("企业注册号")) {
					//System.out.println(line);
					int lineLength=line.length();
					currentRow.createCell(1).setCellValue(line.substring(7, lineLength));
				}
				if(line.contains("企业名称")) {
					//System.out.println(line);
					StringBuilder tempLine=new StringBuilder(line);
					tempLine.replace(tempLine.length()-4, tempLine.length(), "有限公司");
					int lineLength=tempLine.length();
					currentRow.createCell(2).setCellValue(tempLine.substring(6, lineLength));
				}
				if(line.contains("Sorry,We can't find the answer!")) {
					//System.out.println(line);
					currentRow.createCell(1).setCellValue("TooDifficult");
					currentRow.createCell(2).setCellValue("TooDifficult");
				}				
			}
			//将结果流写入到Excel表格之中
			FileOutputStream ExcelResultFileOut = new FileOutputStream(fileTextResultExcelPath);
            ExcelResultWorkBook.write(ExcelResultFileOut);
            ExcelResultFileOut.close();
			txtFr.close();
		}catch(IOException e) {
			System.out.println("Sorry,The IO exception!");
			e.printStackTrace();
		}
        System.out.println("OCR成果空运到excel表格完成！");
        System.out.println("OCR成果空运到"+resultFilePath+"...您请享用吧！");
	}
}
