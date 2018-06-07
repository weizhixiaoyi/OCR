package net.sourceforge.tess4j.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tess4j.example.GetFilePath;
import net.sourceforge.tess4j.example.GetOneImageText;

public class TesseractExample {
	public static String getOneImageText(String imagePath){
		//[识别一张图片]自动读取企业工商信息图片所在的文件夹路径，耗时过长则自动放弃。
    	//String tianMaoFilePath = GetFilePath.autoGetFilePath("F:/源辰java实训/tess4J/test/resources","tianmao1");
    	//System.out.println("tianMaoFilePath:"+tianMaoFilePath);
        String text = "";
		try {
			text = GetOneImageText.getText(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public static List<String> getAllImageText(String path){
		//[识别文件夹图片]自动读取企业工商信息图片所在的文件夹路径，耗时过长则自动放弃，将处理结果保存到txt和excel文件中。
    	String tianMaoFilePath=GetFilePath.autoGetFilePath(path,"tianmao1"); 
    	ArrayList<String> fileTextResult= null;
    	try {
			fileTextResult=GetFileImageText.getText(tianMaoFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	String resultFilePath=GetFilePath.autoGetFilePath(path,"result");
    	try {
			SaveFileImageResult.saveResult(resultFilePath, fileTextResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return fileTextResult;
	}
}
