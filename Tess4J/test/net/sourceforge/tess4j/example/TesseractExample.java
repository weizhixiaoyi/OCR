package net.sourceforge.tess4j.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import net.sourceforge.tess4j.example.GetFilePath;
import net.sourceforge.tess4j.example.GetOneImageText;
import net.sourceforge.tess4j.example.GetFileImageText;

public class TesseractExample {
    public static void main(String[] args) throws IOException {
    	//[识别一张图片]自动读取企业工商信息图片所在的文件夹路径，耗时过长则自动放弃。
    	GetFilePath GetFilePath= new GetFilePath();
    	String TianMaoFilePath=GetFilePath.AutoGetFilePath("/Users/zhenhai/Library/Mobile\\ Documents/com\\~apple\\~CloudDocs/GitHub/OCR/Tess4J","tianmao1");
    	String imagePath=TianMaoFilePath+"/1.jpg";
        GetOneImageText getOneImage=new GetOneImageText();
        String Text=getOneImage.GetText(imagePath);
        System.out.println(Text);
        
        //[识别文件夹图片]自动读取企业工商信息图片所在的文件夹路径，耗时过长则自动放弃，将处理结果保存到txt和excel文件中。
//    	GetFilePath GetFilePath= new GetFilePath();
//    	String TianMaoFilePath=GetFilePath.AutoGetFilePath("/Users/zhenhai/Downloads/SoftwareCup/Tess4J","tianmao1"); 
//    	GetFileImageText getFileImageText=new GetFileImageText();
//    	ArrayList<String> FileTextResult=getFileImageText.GetText(TianMaoFilePath);
//    	String ResultFilePath=GetFilePath.AutoGetFilePath("/Users/zhenhai/Downloads/SoftwareCup/Tess4J","result");
//    	SaveFileImageResult saveFileImageResult = new SaveFileImageResult();
//    	saveFileImageResult.SaveResult(ResultFilePath, FileTextResult);
    }
}
