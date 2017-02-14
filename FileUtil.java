package com.ccx.credit.data.shared.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil
{
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
    //创建 file
    public static File creditFileByDirAndName ( String dir, String name ) {
        File file = new File(dir,name);
        boolean flag = false;
        try
        {
            if ( !file.exists() ) { //判断文件是否存在
                flag = file.createNewFile();//创建文件
                logger.debug("创建 file结果:" + flag);
            } else {
            	logger.info("文件已存在。");
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
        	logger.warn("",e);
        }
        return file;
    }
    
    public static void write ( File file, String content ) {
        byte[] bt = content.getBytes();
        try {
//            FileOutputStream in = new FileOutputStream(file);//覆盖原内容
            FileOutputStream in = new FileOutputStream(file,true);//不覆盖原内容
            in.write(bt);
            in.close();
        } catch ( IOException e ) {
        	logger.warn("",e);
        }
    }
    
    public static void readTxtFile(String filePath){
        try {
                String encoding="UTF-8";
                File file = new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    int i = 0;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        i++;
                        logger.debug(lineTxt);
                      
                    }
                    logger.debug("read num:" + i);
                    read.close();
        }else{
        	logger.debug("找不到指定的文件");
        }
        } catch (Exception e) {
        	logger.warn("读取文件内容出错", e);
        }
     
    }
    
    public static boolean writeToFile ( String filePath, String fileName, String content ) {
    	
    	if ( fileName == null || fileName.trim().equals("") 
    			|| content == null || content.trim().equals("") ) {
    		return false;
    	} else {
    		fileName = fileName.trim();
    		content = content.trim();
    	}
    	
    	boolean hasDir = false;
    	if ( filePath != null ) {
    		File fp = new File(filePath);
        	if ( !fp.isDirectory() ) {
        		hasDir = fp.mkdirs();
        	} else {
        		hasDir = true;
        	}
    	} else {
    	    logger.info("当前目录");
    	    hasDir = true;
    	}
    	
    	boolean hasFile = false;
    	File fn = null;
    	if ( hasDir ) {
    		fn = new File(filePath + File.separator + fileName);
        	if ( !fn.exists()) { //判断文件是否存在
                try {
                	hasFile = fn.createNewFile();//创建文件
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				logger.warn("",e);
    			}
            } else {
            	hasFile = true;
            }
    	}
    	
    	if ( hasFile ) {
    		try {
    			FileOutputStream outStream = new FileOutputStream(fn);
    			BufferedOutputStream buff = new BufferedOutputStream(outStream);
    			
    			byte[] bs = content.getBytes();
    			buff.write(bs);
    			buff.flush();
    			buff.close();
    			
    			return true;
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			logger.warn("",e);
    			return false;
    		}
    	} else {
    		return false;
    	}
		
    }
    
    public static void main(String[] strings) {
        /*String Separators = "\",\"";//分割符
        String enter = "\r\n";//回车  window - \r\n 换行   linux - \n
        File file = FileOpertionUtil.creditFileByDirAndName("C:\\Users\\lixiaohui\\Desktop", "txt.txt");
        if ( file != null ) {
            String content = enter + "写入文件" + Separators; 
            FileOpertionUtil.write(file, content);
        }*/
        
//        readTxtFile("C:\\Users\\lixiaohui\\Desktop\\user_tele.txt");
    	System.out.println(writeToFile("C:\\Users\\lixiaohui\\Desktop\\lixiaohui","lixiaohui.txt","lixiaohui1"));
    }
    
    
}
