package net.sourceforge.tess4j.example;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO; 
import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.ImageHelper;

public class GetOneImageText {
	public String GetText(String imagePath) throws IOException{
		System.out.println("OCR成功降落到"+imagePath+"...开始采摘...");
		//程序开始识别时间
		long nd = 1000 * 24 * 60 * 60;
      	long nh = 1000 * 60 * 60;
      	long nm = 1000 * 60;
      	long ns = 1000;
  		Date ProgramStartDate=new Date();
  		long programStartTime=ProgramStartDate.getTime();
  		//定义OCR识别图片返回内容
		String ImageTextResult="";
		//读取图片
		File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);   
		//是否更改图片大小
		boolean registrationNumber=false;
		boolean companyName=false;
        //图片开始OCR处理，开始计时
		System.out.println("OCR采摘"+imagePath+"之中...");
    	Date ImageStartTime=new Date();
    	long imageStartTime=ImageStartTime.getTime();
		for (int startHeight=0;startHeight+(int)(image.getHeight()*0.2)<=image.getHeight();startHeight+=image.getHeight()*0.15) {
			//定义每次识别高度和宽度
			int resetWidth=(int) (image.getWidth()*0.5);
			int resetHeight=(int)(image.getHeight()*0.17);
			//图片锐化
			BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(image, 0, startHeight, resetWidth, resetHeight));
			textImage = ImageHelper.convertImageToBinary(textImage);
			//图片放大5倍
			textImage = ImageHelper.getScaledInstance(textImage, textImage.getWidth() * 10, textImage.getHeight() * 10);
			
			ITesseract instance = new Tesseract(); // JNA Interface Mapping
			 //ITesseract instance = new Tesseract1(); // JNA Direct Mapping
			instance.setDatapath("/Users/zhenhai/Downloads/SoftwareCup/Tess4J"); // replace <parentPath> with path to parent directory of tessdata
			instance.setLanguage("chi_sim");
			//耗时过长则返回结果
			Date ImageEndTime=new Date();
	    	long imageEndTime=ImageEndTime.getTime();
	    	long imageDiff = imageEndTime-imageStartTime;
	    	long imageSec = imageDiff % nd % nh % nm / ns;
	    	if(imageSec>=5) {
	    		break;
	    	}
			try {
			    String result = instance.doOCR(textImage);
			    if (result.contains("注册号")&&result.contains("名称")) {
			    	companyName=true;
			    	registrationNumber=true;
			    	ImageTextResult=result;
//			    	System.out.println(result);
			    	break;
			    }
			    if(result.contains("注册号")&&registrationNumber==false) {
			    	registrationNumber=true;
			    	ImageTextResult=ImageTextResult+result;
//			    	System.out.println(result);
			    }
			    if(result.contains("名称")&&companyName==false) {
			    	companyName=true;
			    	ImageTextResult=ImageTextResult+result;
//			    	System.out.println(result);
			    }
			    if(registrationNumber==true&&companyName==true) {
			    	break;
			    }			    
			} catch (TesseractException e) {
			    System.err.println(e.getMessage());
			}
		}
		//识别一张图片所需要的时间
		Date ProgressEndTime=new Date();
    	long progressEndTime=ProgressEndTime.getTime();
    	long progressDiff = progressEndTime-programStartTime;
    	long progressSec = progressDiff % nd % nh % nm / ns;
    	//返回识别结果
    	System.out.println("OCR采摘时间"+progressSec+"s");
    	System.out.println("OCR采摘完成...结果已空运到您眼前...您请享用吧！");
		if (registrationNumber==true&&companyName==true) {
			int endImageText=ImageTextResult.indexOf("公司")+2;
			return ImageTextResult.substring(0,endImageText);
		}else {
			return "OCR! Sorry,We can't find the answer!";
		}
	}
}
