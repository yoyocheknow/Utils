package com.ccx.credit.data.shared.util.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Util
{
    public final static String MD5(String s)
    {
        //char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        // update MD5 string to lower-case to adapt to UMP test
        // TODO: check lower-case or upperp-case are the MD5 standard
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try
        {
            byte[] btInput = s.getBytes();
            
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            
            mdInst.update(btInput);
            
            byte[] md = mdInst.digest();
            
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static final String Md(String plainText,boolean judgeMD) {   
        StringBuffer buf = new StringBuffer("");   
        try {   
        MessageDigest md = MessageDigest.getInstance("MD5");   
        md.update(plainText.getBytes());   
        byte b[] = md.digest();   
        int i;   
        for (int offset = 0; offset < b.length; offset++) {   
            i = b[offset];   
            if(i<0) i+= 256;   
            if(i<16)   
            buf.append("0");   
            buf.append(Integer.toHexString(i));   
        }   
//      System.out.println("32位：result: " + buf.toString());//32位的加密   
//      System.out.println("16位：result: " + buf.toString().substring(8,24));//16位的加密   
  
        } catch (NoSuchAlgorithmException e) {   
        // TODO Auto-generated catch block   
        e.printStackTrace();   
        }   
        if(judgeMD == true){  
            return buf.toString();  
        }else{  
            return buf.toString().substring(8,24);  
        }  
          
    } 
    
    public static void main(String[] args)
    {
//        System.out.println(MD5Util.MD5("20121221"));
//        System.out.println(MD5Util.MD5("����"));
//        System.out.println(MD5Util.MD5("account12345678cid=12345612345678123xname张三0000000000"));
//        System.out.println(MD5Util.MD5("account12345678cid=12345612345678123xmobile13800138000name张三0000000000"));
//        System.out.println(MD5Util.MD5("resCode0000resMsg处理成功score700"));
//        
//        int max=20;
//        int min=10;
//        Random random = new Random();
//
//        int s = random.nextInt(max)%(max-min+1) + min;
//        System.out.println(s);
//        
//        for(int i = 0; i<20; i++)
//        {
//            System.out.println(random.nextInt(max)%(max-min+1) + min);
//        }
//        
//        System.out.println(MD5Util.MD5("resCode0000score750"));
//        System.out.println(MD5Util.MD5("accounttestcid=12345612345678123xname张三0000000000"));
//        System.out.println(MD5Util.MD5("accounttestcid=12345612345678123xmobile13800138000name张三0000000000"));
//        
//        System.out.println(MD5Util.MD5("account12345678cid12345612345678123xname张三0000000000"));
//    	System.out.println(MD5Util.Md("15168262336",true));
//    	 System.out.println(MD5Util.Md("15168262336",false));
//    	 System.out.println(MD5Util.MD5("王友超"));
//    	 System.out.println(MD5Util.MD5("411524199103197210"));
    	 
    	 String sign = MD5("67F126663A687B3AEB0AB38EB13C6076"+"F621D664BC7D3859D3C9BC66AEC4AEB5"+"lIYdSwCgFHcyAIgMkH6QN6AGFkDzLm3B");
    	 System.out.println("---sign----"+sign);
    	 
    }
}