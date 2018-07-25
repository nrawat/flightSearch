package com.xyz.common;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class Common {

    public static String toString(String value) {
        return value == null ? "" : value.trim();
    }
    
    public static Date getDateFromString(String dateString) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
    	sdf.setLenient(false);

    	return sdf.parse(dateString);
    }

    @SuppressWarnings("deprecation")
	public static Time getTimeDifference(Date startDate, Date endDate) {
    	long duration = endDate.getTime() - startDate.getTime();
    	long hours = TimeUnit.MILLISECONDS.toHours(duration);
    	duration -= TimeUnit.HOURS.toMillis(hours);
    	long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

    	return new Time((int)hours, (int)minutes, 0);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertSelectMap(Map<Object, Object> selectMap, String columnName) {
    	Map newMap = new HashMap<>();

	    if(selectMap != null) {
	        for(Map.Entry<Object, Object> entry : selectMap.entrySet()) {
	            newMap.put(entry.getKey(), ((Map)entry.getValue()).get(columnName));
	        }
	        return newMap;
	    }

	    return null;
    }

    public static Date getTodayDate() throws ParseException {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_FORMAT);
    	LocalDate localDate = LocalDate.now();
    	return getDateFromString(dtf.format(localDate));
    }
}
