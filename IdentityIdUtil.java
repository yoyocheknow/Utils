package com.ccx.credit.data.shared.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class which provides methods for conversion of different date formats.
 * 
 * @author Emre
 * 
 */
public final class IdentityIdUtil
{

    private static Logger log = LoggerFactory.getLogger(IdentityIdUtil.class);

    private IdentityIdUtil()
    {
    }

    public static int getSex(String id)
    {
        int mark = 0;

        if (id.length() == 18)
        {
            mark = Integer.parseInt(id.substring(16, 17));

        }
        else if (id.length() == 15)
        {
            mark = Integer.parseInt(id.substring(14, 15));
        }

        return mark % 2;
    }

    public static String getBirthday(String id)
    {
        String birth = "";

        if (id.length() == 18)
        {
            birth = id.substring(6, 14);

        }
        else if (id.length() == 15)
        {
            birth = "19" + id.substring(6, 12);
        }
        else
        {
            log.error("identity id length is incorrect.");
        }

        return birth;
    }
    
    public static String getYearOfBirthday( String id ) {
        String birth = "";

        if (id.length() == 18)
        {
            birth = id.substring(6, 10);

        }
        else if (id.length() == 15)
        {
            birth = "19" + id.substring(6, 8);
        }
        else
        {
            log.error("identity id length is incorrect.");
        }

        return birth;
    }

    public static int getAge(String id)
    {
        int age = 0;
        String birth = "";

        if (id.length() == 18)
        {
            birth = id.substring(6, 14);

        }
        else if (id.length() == 15)
        {
            birth = "19" + id.substring(6, 12);
        }
        else
        {
            log.error("identity id length is incorrect.");
            return age;
        }

        Date dayOfBirth = null;
        try
        {
            dayOfBirth = new SimpleDateFormat("yyyyMMdd").parse(birth);
        }
        catch (ParseException e)
        {
            log.error("Cannot parse birth date");
            e.printStackTrace();
        }
        Calendar cal = new GregorianCalendar();// 当前时间
        int year = cal.get(Calendar.YEAR);// year今年年份
        cal.setTime(dayOfBirth);// 当前cal为dayOfBirth出生那一日的Calendar时间
        int birthYear = cal.get(Calendar.YEAR);// 出生年份
        age = year - birthYear;

        return age;
    }

