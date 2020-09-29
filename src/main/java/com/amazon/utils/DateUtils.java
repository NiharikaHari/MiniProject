package com.amazon.utils;

import java.util.Date;

public class DateUtils {
	public static String getTimeStamp(){
		Date date = new Date();
		String timeStamp = date.toString().replaceAll(":| ", "_");
		return timeStamp;
	}
}
