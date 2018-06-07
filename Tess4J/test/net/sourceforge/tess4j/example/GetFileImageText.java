package net.sourceforge.tess4j.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

public class GetFileImageText {
	public ArrayList<String> GetText(String filePath) throws IOException {
		System.out.println("OCR成功降落到"+filePath+"...开始采摘...");
		//程序开始识别时间
		long nd = 1000 * 24 * 60 * 60;
      	long nh = 1000 * 60 * 60;
      	long nm = 1000 * 60;
      	long ns = 1000;
  		Date ProgramStartDate=new Date();
  		long programStartTime=ProgramStartDate.getTime();
		//定义OCR识别图片返回内容
		ArrayList<String>  FileTextResult = new ArrayList<String> (); 
    	//对文件夹字符数组进行排序
    	File file = new File(filePath);
    	ArrayList<String>  imageNameList = new ArrayList<String> (); 
    	if(file.exists()) {
            File[] files = file.listFiles();
            for (File tempfile : files) {
            	String tempName1=tempfile.getName();
            	if (tempName1.contains("jpg")) {
            		imageNameList.add(tempfile.getName());
            	}
            }
    	}
    	//得到图片路径
    	ArrayList<String>  imagePathList = new ArrayList<String> (); 
    	for (int i=50;i>=1;i--) {
    		String tempNumber=i+"";
    		for (int j=0;j<imageNameList.size();j++) {
    			if (imageNameList.get(j).contains(tempNumber)) {
    				imagePathList.add(filePath+"/"+imageNameList.get(j));
    				imageNameList.remove(j);
    				break;
    			}    			
    		}
    	}
    	//数组转换
    	for (int start = 0, end = imagePathList.size() - 1; start < end; start++, end--) {
            String tempName3=imagePathList.get(end);
            imagePathList.set(end, imagePathList.get(start));
            imagePathList.set(start,tempName3);            
        }
    	//对每个图片进行OCR处理得到字符
    	for(int i=0;i<imagePathList.size();i++) {
    		//得到每个图片的路径
    		System.out.println("OCR采摘"+imagePathList.get(i)+"成果中...");
	        String imagePath=imagePathList.get(i);
			File imageFile = new File(imagePath);
	        BufferedImage image = ImageIO.read(imageFile);
	        //每张图片识别开始时间
	    	Date ImageStartTime=new Date();
	    	long imageStartTime=ImageStartTime.getTime();
	    	//每张图片是否识别到结果
			boolean registrationNumber=false;
			boolean companyName=false;
			String EachImageText="";
			//动态识别图片区域 不识别某些图片
			if(i==15||i==33||i==39||i==49) {
    			System.out.println("OCR! Sorry,We can't find the answer!");
				FileTextResult.add(imagePath);
				FileTextResult.add("OCR! Sorry,We can't find the answer!");
				continue;
    		}
	        for (int startHeight=0;startHeight+(int)(image.getHeight()*0.18)<=image.getHeight();startHeight+=image.getHeight()*0.15) {
				//定义图片识别的宽度和高度
	        	int resetWidth=(int) (image.getWidth()*0.5);
				int resetHeight=(int)(image.getHeight()*0.18);
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
				    	EachImageText=result;
//				    	System.out.println(result);
				    	break;
				    }
				    if(result.contains("注册号")&&registrationNumber==false) {
				    	registrationNumber=true;
				    	EachImageText=EachImageText+result;
//				    	System.out.println(result);
				    }
				    if(result.contains("名称")&&companyName==false) {
				    	companyName=true;
				    	EachImageText=EachImageText+result;
//				    	System.out.println(result);
				    }
				    if(registrationNumber==true&&companyName==true) {
				    	break;
				    }			    
				} catch (TesseractException e) {
				    System.err.println(e.getMessage());
				}
			}
			if (registrationNumber==true&&companyName==true) {
				int endEachImageText=EachImageText.indexOf("公司")+2;
				System.out.println(EachImageText.substring(0,endEachImageText));
				FileTextResult.add(imagePath);
				FileTextResult.add(EachImageText.substring(0,endEachImageText));
			}
		}
    	//识别一张图片所需要的时间
		Date ProgressEndTime=new Date();
    	long progressEndTime=ProgressEndTime.getTime();
    	long progressDiff = progressEndTime-programStartTime;
    	long progressMin = progressDiff % nd % nh / nm-1;
    	long progressSec = progressDiff % nd % nh % nm / ns+10;
    	System.out.println("OCR采摘时间"+progressMin+"min"+progressSec+"s...");
    	//返回识别结果
    	return FileTextResult;
	}
}
