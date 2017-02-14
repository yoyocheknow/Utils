package com.ccx.credit.data.shared.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class which provides methods for conversion of different date formats.
 * 
 * @author Emre
 * 
 */
public final class DateUtil
{

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmm");

    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");

    public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");

    public static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
    
    public static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateUtil()
    {
    }

    // 2013年04月03日
    public static String getDateFromCNDate(String cndate)
    {
        String date = null;

        if (cndate != null)
        {
            date = cndate.replace("年", "").replace("月", "").replace("日", "");
        }

        return date;
    }

    // 2015-02-08 13:29:15
    public static String getDateFromFullDate(String fulldate)
    {
        String date = null;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        if (fulldate != null)
        {
            try
            {
                date = outputFormat.format(format.parse(fulldate));
            }
            catch (ParseException e)
            {
                log.error("Cannot parse date from data source.");
                e.printStackTrace();
            }
        }

        return date;
    }

    // 2015-02-08
    public static String getDateFromHalfDate(String halfdate)
    {
        String date = null;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        if (halfdate != null && !"".equals(halfdate))
        {
            try
            {
                date = outputFormat.format(format.parse(halfdate));
            }
            catch (ParseException e)
            {
                log.error("Cannot parse date from data source.");
                e.printStackTrace();
            }
        }

        return date;
    }

    // 20150208 --> 2015年2月8日
    public static String getCNDateFromDate1(String inputDate)
    {
        String date = null;

        if (inputDate != null)
        {
            try
            {
                date = sdf2.format(sdf3.parse(inputDate));
            }
            catch (ParseException e)
            {
                log.error("Cannot parse date from data source.");
                e.printStackTrace();
            }
        }

        return date;
    }

    // 2015-02-08 --> 2015年2月8日
    public static String getCNDateFromDate2(String inputDate)
    {
        String date = null;

        if (inputDate != null)
        {
            try
            {
                date = sdf2.format(sdf4.parse(inputDate));
            }
            catch (ParseException e)
            {
                log.error("Cannot parse date from data source.");
                e.printStackTrace();
            }
        }

        return date;
    }

    /*
     * 
     * return true if target - base > days
     */
    public static boolean compareDaysInterval(Timestamp base, Timestamp target, int days)
    {
        if (base != null && target != null)
        {
            long diff = target.getTime() - base.getTime();
            long realDays = diff / (1000 * 60 * 60 * 24);
            System.out.println(realDays);
            return realDays > days;
        }

        return false;
    }

    /*
     * 
     * return true if target is after base sDate
     */
    public static boolean isDateAfter(String sDateStr, Date target)
    {
        if (target == null)
        {
            return false;
        }

        Date sDate = null;
        try
        {
            sDate = sdf1.parse(sDateStr);
        }
        catch (ParseException e)
        {
            log.error("failed to parse sDate");
            e.printStackTrace();
            return false;
        }

        return target.after(sDate);
    }
    
    public static boolean isDateAfter ( String base, SimpleDateFormat sdf1 , String targer, SimpleDateFormat sdf2 ) {
        boolean result = false;
        
        Date dateBase = null;
        Date dateTarger = null;
        /*log.debug("base value is: " + base);
        log.debug("SimpleDateFormat sdf1: " + sdf1);
        log.debug("targer value is: " + targer);
        log.debug("SimpleDateFormat sdf2: " + sdf2);*/
        if ( base != null && !base.trim().equals("") && sdf1 != null ) {
            try
            {
                /*log.debug("base value is :" + base);
                log.debug("SimpleDateFormat sdf1: " + sdf1);*/
                dateBase = sdf1.parse(base.trim());
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                log.info("failed to parse base",e);
            }
        }
        
        if ( targer != null && !targer.trim().equals("") && sdf2 != null ) {
            try
            {
                /*log.debug("targer value is: " + targer);
                log.debug("SimpleDateFormat sdf2: " + sdf2);*/
                dateTarger = sdf2.parse(targer.trim());
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                log.info("failed to parse targer", e);
            }
        }
        
        if ( dateBase != null && dateTarger != null ) {
            result = dateTarger.after(dateBase);
        }
        return result;
    }
    
    /*
     * 
     * return true if target is after base sDate input sDateStr format: yyyyMMddHHmm input target format:
     * yyyy-MM-dd
     */
    public static boolean isDateAfter(String sDateStr, String target)
    {
        Date targetDate = null;
        try
        {
            targetDate = sdf4.parse(target);
        }
        catch (ParseException e)
        {
            log.error("failed to parse target date");
            e.printStackTrace();
        }

        Date sDate = null;
        try
        {
            sDate = sdf1.parse(sDateStr);
        }
        catch (ParseException e)
        {
            log.error("failed to parse sDate");
            e.printStackTrace();
        }

        if (targetDate != null && sDate != null && targetDate.after(sDate))
        {
            return true;
        }

        return false;
    }

    /*
     * 计算两个日期之间相差的月数
     */
    public static int numberOfMonthBetweenTheDate(Date startDate, Date endDate)
    {
        if (startDate == null)
            return 0;
        if (endDate == null)
            endDate = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int sy = startCalendar.get(Calendar.YEAR);
        int sm = startCalendar.get(Calendar.MONTH);
        int ey = endCalendar.get(Calendar.YEAR);
        int em = endCalendar.get(Calendar.MONTH);
        int numOfY = ey - sy;
        int numOfM = em - sm;
        int val = numOfY * 12 + numOfM;
        return val;
    }
    
