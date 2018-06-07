package net.sourceforge.tess4j.example;

import java.io.File;
import java.util.LinkedList;

public class GetFilePath{
	public String AutoGetFilePath(String StartFilePath,String TargetFileName){
		System.out.println("OCR在"+StartFilePath+"迷失..."+"寻找"+TargetFileName+"模块中...");
		String TargetFilePath="";
        File file = new File(StartFilePath);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("文件夹:" + file2.getAbsolutePath());
                    String tempPath=file2.getName();
                    //System.out.println(tempPath);
                    if(file2.getAbsolutePath().contains("bin")) {
                    	continue;
                    }
                    if(tempPath.equals(TargetFileName)) {
                    	TargetFilePath=file2.getAbsolutePath();
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
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        String tempPath=file2.getName();
                        //System.out.println(tempPath);
                        if(file2.getAbsolutePath().contains("bin")) {
                        	continue;
                        }
                        if(tempPath.equals(TargetFileName)) {
                        	TargetFilePath=file2.getAbsolutePath();
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
        System.out.println("OCR成功找到"+TargetFilePath+"...开心的像个300斤的孩子！");
        return TargetFilePath;
	}
	
}
