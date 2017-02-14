package com.ccx.credit.data.shared.util;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccx.credit.data.shared.annotation.CcxcAttribute;
import com.ccx.credit.data.shared.annotation.CcxcEntity;
import com.ccx.credit.data.shared.entity.external.yuansu.ent.Alter;
import com.ccx.credit.data.shared.entity.external.yuansu.ent.Basic;
import com.ccx.credit.data.shared.entity.external.yuansu.ent.EntInv;
import com.ccx.credit.data.shared.entity.external.yuansu.ent.Person;
import com.ccx.credit.data.shared.entity.external.yuansu.ent.RspEntData;
import com.ccx.credit.data.shared.entity.external.yuansu.ent.Shareholder;
import com.ccx.credit.data.shared.entity.external.yuansu.person.RspPersonData;

public class Object2Excel
{
    private static Logger log = LoggerFactory.getLogger(Object2Excel.class);

    public void genExcel(String fileName, List list, Class clazz)
        throws Exception
    {
        log.debug("class: " + clazz);
        // 获取总列数
        int CountColumnNum = list.size();
        CountColumnNum = 15;

        // 创建Excel文档
        HSSFWorkbook hwb = new HSSFWorkbook();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String sheetName = sdf.format(new Date());
        if (clazz.isAnnotationPresent(CcxcEntity.class))
        {
            CcxcEntity refObject = (CcxcEntity) clazz.getAnnotation(CcxcEntity.class);
            log.debug("CcxcEntity: " + refObject.name());
            sheetName = refObject.name();
        }

        // sheet 对应一个工作页
        HSSFSheet sheet = hwb.createSheet(sheetName);
        HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始

        Field[] fields = clazz.getDeclaredFields();

        log.debug("field size: " + fields.length);

        HSSFCell[] firstcell = new HSSFCell[fields.length];

        int j = 0;
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(CcxcAttribute.class))
            {
                field.setAccessible(true);
                log.debug("field name: " + field.getName());
                // log.debug("field value: " + field.get(memEntity));

                CcxcAttribute refObject = field.getAnnotation(CcxcAttribute.class);
                log.debug(field + ": " + refObject.name());

                firstcell[j] = firstrow.createCell(j);
                firstcell[j].setCellValue(new HSSFRichTextString(refObject.name()));
                j++;
            }
        }

        int rowCount = 1;
        Object[] objs = list.toArray();
        for (Object object : objs)
        {
            log.debug("object: " + object);

            HSSFRow row = sheet.createRow(rowCount); // 下标为0的行开始
            HSSFCell[] cell = new HSSFCell[fields.length];

            int i = 0;
            for (Field field : fields)
            {
                if (field.isAnnotationPresent(CcxcAttribute.class))
                {
                    field.setAccessible(true);
                    log.debug("field name: " + field.getName());
                    log.debug("field value: " + field.get(object));

                    CcxcAttribute refObject = field.getAnnotation(CcxcAttribute.class);
                    log.debug(field + ": " + refObject.name());

                    cell[i] = row.createCell(i);
                    cell[i].setCellValue(new HSSFRichTextString(field.get(object) == null ? null : field.get(
                            object).toString()));
                    i++;
                }
            }

            rowCount++;

        }

        // 创建文件输出流，准备输出电子表格
        String excelFile = fileName;
        FileOutputStream fos = new FileOutputStream(excelFile);

        hwb.write(fos);
        hwb.close();
        fos.close();

        System.out.println("数据导出成功");
    }

    public HSSFWorkbook addWorksheet(HSSFWorkbook hwb, List list, Class clazz)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String date = sdf.format(new Date());
        String sheetName = date + RandomUtil.calRandomScore(9999, 1000);

        if (clazz.isAnnotationPresent(CcxcEntity.class))
        {
            CcxcEntity refObject = (CcxcEntity) clazz.getAnnotation(CcxcEntity.class);
            //log.debug("CcxcEntity: " + refObject.name());
            sheetName = refObject.name();
        }

        // sheet 对应一个工作页
        HSSFSheet sheet = hwb.createSheet(sheetName);
        HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始

        Field[] fields = clazz.getDeclaredFields();

        //log.debug("field size: " + fields.length);

        HSSFCell[] firstcell = new HSSFCell[fields.length];

        int j = 0;
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(CcxcAttribute.class))
            {
                field.setAccessible(true);
                //log.debug("field name: " + field.getName());
                // log.debug("field value: " + field.get(memEntity));

                CcxcAttribute refObject = field.getAnnotation(CcxcAttribute.class);
                //log.debug(field + ": " + refObject.name());

                firstcell[j] = firstrow.createCell(j);
                firstcell[j].setCellValue(new HSSFRichTextString(refObject.name()));
                j++;
            }
        }

        int rowCount = 1;
        Object[] objs = list.toArray();
        for (Object object : objs)
        {
            //log.debug("object: " + object);

            HSSFRow row = sheet.createRow(rowCount); // 下标为0的行开始
            HSSFCell[] cell = new HSSFCell[fields.length];

            int i = 0;
            for (Field field : fields)
            {
                if (field.isAnnotationPresent(CcxcAttribute.class))
                {
                    field.setAccessible(true);
                    //log.debug("field name: " + field.getName());
                    //log.debug("field value: " + field.get(object));

                    CcxcAttribute refObject = field.getAnnotation(CcxcAttribute.class);
                    //log.debug(field + ": " + refObject.name());

                    try
                    {
                        cell[i] = row.createCell(i);
                        cell[i].setCellValue(new HSSFRichTextString(field.get(object) == null ? null : field.get(
                                object).toString()));
                        i++;
                    }
                    catch(Exception e)
                    {
                    	log.error("set cell content exception: e" + e);
                    	String str = field.get(object).toString();
                    	log.debug("error str: " + str);
                    	if (str != null && str.length() > 32767)
                    	{
                    		String str1 = str.substring(0, 32767);
                    		String str2 = str.substring(32767);
                    		
                            cell[i] = row.createCell(i);
                            cell[i].setCellValue(new HSSFRichTextString(str1));
                            i++;
                            
                            cell[i] = row.createCell(i);
                            cell[i].setCellValue(new HSSFRichTextString(str2));
                            i++;                           
                    	}
                    }
                }
            }

            rowCount++;

        }

        return hwb;

    }

}
