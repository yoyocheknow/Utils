package com.ccx.credit.data.shared.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ObjectURLEncoder
{

    private static Logger log = LoggerFactory.getLogger(ObjectURLEncoder.class);

    private ObjectURLEncoder()
    {
    }

    public static String encode(String str)
    {
        return encode(str, "utf-8");
    }

    public static String encode(String str, String enc)
    {
        String result;
        
        if (null == str || str.equals(""))
        {
            return "";
        }
        try
        {
            result = URLEncoder.encode(str, enc);
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
        System.out.println(ObjectURLEncoder.encode("张淑迎"));
        System.out.println(ObjectURLEncoder.encode("何艳华"));
        
        
    }


}