    // compare identity id
    public static boolean compareId(String base, String target)
    {
        if (target != null && base != null)
        {
            if (base.length() == 18)
            {
                if (target.length() == 18)
                {
                    if (base.substring(0, 10).equals(target.substring(0, 10))
                            && base.substring(14, 18).equals(target.substring(14, 18)))
                    {
                        return true;
                    }
                }
                else if (target.length() == 15)
                {
                    if (base.substring(0, 6).equals(target.substring(0, 6))
                            && base.substring(8, 10).equals(target.substring(6, 8))
                            && base.substring(14, 17).equals(target.substring(12, 15)))
                    {
                        return true;
                    }

                }
            }
            else if (base.length() == 15)
            {
                if (target.length() == 18)
                {
                    if (base.substring(0, 6).equals(target.substring(0, 6))
                            && base.substring(6, 8).equals(target.substring(8, 10))
                            && base.substring(12, 15).equals(target.substring(14, 17)))
                    {
                        return true;
                    }
                }
                else if (target.length() == 15)
                {
                    if (base.substring(0, 6).equals(target.substring(0, 6))
                            && base.substring(6, 8).equals(target.substring(6, 8))
                            && base.substring(12, 15).equals(target.substring(12, 15)))
                    {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    // compare identity id
    public static boolean compareCidByPattern(String base, String target)
    {
        if (target != null && base != null)
        {
            if (base.length() == 18)
            {
                if (target.length() == 18)
                {
                    if (base.substring(0, 10).equals(target.substring(0, 10))
                            && base.substring(14, 18).equals(target.substring(14, 18)))
                    {
                        return true;
                    }
                }
                else if (target.length() == 15)
                {
                    if (base.substring(0, 6).equals(target.substring(0, 6))
                            && base.substring(8, 10).equals(target.substring(6, 8))
                            && base.substring(14, 17).equals(target.substring(12, 15)))
                    {
                        return true;
                    }

                }
            }
            else if (base.length() == 15)
            {
                if (target.length() == 18)
                {
                    if (base.substring(0, 6).equals(target.substring(0, 6))
                            && base.substring(6, 8).equals(target.substring(8, 10))
                            && base.substring(12, 15).equals(target.substring(14, 17)))
                    {
                        return true;
                    }
                }
                else if (target.length() == 15)
                {
                    if (base.substring(0, 6).equals(target.substring(0, 6))
                            && base.substring(6, 8).equals(target.substring(6, 8))
                            && base.substring(12, 15).equals(target.substring(12, 15)))
                    {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    // compare identity id by rules
    public static boolean compareCidByRules(String base, String target)
    {
        log.info("base: " + base);
        log.info("target: " + target);
        
        try
        {
            if (target != null && base != null && base.length() == target.length())
            {
                return compareCidPattern(base, convertCidToPattern(target));       
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.error("failed to compare cid by pattern");
        }

        return false;
    }

    // compare identity id by pattern
    public static boolean compareCidPattern(String base, String pattern)
    {
        try
        {
            if (pattern != null && base != null)
            {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(base);
                while (m.find())
                {
                    return true;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.error("failed to compare cid by pattern");
        }

        return false;
    }

    // convert cid to pattern format
    public static String convertCidToPattern(String base)
    {
        String pattern = null;
        if (base == null)
        {
            return null;
        }
        
        try
        {
            String patternBase = "*";
            String patternEle = "[0-9xX]{1}";

            if (base != null)
            {
                pattern = base.replace(patternBase, patternEle);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.error("failed to convert to pattern");
            pattern = base;
        }

        return pattern;
    }

    public static String trimId(String id)
    {
        String result = "不详";
        // original plan text id format
        // Pattern p15 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        // Pattern p18 =
        // Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
        Pattern p15 = Pattern
                .compile("^[1-9]\\d{7}((0\\d)|(1[0-2]|\\*{2}))(([0|1|2]\\d)|3[0-1]|\\*{2})\\d{3}$");
        Pattern p18 = Pattern
                .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2])|\\*{2})(([0|1|2]\\d)|3[0-1]|\\*{2})\\d{3}[0-9a-zA-Z]$");

        if (id != null)
        {
            if (id.length() == 18)
            {
                Matcher m = p18.matcher(id);
                if (m.matches())
                {
                    result = id;
                }
            }
            else if (id.length() == 15)
            {
                Matcher m = p15.matcher(id);
                if (m.matches())
                {
                    result = id;
                }
            }
        }

        return result;

    }

    // compare identity id
    public static boolean compareCode(String base, String target)
    {
        return base.equals(target);
    }

    // check cid
    public static boolean isCid(String cid)
    {
        if (cid == null || (cid.length() != 18 && cid.length() != 15))
        {
            return false;
        }

        return true;
    }

    public static String getBirthdayToStar(String id)
    {
        String idStar = "";

        if (id.length() == 18)
        {
            idStar = id.substring(0, 10) + "****" + id.substring(14);

        }
        else if (id.length() == 15)
        {
            idStar = id.substring(0, 8) + "****" + id.substring(12);
        }
        else
        {
            log.error("identity id length is incorrect.");
        }

        return idStar;
    }

    public static String getBirthdayAllToStar(String id)
    {
        String idStar = "";

        if (id.length() == 18)
        {
            idStar = id.substring(0, 6) + "********" + id.substring(14);

        }
        else if (id.length() == 15)
        {
            idStar = id.substring(0, 6) + "******" + id.substring(12);
        }
        else
        {
            log.error("identity id length is incorrect.");
        }

        return idStar;
    }

    public static String getZodiac(int month, int day)
    {
        final int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
        final String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
                "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };

        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];

    }

    public static String getZodiac(String cid)
    {
        String birth = getBirthday(cid);
        String zodiac = null;

        if (birth != null)
        {
            try
            {
                String monthc = birth.substring(4, 6);
                String dayc = birth.substring(6, 8);

                Integer month = Integer.parseInt(monthc);
                Integer day = Integer.parseInt(dayc);

                System.out.println(month);
                System.out.println(day);

                zodiac = getZodiac(month, day);
            }
            catch (Exception e)
            {
                log.error("failed to parse zodiac");
                e.printStackTrace();
            }
        }

        return zodiac;
    }

    public static boolean compareToId(String base, String target)
    {
        if (target != null && base != null)
        {
            if (base.length() == 18 && target.length() == 18){            	
            	if(base.contains("x"))
            	{
	                if (base.substring(0, 9).equals(target.substring(0, 9))&&base.substring(14, 17).equals(target.substring(14, 17))&&target.substring(17, 18).equals("X"))
	                {
	                    return true;
	                }
	            }
	            else if (base.contains("X"))
	            {
	            	if (base.substring(0, 9).equals(target.substring(0, 9))&&base.substring(14, 17).equals(target.substring(14, 17))&&target.substring(17, 18).equals("x"))
	                {
	                    return true;
	                }
	            }
            }
        }

        return false;
    }
    
    public static boolean compareOrganCode(String base, String target, String identityId)
    {
        if (target != null && base != null)
        {
            if (base.length() == 10)
            {
            	if(base.contains("-")){
	                if (target.length() == 10)
	                {
	                    if (target.contains("-")&&(base == target || base.equals(target)))
	                    {
	                        return true;
	                    }
	                }
	                else if (target.length() == 9)
	                {
	                    if (base.substring(0, 7).equals(target.substring(0, 7))
	                            && base.substring(9, 9).equals(target.substring(8, 8)))
	                    {
	                        return true;
	                    }
	                }
            	}
            }
            else if (base.length() == 9)
            {
                if (base == identityId || base.equals(identityId))
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    public static void main(String[] args)
    {
        // 622924195801010030
        // 3307221984****9017
        /*
         * System.out.println(IdentityIdUtil.compareId("622924195801010030", "3307221984****9017"));
         System.out.println(IdentityIdUtil.trimId("622924195801010030"));
         System.out.println(IdentityIdUtil.trimId("1234561980****123x"));
         * System.out.println(IdentityIdUtil.trimId("adsfsdf"));
         * 
         * System.out.println(IdentityIdUtil.trimId("622924580101003"));
         * System.out.println(IdentityIdUtil.trimId("12345680****123"));
         * System.out.println(IdentityIdUtil.trimId("adsfsdf"));
         * 
         * System.out.println(IdentityIdUtil.getAge("132201198911280619"));
         */
//        String id18 = "622924195801010030";
//        String id15 = "130503670401001";
//        System.out.println(IdentityIdUtil.getBirthdayToStar(id18));
//        System.out.println(IdentityIdUtil.getBirthdayToStar(id15));
//
//        System.out.println(IdentityIdUtil.getBirthdayAllToStar(id18));
//        System.out.println(IdentityIdUtil.getBirthdayAllToStar(id15));
//
//        System.out.println(getBirthday("622924195801010030"));
//        System.out.println(getZodiac("622924195812020030"));
//
//        System.out.println(convertCidToPattern("2***2319740301****"));
//        System.out
//                .println(compareCidPattern("212323197403011234", convertCidToPattern("2***2319740301****")));
//
//        
//        System.out.println(getSex("132401197912030650"));
//        String id="1";
//        String idStar=id.substring(id.length()-2,id.length()-1);
//        System.out.println(idStar);
//        int a=0;int b=3;
//        System.out.println(a % b);
    	  String base = "21142119921120162X";
    	  String target = "2114211992****162x";
    	  
//    	  String a = target.substring(17, 18);
//    	  System.out.println("----"+a);
    	  
    	  
//    	  if (base == null || "".equals(base) ||IdentityIdUtil.compareId(base, target)|| IdentityIdUtil.compareToId(base, target)){
//    		  target=IdentityIdUtil.getBirthdayToStar(target);
//    		  System.out.println("--true--"+target);
//    	  }else{
//    		  System.out.println("--false--"+false);
//    	  }
    	  System.out.println("--target长度--"+target.length());
    	  if (base == null || "".equals(base) ||IdentityIdUtil.compareId(base, target)|| IdentityIdUtil.compareToId(base, target)|| IdentityIdUtil.compareCidByRules(base, target)){
    		  target=IdentityIdUtil.getBirthdayToStar(target);
    		  System.out.println("--true--"+target);
    	  }else{
    		  System.out.println("--false--"+false);
    	  }
        
    }
    
    public static String getMatch(String content, String sp_regex) {
		return getMatch(content, sp_regex, true);
	}
	
//	正则使用方法
	protected static String getMatch(String content, String sp_regex, boolean case_insensitive) {
		Pattern[] patterns = createPattern(sp_regex, case_insensitive);
		Matcher m = patterns[0].matcher(content);
		if (m.find()) {
			String _match = m.group();
			if (patterns.length > 1) {
				_match = patterns[1].matcher(_match).replaceFirst("");
				_match = patterns[2].matcher(_match).replaceFirst("");
			}
			return _match;
		}
		return null;
	}
	
	/**
	 * @param content
	 *            用于匹配的正文
	 * @param sp_regex
	 *            用于匹配的（特殊）正则表达式
	 * @return 匹配结果列表
	 */
	protected static List<String> getMatchList(String content, String sp_regex) {
		return getMatchList(content, sp_regex, false, 65536);
	}

	protected static List<String> getMatchList(String content, String sp_regex, boolean case_insensitive, int match_num) {
		List<String> match = new ArrayList<String>();
		try {
			Pattern[] patterns = createPattern(sp_regex, case_insensitive);
			Matcher m = patterns[0].matcher(content);
			while (m.find()) {
				String _match = m.group();
				if (patterns.length > 1) {
					_match = patterns[1].matcher(_match).replaceFirst("");
					_match = patterns[2].matcher(_match).replaceFirst("");
				}
				match.add(_match);
				if (match.size() >= match_num) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return match;
	}
	
	private static final String SP_MARK = "(*)";

	private static Pattern[] createPattern(String sp_regex, boolean case_insensitive) {
		Pattern[] patterns = null;
		int pos = -1;
		if ((pos = sp_regex.indexOf(SP_MARK)) > -1) {
			patterns = new Pattern[3];
			String subRegex_0 = sp_regex.substring(0, pos);
			String subRegex_1 = sp_regex.substring(pos + SP_MARK.length());
			if (case_insensitive) {
				patterns[0] = Pattern.compile(subRegex_0 + ".*?" + subRegex_1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				patterns[1] = Pattern.compile(subRegex_0, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				patterns[2] = Pattern.compile(subRegex_1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			} else {
				patterns[0] = Pattern.compile(subRegex_0 + ".*?" + subRegex_1, Pattern.DOTALL);
				patterns[1] = Pattern.compile(subRegex_0, Pattern.DOTALL);
				patterns[2] = Pattern.compile(subRegex_1, Pattern.DOTALL);
			}
		} else {
			patterns = new Pattern[1];
			if (case_insensitive) {
				patterns[0] = Pattern.compile(sp_regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			} else {
				patterns[0] = Pattern.compile(sp_regex, Pattern.DOTALL);
			}
		}
		return patterns;
	}
	
	public static String verifyPattern(String str){
		String identityId = null;
		String temp = null;
		Pattern pattern1 = Pattern.compile("([0-9A-Z]){8}[0-9|X]");
		Pattern pattern2 = Pattern.compile("([0-9A-Z]){8}-[0-9|X]");
		Matcher m1 = null;
		Matcher m2 = null;
		if(str.length() == 9 || str.length() == 10){
			m1 = pattern1.matcher(str);
			m2 = pattern2.matcher(str);
			log.info("m1 ----1794-- "+m1);
			log.info("m2 ----1795-- "+m2);
			if(m1 != null||m2 != null){
				if(m1.find() || m2.find()){
					log.info("scid ----1797-- "+str);
					temp = str.replace("-", "");
					identityId = IdentityIdUtil.getMatch(temp,"([0-9A-Z]){8}[0-9|X]");
					log.info("identityId ----629-- "+identityId);
				}
			}
		}
		return identityId;
	}
    
}