package com.ccx.credit.data.shared.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileUtil
{
    private static final int MOBILE_TELE_NUM = 1;
    private static final int MOBILE_UNIC_NUM = 2;
    private static final int MOBILE_YIDO_NUM = 3;
    private static final int UNKNOE = 0;
    private static final int FORMAT_ERROR_1 = -1;//手机号格式错误
    private static final int FORMAT_ERROR_2 = -2;//手机号格式错误
    
    
    //电信中国电信手机号码开头数字 133、1349、153、180、181、189
    //联通中国联通手机号码开头数字 130、131、132、145、155、156、185、186
    //移动中国移动手机号码开头数字 1340-1348、135、136、137、138、139、147、150、151、152、157、158、159、182、183、184、187、188　
    private static List<String> TELE_LIST = new ArrayList<String>();
    private static List<String> UNIC_LIST = new ArrayList<String>();
    private static List<String> YIDO_LIST = new ArrayList<String>();
    
    static {
        //电信
        TELE_LIST.add("133");
        TELE_LIST.add("153");
        TELE_LIST.add("180");
        TELE_LIST.add("181");
        TELE_LIST.add("189");
        
        TELE_LIST.add("177");
        
        //联通
        UNIC_LIST.add("130");
        UNIC_LIST.add("131");
        UNIC_LIST.add("132");
        UNIC_LIST.add("145");
        UNIC_LIST.add("155");
        
        UNIC_LIST.add("156");
        UNIC_LIST.add("185");
        UNIC_LIST.add("186");
        UNIC_LIST.add("176");
        
        //移动
        YIDO_LIST.add("135");
        YIDO_LIST.add("136");
        YIDO_LIST.add("137");
        YIDO_LIST.add("138");
        YIDO_LIST.add("139");
        
        YIDO_LIST.add("147");
        YIDO_LIST.add("150");
        YIDO_LIST.add("151");
        YIDO_LIST.add("152");
        YIDO_LIST.add("157");
        
        YIDO_LIST.add("158");
        YIDO_LIST.add("159");
        YIDO_LIST.add("182");
        YIDO_LIST.add("183");
        YIDO_LIST.add("184");
        
        YIDO_LIST.add("187");
        YIDO_LIST.add("188");
        YIDO_LIST.add("178");
    }
    
    public static int getMobileType ( String mobile ) {
        if ( mobile == null || mobile.trim().length() != 11 ) {
            return FORMAT_ERROR_1;
        }
        
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile.trim());
        if (!m.matches()){
            return FORMAT_ERROR_2;
        }
        
        String pre3Num = mobile.trim().substring(0,3);
        if ( "134".equals(pre3Num) ) {
            String pre4Num = mobile.trim().substring(0,4);
            if ( "1349".equals(pre4Num) ) {
                return MOBILE_TELE_NUM;
            } else {
                return MOBILE_YIDO_NUM;
            }
        } if ( "170".equals(pre3Num) ) {
            String pre4Num = mobile.trim().substring(0,4);
            if ( "1700".equals(pre4Num) ) {
                return MOBILE_TELE_NUM;
            } else if ( "1705".equals(pre4Num) ) {
                return MOBILE_YIDO_NUM;
            } else if ( "1709".equals(pre4Num) ) {
                return MOBILE_UNIC_NUM;
            }
        } else {
            if ( TELE_LIST.contains(pre3Num) ) {
                return MOBILE_TELE_NUM;
            } else if (UNIC_LIST.contains(pre3Num)) {
                return MOBILE_UNIC_NUM;
            } else if (YIDO_LIST.contains(pre3Num)) {
                return MOBILE_YIDO_NUM;
            }
        }
        
        return UNKNOE;
    }
    
    /**
     * 校验手机号码 
     */
    public static boolean mobileValid ( String mobile ) {
        boolean result = false;
        
        if ( mobile == null || mobile.trim().equals("") ) {
            return result;
        } else {
            mobile = mobile.trim();
        }
        
//        String regex = "^1[3|4|5|7|8][0-9]\\d{8}$";
        String regex ="^((\\+86)|(86))?[1][3456789][0-9]{9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        result = m.matches();
        
        return result;
    }
    
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
//        System.out.println(getMobileType("13811280014"));
        
        System.out.println(mobileValid("18513850741"));
        
    }

}
