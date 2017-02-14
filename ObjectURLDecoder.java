package com.ccx.credit.data.shared.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ObjectURLDecoder
{

    private static Logger log = LoggerFactory.getLogger(ObjectURLDecoder.class);

    private ObjectURLDecoder()
    {
    }

    public static String decode(String str)
    {
        return decode(str, "utf-8");
    }


    public static String decode(String str, String enc)
    {
        String result;
        
        if (null == str || str.equals(""))
        {
            return "";
        }
        try
        {
            result = URLDecoder.decode(str, enc);
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            log.error("Failed to URLEncode, " + e.getMessage());
            result = "";
        }

        return result;

    }
    
    public static void main(String[] args)
    {
        
        System.out.println(ObjectURLDecoder.decode("%E7%94%B3%E5%B7%A7%E7%8E%B2"));
    }

}