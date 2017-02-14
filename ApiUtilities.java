package com.ccx.credit.data.shared.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class which provides methods for conversion of different date formats.
 * 
 * @author Emre
 * 
 */
public final class ApiUtilities
{

    private static Logger log = LoggerFactory.getLogger(ApiUtilities.class);

    private ApiUtilities()
    {
    }

    /**
     * Converts a XMLGregorianCalendar object to a Date object.
     * 
     * @param xgc XMLGregorianCalendar object, typically representing a Date in an API object.
     * @return Date object.
     */
    public static Date convertXMLGregorianCalendarToDate(final XMLGregorianCalendar xgc)
    {
        Date dt = null;
        if (xgc != null)
        {
            dt = xgc.toGregorianCalendar().getTime();
        }
        return dt;
    }

    /**
     * Converts a Date object to an XMLGregorianCalendar object.
     * 
     * @param date Date object.
     * @return XMLGregorianCalendar object, typically representing a Date in an API object.
     */
    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(final Date date)
    {
        XMLGregorianCalendar xgc = null;
        if (date != null)
        {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            try
            {
                xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            }
            catch (DatatypeConfigurationException dce)
            {
                log.error("convertDateToXMLGregorianCalendar exception: " + dce);
            }
        }
        return xgc;
    }

    /**
     * Converts a String object to an XMLGregorianCalendar object.
     * 
     * @param str String object, representing a date in xs:dateTime format.
     * @return XMLGregorianCalendar object, typically representing a Date in an API object.
     */
    public static XMLGregorianCalendar convertStringToXMLGregorianCalendar(final String str)
    {
        XMLGregorianCalendar xge = null;
        if (str != null)
        {
            try
            {
                xge = DatatypeFactory.newInstance().newXMLGregorianCalendar(str);
            }
            catch (IllegalArgumentException e)
            {
                log.error("convertStringToXMLGregorianCalendar exception: " + e);
            }
            catch (DatatypeConfigurationException dce)
            {
                log.error("convertStringToXMLGregorianCalendar exception: " + dce);
            }
        }
        return xge;
    }

    /**
     * Returns the XML obtained after marshaling the java object.
     * 
     * @param object allowed object is {@link Object}
     * @return possible object is {@link String}
     */
    public static Class< ? >[] buildExtraContextClasses(final Object obj)
    {

        List<Class< ? >> extraContextClasses = new ArrayList<Class< ? >>();
        /*
         * if (obj instanceof CdrEvent) { CdrEvent e = (CdrEvent) obj;
         * extraContextClasses.add(e.getClass()); if (null != e.getEventData() && null !=
         * e.getEventData().getAny()) { extraContextClasses.add(e.getEventData().getAny().getClass()); } }
         * else if (obj instanceof CdrEventList) { CdrEventList l = (CdrEventList) obj;
         * extraContextClasses.add(l.getClass()); if (l.getCdrEventList().size() > 0) {
         * extraContextClasses.add(l.getCdrEventList().get(0).getClass()); } for (CdrEvent e :
         * l.getCdrEventList()) { if (null != e.getEventData() && null != e.getEventData().getAny()) {
         * extraContextClasses.add(e.getEventData().getAny().getClass()); } } } else {
         * extraContextClasses.add(obj.getClass()); }
         */

        return extraContextClasses.toArray(new Class< ? >[extraContextClasses.size()]);
    }

    /**
     * Returns the XML obtained after marshaling the java object.
     * 
     * @param object allowed object is {@link Object}
     * @return possible object is {@link String}
     */
    public static String marshall(final Object object)
    {

        String str = null;
        if (null != object)
        {
            try
            {

                JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());

                /*
                 * JAXBContext jaxbContext = JAXBContext.newInstance( buildExtraContextClasses(object));
                 */

                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                StringWriter writer = new StringWriter();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

                // Marshall the java object to a writer object.
                jaxbMarshaller.marshal(object, writer);

                str = writer.toString();

            }
            catch (JAXBException e)
            {
                log.error("marshall exception: " + e);
            }
        }
        return str;
    }

    public static String marshall(final Object object, final String encoding)
    {

        String str = null;
        if (null != object)
        {
            try
            {

                JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());

                /*
                 * JAXBContext jaxbContext = JAXBContext.newInstance( buildExtraContextClasses(object));
                 */

                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                StringWriter writer = new StringWriter();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

                // Marshall the java object to a writer object.
                jaxbMarshaller.marshal(object, writer);

                str = writer.toString();

            }
            catch (JAXBException e)
            {
                log.error("marshall exception: " + e);
            }
        }
        return str;
    }

    /**
     * Returns the java object obtained after un-marshalling the XML.
     * 
     * @param xml allowed object is {@link String}
     * @param referenceClass
     * @return possible object is {@link Object}
     */
    public static Object unmarshal(final String xml, final Class referenceClass)
    {
        Object obj = null;
        if (null != xml && null != referenceClass)
        {
            try
            {
            	
                JAXBContext jaxbContext = JAXBContext.newInstance(referenceClass);           
                Unmarshaller umarshaller = jaxbContext.createUnmarshaller();              
                obj = umarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
              
            }
            catch (JAXBException e)
            {
                log.error("unmarshal exception: " + e);
            }
        }
        return obj;
    }


    /**
     * Generates the date/time values in specified formats from the {@link Date} object.
     * 
     * @param date allowed object is {@link Date}
     * @param pattern allowed object is {@link String}
     * @return possible object is {@link String}
     */
    public static String formatDateTime(Date date, String pattern)
    {
        String dateTime = null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            dateTime = formatter.format(date);
        }
        catch (Exception e)
        {
            log.error("formatDateTime exception: " + e);
        }
        return dateTime;
    }

    public static String convertInputStreamToString(InputStream in)
    {
        StringBuilder sb = null;
        try
        {
            InputStreamReader is = new InputStreamReader(in);
            sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null)
            {
                sb.append(read);
                read = br.readLine();
            }

            return sb.toString();
        }
        catch (Exception e)
        {
            log.error("convertInputStreamToString exception: " + e);
        }
        return null;
    }

    public static String convertInputStreamToString(InputStream in, String encoding)
    {
        StringBuilder sb = null;
        try
        {
            InputStreamReader is = new InputStreamReader(in);
            sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null)
            {
                sb.append(read);
                read = br.readLine();
            }

            return sb.toString();
        }
        catch (Exception e)
        {
            log.error("convertInputStreamToString exception: " + e);
        }
        return null;
    }

}