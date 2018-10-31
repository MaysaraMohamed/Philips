package com.philips.backend.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
@Controller
public class Utilities {

	public static boolean stringContainsNumber( String s ){
	    return Pattern.compile( "[0-9]" ).matcher( s ).find();
	}
	
	public static String getNumberOnly(String input){
		   String numberOnly= input.replaceAll("[^0-9]", "");
		   return numberOnly; 
	}
	
	public static Date stringToDate(String stringDate) {
	    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");  
	    Date date = null;
		try {
			date = formatter.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return date; 
	}
	
    public static Date ignoreTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static boolean compareStringLists(List<String> list1, List<String> list2)
    {
    	if(list1.size() != list2.size())
    		return false; 
    	list1.retainAll(list2); 
    	if(list1.size() != list2.size())
    		return false; 
    	return true; 
    }
}
