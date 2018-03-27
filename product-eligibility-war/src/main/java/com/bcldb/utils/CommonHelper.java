package com.bcldb.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class CommonHelper {
	
	public static Date getCurrentShortDate(){
        return stripTime(new Date());
    }	
	
	public static Date gregorian2Date(XMLGregorianCalendar xmlD){
        Date d = null;
        if (xmlD != null){
            d = stripTime(xmlD.toGregorianCalendar().getTime());
        }
        return d;
    }

	public static Date gregorian2DateNotNull(XMLGregorianCalendar xmlD){
        Date d = null;
        if (xmlD != null){
            d = gregorian2Date(xmlD);
        }else{
            d = getCurrentShortDate();
        }
        return d;
    }
	
	public static XMLGregorianCalendar date2Gregorian(Date d) throws Exception{
        XMLGregorianCalendar xd = null;
        if (d != null){            
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(d);
            xd = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        }
        return xd;
    }
    

	private static Date stripTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();        
    }
	
	public static XMLGregorianCalendar getTimestamp(){
        XMLGregorianCalendar timestamp = null;
        try{
            timestamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        }catch(DatatypeConfigurationException e){
        }
        return timestamp;
    }

}
