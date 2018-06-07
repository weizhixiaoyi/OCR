package net.sourceforge.tess4j.example;

import java.io.File;
import java.util.LinkedList;

public class GetFilePath{
	public static String autoGetFilePath(String startFilePath,String targetFileName){
		System.out.println("OCR在："+startFilePath+"迷失..."+"寻找"+targetFileName+"模块中...");
		String targetFilePath="";
        File file = new File(startFilePath);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    String tempPath=file2.getName();
                    if(file2.getAbsolutePath().contains("bin")) {
                    	continue;
                    }
                    if(tempPath.equals(targetFileName)) {
                    	targetFilePath=file2.getAbsolutePath();
                    }
                    list.add(file2);
                } else {
                    //System.out.println("文件:" + file2.getAbsolutePath());
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        String tempPath=file2.getName();
                        if(file2.getAbsolutePath().contains("bin")) {
                        	continue;
                        }
                        if(tempPath.equals(targetFileName)) {
                        	targetFilePath=file2.getAbsolutePath();
                        }
                        list.add(file2);
                    } else {
                        //System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("OCR表示文件不存在!");
        }
        System.out.println("OCR成功找到"+targetFilePath+"...开心的像个300斤的孩子！");
        return targetFilePath;
	}
	
}
