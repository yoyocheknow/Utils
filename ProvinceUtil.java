package com.ccx.credit.data.shared.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class which provides methods for conversion of different date formats.
 * 
 * @author Emre
 * 
 */
public final class ProvinceUtil
{

    private static Logger log = LoggerFactory.getLogger(ProvinceUtil.class);

    private ProvinceUtil()
    {
    }

    public static String filterProvince(String in)
    {
        String out = in;

        if (in != null)
        {
            if (in.length() > 2)
            {
                out = in.replace("省", "").replace("市", "").replace("县", "").replace("区", "");
            }
        }

        return out;
    }

    public static void main(String[] args)
    {

        System.out.println(ProvinceUtil.filterProvince("北京市"));

    }

}