package com.ccx.credit.data.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccx.credit.data.shared.entity.external.identity.RspIdentityDataDetail;
import com.ccx.credit.data.shared.enumeration.returncode.ResCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class which provides methods for conversion of different date formats.
 * 
 * @author Emre
 * 
 */
public final class StringUtil
{

    private static Logger log = LoggerFactory.getLogger(StringUtil.class);
    
    private static ObjectMapper mapper = new ObjectMapper();

    private StringUtil()
    {
    }

    public static String fileToString(String fileurl)
    {
        String sb = null;
        try
        {
            InputStream in = new FileInputStream(new File(fileurl));
            // FileInputStream 用于读取诸如图像数据之类的原始字节流。要读取字符流，请考虑使用 FileReader。

            String str = "";
            // size 为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "utf-8");
            System.out.println(str);
            sb = str;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return sb;

    }

    public static int stringToInt(String str)
    {
        int i = 0;

        if (str != null)
        {
            try
            {
                i = Integer.parseInt(str);
            }
            catch (NumberFormatException e)
            {
                log.error("failed to decode str: " + str);
                e.printStackTrace();
            }
        }

        return i;
    }

    public static int calRandomScore(int min, int max)
    {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static boolean isEmptyNull(String in)
    {
        if (in != null && in.length() != 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 
     * 将byte转换为一个长度为8的byte数组,数组每个值代表bit
     */

    // public static byte[] getBooleanArray(byte b)
    // {
    // byte[] array = new byte[8];
    // for (int i = 7; i >= 0; i--)
    // {
    // array[i] = (byte) (b & 1);
    // b = (byte) (b >> 1);
    // }
    // return array;
    // }

    public static byte[] getBooleanArray(byte b)
    {
        byte[] array = new byte[8];
        for (int i = 0; i < 8; i++)
        {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    /**
     * 
     * 把byte转为字符串的bit
     */

    public static String byteToBit(byte b)
    {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
                + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static boolean isSmsSucceed(String in)
    {
        if (in == null || in.isEmpty())
        {
            return false;
        }

        if (in.startsWith("-"))
        {
            return false;
        }

        return true;
    }

    public static String trimBegEndSB(String in)
    {
        if (in != null && in.length() > 1 && in.startsWith("["))
        {
            in = in.substring(1);
        }

        if (in != null && in.length() > 1 && in.endsWith("]"))
        {
            in = in.substring(0, in.length() - 1);
        }

        return in;
    }

    public static boolean compareTel(String base, String target)
    {
        if (base != null && target != null)
        {
            base = base.replace(" ", "").replaceAll("-", "");
            target = target.replace(" ", "").replaceAll("-", "");

            if (base.equals(target))
            {
                return true;
            }
        }

        return false;
    }

    public static String updateToCNBracket(String name)
    {
        String result = null;
        if (name != null)
        {
            result = name.replace("(", "（").replace(")", "）");
        }

        return result;
    }

    public static String fullWidth2halfWidth(String name)
    {
        String result = null;
            try {
                if (name != null)
                {
				result = name.replace("（", "(").replace("）", ")");
                }
			} catch (Exception e) {
				e.printStackTrace();
			}


        return result;
    }
    
    public static String updateRegorgToProvince(String regorg)
    {
        String result = null;
        if (regorg != null && regorg.length() == 6)
        {
            result = regorg.substring(0, 2) + "0000";
        }

        return result;
    }

    public static String replaceBlank(String str)
    {
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void saveAsFileWriter(String content, String fileUrl)
    {

        FileWriter fwriter = null;
        try
        {
            fwriter = new FileWriter(fileUrl);
            fwriter.write(content);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                fwriter.flush();
                fwriter.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static Map<String, String> convertMaskedCid(String cid)
    {
        Map<String, String> map = new HashMap<String, String>();
        
        return map;
    }
    
    public static String objToJsonStr(Object obj)
    {
    	String result = null;

    	if (obj == null)
    	{
    		return result;
    	}
    	    	
    	try {
			result = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public static String filterStatute(String str){
    	String statute = str;
    	
    	try {
			if(str!=null&&("0".equals(str)||"1".equals(str)||"2".equals(str)||"3".equals(str)||"...".equals(str))){
				statute = null;
			}
		} catch (Exception e) {
			statute = null;
			e.printStackTrace();
		}
    	
    	return statute;
    }
    
    public static void main(String[] args)
    {
//        System.out.println(StringUtil.calRandomScore(500, 599));
//        System.out.println(StringUtil.isEmptyNull(""));
//        System.out.println(StringUtil.isEmptyNull(null));
//        System.out.println(StringUtil.isEmptyNull("abc"));
//
//        System.out.println(StringUtil.isSmsSucceed("-4"));
//        System.out.println(StringUtil.isSmsSucceed("-10"));
//        System.out.println(StringUtil.isSmsSucceed("1362120689344"));
//
//        System.out.println(StringUtil.compareTel("02188887777", "021-8888 7777"));
//        System.out.println(StringUtil.compareTel("02188887777", " 021-8889 7777"));
//        System.out.println(StringUtil.updateToCNBracket("约克(中国)商贸有限公司"));
//        System.out.println(StringUtil.updateRegorgToProvince("110102"));
//
//        System.out.println(StringUtil.replaceBlank("just do it!"));
//        
//        System.out.println(ResCode.RC_SUCCEED.equals(ResCode.RC_SUCCEED));
        
        String sb = StringUtil.fileToString("d:/a.xml");
        
        RspIdentityDataDetail response = (RspIdentityDataDetail) ApiUtilities.unmarshal(sb,
    			RspIdentityDataDetail.class);
        
        System.out.println("response: " + response);
        
    }

}