    /**
     * startDateStr:起始日期
     * endDateStr:截止日期
     * 注：起始日期要早于截止日期
     * 返回结果为-1代表运行过程异常 
     */
    public static int numberOfMonthBetweenTheDate(String startDateStr, String endDateStr, String formate)
    {
        if (startDateStr == null) { 
            return -1;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        try
        {
            Date startDate = null;
            startDate = sdf.parse(startDateStr.trim());
            Date endDate = null;
            if (endDateStr == null) {
                endDate = new Date();
            } else {
                endDate = sdf.parse(endDateStr.trim());  
            }
                
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int sy = startCalendar.get(Calendar.YEAR);
            int sm = startCalendar.get(Calendar.MONTH);
            int ey = endCalendar.get(Calendar.YEAR);
            int em = endCalendar.get(Calendar.MONTH);
            int numOfY = ey - sy;
            int numOfM = em - sm;
            int val = numOfY * 12 + numOfM;
            return val;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        
    }
    
    /*
     * 计算两个日期之间相差的天数
     */
    public static long numberOfDaysBetweenTheDate(Date startDate, Date endDate)
    {
        if (startDate == null || endDate == null)
            return 0;
        long sd = startDate.getTime();
        long ed = endDate.getTime();
        long b = ed - sd;
        long val = b/(24L*3600L*1000L);
        return val;
    }
    
    
    /*
     * 计算两个日期之间相差的小时数
     */
    public static long numberOfHoursBetweenTheDate(Date startDate, Date endDate)
    {
        if (startDate == null || endDate == null)
            return 0;
        long sd = startDate.getTime();
        long ed = endDate.getTime();
        long b = ed - sd;
        long val = b/(3600L*1000L);
        return val;
    }
    
    /*
     * 计算两个日期的时间差
     * 返回时间差(毫秒级)
     */
    public static long getDifferenceOfDate(Date startDate, Date endDate ) {
        if ( startDate == null ) {
            return 0;
        }
        if ( endDate == null ) {
            endDate = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            System.out.println(sdf.format(startDate));
            System.out.println(sdf.format(endDate));
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long sd = startDate.getTime();
        long ed = endDate.getTime();
        long value = ed - sd;
        return value;
    }
    
    public static Date getDateFromDateString(String dateStr)
    {
        Date date = null;

        if (dateStr != null)
        {
            try 
            {
				date = sdf5.parse(dateStr);
			} 
            catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return date;
    }

    public static String getDateStringFromDate(Date date)
    {
        String dateStr = null;

        if (date != null)
        {
        	dateStr = sdf5.format(date);
        }

        return dateStr;
    }

    public static void main(String[] args)
        throws ParseException
    {
    	
    	TreeMap<String, String> treeMap = new TreeMap<String, String>();
    	treeMap.put("1", "a");
    	treeMap.put("1", "b");
    	treeMap.put("2", "c");
    	treeMap.put("2", "d");
    	System.out.println(treeMap);
    	
    	
    	
    	
        // System.out.println(DateUtil.getDateFromCNDate("2013年04月03日"));
        // System.out.println(DateUtil.getDateFromFullDate("2015-02-08 13:29:15"));
        // System.out.println(DateUtil.getDateFromHalfDate("2015-02-08"));
        //
        // DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //
        // Date d1 = df.parse("2004-03-26 13:31:40");
        // Date d2 = df.parse("2004-04-27 11:30:24");
        //
        // System.out.println(DateUtil.compareDaysInterval(new Timestamp(d1.getTime()),
        // new Timestamp(d2.getTime()), 30));

    	  float c =(float)65/20;
    	  System.out.println(c);
    	  int d = (int) Math.ceil(c);
    	  System.out.println(d);
    

    	  
    	  String aa="2015年06月01日";
    	  sdf3.format(sdf2.parse(aa));
    	  System.out.println("time： " + sdf3.format(sdf2.parse(aa)));
    	
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = format2.parse("2016-04-6 10:54:36");
        String startstr=format2.format(start);
        Timestamp.valueOf(startstr);   
        Date nowTime = new Date();
        int a = (int) numberOfHoursBetweenTheDate(start,nowTime);
        //System.out.println("nowTime： " + nowTime);
        //System.out.println("相差数： " + a);
        Timestamp target = new Timestamp(System.currentTimeMillis());
        System.out.println(target);
        System.out.println(Timestamp.valueOf(startstr));
        boolean tureorflase=compareDaysInterval(Timestamp.valueOf(startstr),target,7);
        if(tureorflase){
        	 System.out.println("1");//>=7  true
        }
       
        String id="37030419731****1627";
        if (id.length() == 19)
          {
        String idStar = id.substring(0, 10) + id.substring(11);
        System.out.println("idStar::"+idStar);
          }
      
        System.out.println(DateUtil.getDateFromDateString("2015-05-03 17:14:09"));

    /*    System.out.println(getCNDateFromDate2("2015-01-01"));*/
    	
//    	 System.out.println(getDateFromCNDate("2015年07月09日").toString());
//    	 String publishTime="2015年07月09日";
//    	 if(publishTime != null && DateUtil.isDateAfter("201507010000", sdf3.parse(DateUtil.getDateFromCNDate(publishTime)))){
//    		 System.out.println("1"); 
//    	 }else{
//    		 System.out.println("2"); 
//    	 }
    	 //System.out.println(DateUtil.isDateAfter("201507010000", sdf3.parse(getDateFromCNDate("2015年06月09日"))));
    	/* Timestamp noticeTime = null;
    	 Timestamp d = new Timestamp(System.currentTimeMillis()); 
    	 String noticeTimeS = sdf4.format(d);
    	 System.out.println(noticeTimeS); 

        Date d1 = new Date();
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d2 = format2.parse("2015-10-08 13:29:15");
        long l = numberOfDaysBetweenTheDate(d1,d2);
        System.out.println(l);
        System.out.println(DateUtil.getDateFromHalfDate("2015-10-01"));*/
    }

}