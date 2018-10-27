package com.philips.backend.common;

import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
@Controller
public class Utilities {

	public static boolean stringContainsNumber( String s )
	{
	    return Pattern.compile( "[0-9]" ).matcher( s ).find();
	}
	
	public static String getNumberOnly(String input)
	{
		   String numberOnly= input.replaceAll("[^0-9]", "");
		   return numberOnly; 
	}
}
