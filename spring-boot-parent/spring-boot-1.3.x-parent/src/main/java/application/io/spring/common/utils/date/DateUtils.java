package application.io.spring.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateUtils {
	
    // Grace style
    public static final String PATTERN_GRACE = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_GRACE_NORMAL = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_GRACE_SIMPLE = "yyyy/MM/dd";
 
    // Classical style
    public static final String PATTERN_CLASSICAL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_CLASSICAL_NORMAL = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_CLASSICAL_SIMPLE = "yyyy-MM-dd";
    
    public static final String PATTERN_DATETIME_CLOSE = "yyyyMMddHHmmss";
    
	public static final String PATTERN_MILLISECOND = "yyyyMMddhhmmssSSS";

	public static final String PATTERN_MMDD_SIMPLE = "yyyyMMdd";
    
    public static final String PATTREN_DDMMM_SIMPLE = "ddMMMyyyy";
    public static final String PATTREN_DDMMMYY_SIMPLE = "ddMMMyy";
    
    public static final String  PATTREN_MDHMS_SIMPLE = "MMddhhmmss";
    
    public static final String PATTREN_SPECIAL=" yyyy-MM-dd'T'HH:mm:ss.SSSZZZ";
    
    public static final FastDateFormat PATTERN_GRACE_FORMAT = FastDateFormat.getInstance(PATTERN_GRACE);
    public static final FastDateFormat PATTERN_GRACE_NORMAL_FORMAT = FastDateFormat.getInstance(PATTERN_GRACE_NORMAL);
    public static final FastDateFormat PATTERN_GRACE_SIMPLE_FORMAT = FastDateFormat.getInstance(PATTERN_GRACE_SIMPLE);
    
    public static final FastDateFormat PATTERN_CLASSICAL_FORMAT = FastDateFormat.getInstance(PATTERN_CLASSICAL);
    public static final FastDateFormat PATTERN_CLASSICAL_NORMAL_FORMAT = FastDateFormat.getInstance(PATTERN_CLASSICAL_NORMAL);
    public static final FastDateFormat PATTERN_CLASSICAL_SIMPLE_FORMAT = FastDateFormat.getInstance(PATTERN_CLASSICAL_SIMPLE);
    
    public static final FastDateFormat PATTERN_DATETIME_CLOSE_FORMAT = FastDateFormat.getInstance(PATTERN_DATETIME_CLOSE);
    
    public static final FastDateFormat PATTERN_DD_MMM_FORMAT = FastDateFormat.getInstance(PATTREN_DDMMM_SIMPLE, Locale.US);
    public static final FastDateFormat PATTERN_DD_MMM_YY_FORMAT = FastDateFormat.getInstance(PATTREN_DDMMMYY_SIMPLE, Locale.US);
    public static final FastDateFormat PATTERN_HH_MM_FORMAT = FastDateFormat.getInstance("HHmm");
    
    private static final String GRACE_YEAR = "[0-9]{4}/[0-9]{2}/[0-9]{2}";
	private static final String CLASSICAL_YEAR = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	private static final String CLOSE_DATE = "[0-9]{4}[0-9]{2}[0-9]{2}";
	
	private static final String FLIGHT_DATE = "[0-9]{2}[A-Za-z]{3}[0-9]{2}";
	private static final String TWO_PATTERN = "[0-9]{2}";
	
    private static final Pattern PATTERN_GRACE_REGULAR = Pattern.compile("^"+GRACE_YEAR+" [0-9]{2}:[0-9]{2}:[0-9]{2}$"); //"yyyy/MM/dd HH:mm:ss"
    private static final Pattern PATTERN_GRACE_NORMAL_REGULAR = Pattern.compile("^"+GRACE_YEAR+" [0-9]{2}:[0-9]{2}$");   //"yyyy/MM/dd HH:mm"
    private static final Pattern PATTERN_GRACE_SIMPLE_REGULAR = Pattern.compile("^"+GRACE_YEAR+"$");                     //"yyyy/MM/dd"
    
    private static final Pattern PATTERN_CLASSICAL_REGULAR = Pattern.compile("^"+CLASSICAL_YEAR+" [0-9]{2}:[0-9]{2}:[0-9]{2}$");  //"yyyy-MM-dd HH:mm:ss"
    private static final Pattern PATTERN_CLASSICAL_NORMAL_REGULAR = Pattern.compile("^"+CLASSICAL_YEAR+" [0-9]{2}:[0-9]{2}$");    //"yyyy-MM-dd HH:mm"
    private static final Pattern PATTERN_CLASSICAL_SIMPLE_REGULAR = Pattern.compile("^"+CLASSICAL_YEAR+"$");                      //"yyyy-MM-dd"
    
    private static final Pattern PATTERN_DATETIME_CLOSE_REGULAR = Pattern.compile("^"+CLOSE_DATE+"[0-9]{2}[0-9]{2}[0-9]{2}$");    //"yyyyMMddHHmmss"
    
    private static final Pattern PATTERN_DATETIME_MILL_REGULAR = Pattern.compile("^[1-9][0-9]{0,17}$");    //"long 型date"
    
    private static final Pattern PATTERN_DATETIME_FLIGHT_DATE = Pattern.compile("^"+FLIGHT_DATE+"$");    // 19NOV2016
    
    private static final ImmutableList<FastDateFormat> fastDateFormatList = new Builder<FastDateFormat>()
    		.add(DateFormatUtils.ISO_DATE_FORMAT)
    		.add(DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT)
    		.add(DateFormatUtils.ISO_DATETIME_FORMAT)
    		.add(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT)
    		.add(DateFormatUtils.ISO_TIME_FORMAT)
    		.add(DateFormatUtils.ISO_TIME_NO_T_FORMAT)
    		.add(DateFormatUtils.ISO_TIME_NO_T_TIME_ZONE_FORMAT)
    		.add(DateFormatUtils.ISO_TIME_TIME_ZONE_FORMAT)
    		.add(PATTERN_HH_MM_FORMAT)
    		.build(); 
    
    private static final Map<Pattern, FastDateFormat> fastDateFormatMap = new HashMap<Pattern, FastDateFormat>();
    static{
    	for(FastDateFormat format : fastDateFormatList){
    		String pattern = format.getPattern();
    		//正则表达式转化
    		pattern = pattern.replaceAll("yyyy", "[0-9]{4}");
    		pattern = pattern.replaceAll("MM", TWO_PATTERN);
    		pattern = pattern.replaceAll("dd", TWO_PATTERN);
    		pattern = pattern.replaceAll("HH", TWO_PATTERN);
    		pattern = pattern.replaceAll("mm", TWO_PATTERN);
    		pattern = pattern.replaceAll("ss", TWO_PATTERN);
    		pattern = pattern.replaceAll("'", "");
    		if(pattern.endsWith("ZZ")){
    			pattern = pattern.substring(0, pattern.length() - 2);
    			
    			fastDateFormatMap.put(Pattern.compile ("^" + pattern + "\\+[0-9]{2}$"), format);
    			fastDateFormatMap.put(Pattern.compile ("^" + pattern + "\\+[0-9]{2}:[0-9]{2}$"), format);
    		}else{
    			fastDateFormatMap.put(Pattern.compile ("^" +pattern +"$"), format);    			
    		}
    	}
    }
    
    private DateUtils() {
        // Cannot be instantiated
    }
 
    /**
     * 根据默认格式将指定字符串解析成日期
     * 
     * @param str
     *            指定字符串
     * @return 返回解析后的日期
     */
    public static Date parse(String str) {
        return parse(str, PATTERN_CLASSICAL);
    }
 
    /**
     * 根据指定格式将指定字符串解析成日期
     * 
     * @param str
     *            指定日期
     * @param pattern
     *            指定格式
     * @return 返回解析后的日期
     */
    public static Date parse(String str, String pattern) {
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
        	log.error("=== DateUtils.parseDateClose | There is an exception"
            		+ " | exception message: " + e.getMessage() + " ===");
        }
        return null;
    }
 
    public static Date parseDateClose(String str) {
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATETIME_CLOSE);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            log.error("=== DateUtils.parseDateClose | There is an exception"
            		+ " | exception message: " + e.getMessage() + " ===");
        }
        return null;
    }
    
    /**
     * 根据默认格式将日期转格式化成字符串
     * 
     * @param date
     *            指定日期
     * @return 返回格式化后的字符串
     */
    public static String format(Date date) {
        return format(date, PATTERN_CLASSICAL);
    }
    
    /**
     * 根据指定格式将字符串转格式化成日期
     * @throws ParseException 
     */
    public static Date format(String str,String pattern) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	
    	return sdf.parse(str);
    }
    
    /**
     * 
     * @throws ParseException 
     * @Title: format 
     * @Description: 特殊用法 将 19NOV16 与 2016-11-19 互转
     */
    @SuppressWarnings("static-access")
	public static String formatFlightDate(String str) throws ParseException{
    	
    	if(PATTERN_CLASSICAL_SIMPLE_REGULAR.matches(CLASSICAL_YEAR, str)){
    		String newStr = PATTERN_DD_MMM_FORMAT.format(PATTERN_CLASSICAL_SIMPLE_FORMAT.parse(str));
    		return newStr.substring(0, newStr.length()-4) + newStr.substring(newStr.length()-2, newStr.length());
    	}
    	
    	if(PATTERN_DATETIME_FLIGHT_DATE.matches(FLIGHT_DATE, str)){
    		String newStr = str.substring(0, str.length()-2) + "20" + str.substring(str.length()-2, str.length());
    		return PATTERN_CLASSICAL_SIMPLE_FORMAT.format(PATTERN_DD_MMM_FORMAT.parse(newStr));
    	}
    	
    	return null;
    }

    /**
     * @Title: formatFlightDate 
     * @Description: 特殊用法 将  时间转成  19NOV16 互转
     * @param date
     * @return
     * @throws ParseException
     * @return: String
     */
    @SuppressWarnings("static-access")
	public static String formatFlightDate(Date date) throws ParseException{
    	if(date == null){
    		return null;
    	}
    	
    	String str = format(date,PATTERN_CLASSICAL_SIMPLE);
    	
    	if(PATTERN_CLASSICAL_SIMPLE_REGULAR.matches(CLASSICAL_YEAR, str)){
    		String newStr = PATTERN_DD_MMM_FORMAT.format(PATTERN_CLASSICAL_SIMPLE_FORMAT.parse(str));
    		return newStr.substring(0, newStr.length()-4) + newStr.substring(newStr.length()-2, newStr.length());
    	}
    	
    	
    	return null;
    }
    
    /**
     * 获取特定的日期格式
     * @throws ParseException 
     */
    public static Date formatDDMMM(String source) throws ParseException{
    	return PATTERN_DD_MMM_FORMAT.parse(source);
    }
 
    /**
     * 根据指定格式将指定日期格式化成字符串
     * 
     * @param date
     *            指定日期
     * @param pattern
     *            指定格式
     * @return 返回格式化后的字符串
     */
    public static String format(Date date, String pattern) {
    	if(date == null){
    		return null;
    	}
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
 
    /**
     * 获取时间date1与date2相差的秒数
     * 
     * @param date1
     *            起始时间
     * @param date2
     *            结束时间
     * @return 返回相差的秒数
     */
    public static int getOffsetSeconds(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / 1000);
    }
 
    /**
     * 获取时间date1与date2相差的分钟数
     * 
     * @param date1
     *            起始时间
     * @param date2
     *            结束时间
     * @return 返回相差的分钟数
     */
    public static int getOffsetMinutes(Date date1, Date date2) {
        return getOffsetSeconds(date1, date2) / 60;
    }
 
    /**
     * 获取时间date1与date2相差的小时数
     * 
     * @param date1
     *            起始时间
     * @param date2
     *            结束时间
     * @return 返回相差的小时数
     */
    public static int getOffsetHours(Date date1, Date date2) {
        return getOffsetMinutes(date1, date2) / 60;
    }
 
    /**
     * 获取时间date1与date2相差的天数数
     * 
     * @param date1
     *            起始时间
     * @param date2
     *            结束时间
     * @return 返回相差的天数
     */
    public static int getOffsetDays(Date date1, Date date2) {
        return getOffsetHours(date1, date2) / 24;
    }
 
    /**
     * 获取时间date1与date2相差的周数
     * 
     * @param date1
     *            起始时间
     * @param date2
     *            结束时间
     * @return 返回相差的周数
     */
    public static int getOffsetWeeks(Date date1, Date date2) {
        return getOffsetDays(date1, date2) / 7;
    }
 
    /**
     * 获取重置指定日期的时分秒后的时间
     * 
     * @param date
     *            指定日期
     * @param hour
     *            指定小时
     * @param minute
     *            指定分钟
     * @param second
     *            指定秒
     * @return 返回重置时分秒后的时间
     */
    public static Date getResetTime(Date date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, minute);
        cal.set(Calendar.MINUTE, second);
        return cal.getTime();
    }
 
    /**
     * 返回指定日期的起始时间
     * 
     * @param date
     *            指定日期（例如2014-08-01）
     * @return 返回起始时间（例如2014-08-01 00:00:00）
     */
    public static Date getIntegralStartTime(Date date) {
        return getResetTime(date, 0, 0, 0);
    }
 
    /**
     * 返回指定日期的结束时间
     * 
     * @param date
     *            指定日期（例如2014-08-01）
     * @return 返回结束时间（例如2014-08-01 23:59:59）
     */
    public static Date getIntegralEndTime(Date date) {
        return getResetTime(date, 23, 59, 59);
    }
 
    /**
     * 获取指定日期累加年月日后的时间
     * 
     * @param date
     *            指定日期
     * @param year
     *            指定年数
     * @param month
     *            指定月数
     * @param day
     *            指定天数
     * @return 返回累加年月日后的时间
     */
    public static Date rollDate(Date date, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        
        return cal.getTime();
    }
    
    /**
     * 获取指定日期累加年月日后的时间
     * 
     * @param date
     *            指定日期
     * @param year
     *            指定年数
     * @param month
     *            指定月数
     * @param day
     *            指定天数
     * @return 返回累加年月日后的时间
     */
    public static Date rollDate(Date date, int year, int month, int day,int hours,int minute,int seconds) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, seconds);
        
        return cal.getTime();
    }
 
    /**
     * 获取指定日期累加指定月数后的时间
     * 
     * @param date
     *            指定日期
     * @param month
     *            指定月数
     * @return 返回累加月数后的时间
     */
    public static Date rollMonth(Date date, int month) {
        return rollDate(date, 0, month, 0);
    }
 
    /**
     * 获取指定日期累加指定天数后的时间
     * 
     * @param date
     *            指定日期
     * @param day
     *            指定天数
     * @return 返回累加天数后的时间
     */
    public static Date rollDay(Date date, int day) {
        return rollDate(date, 0, 0, day);
    }
 
    /**
     * 计算指定日期所在月份的天数
     * 
     * @param date
     *            指定日期
     * @return 返回所在月份的天数
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        int dayOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dayOfMonth;
    }
 
    /**
     * 获取当月第一天的起始时间，例如2014-08-01 00:00:00
     * 
     * @return 返回当月第一天的起始时间
     */
    public static Date getMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取当月最后一天的结束时间，例如2014-08-31 23:59:59
     * 
     * @return 返回当月最后一天的结束时间
     */
    public static Date getMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }
 
    /**
     * 获取上个月第一天的起始时间，例如2014-07-01 00:00:00
     * 
     * @return 返回上个月第一天的起始时间
     */
    public static Date getLastMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取上个月最后一天的结束时间，例如2014-07-31 23:59:59
     * 
     * @return 返回上个月最后一天的结束时间
     */
    public static Date getLastMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }
 
    /**
     * 获取下个月第一天的起始时间，例如2014-09-01 00:00:00
     * 
     * @return 返回下个月第一天的起始时间
     */
    public static Date getNextMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取下个月最后一天的结束时间，例如2014-09-30 23:59:59
     * 
     * @return 返回下个月最后一天的结束时间
     */
    public static Date getNextMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }
 
    /**
     * 获取当前季度第一天的起始时间
     * 
     * @return 返回当前季度第一天的起始时间
     */
    public static Date getQuarterStartTime() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        if (month < 3) {
            cal.set(Calendar.MONTH, 0);
        } else if (month < 6) {
            cal.set(Calendar.MONTH, 3);
        } else if (month < 9) {
            cal.set(Calendar.MONTH, 6);
        } else {
            cal.set(Calendar.MONTH, 9);
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取当前季度最后一天的结束时间
     * 
     * @return 返回当前季度最后一天的结束时间
     */
    public static Date getQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        if (month < 3) {
            cal.set(Calendar.MONTH, 2);
        } else if (month < 6) {
            cal.set(Calendar.MONTH, 5);
        } else if (month < 9) {
            cal.set(Calendar.MONTH, 8);
        } else {
            cal.set(Calendar.MONTH, 11);
        }
        cal.set(Calendar.DAY_OF_MONTH, getDayOfMonth(cal.getTime()));
        return getIntegralEndTime(cal.getTime());
    }
 
    /**
     * 获取前一个工作日
     * 
     * @return 返回前一个工作日
     */
    public static Date getPrevWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -2);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取下一个工作日
     * 
     * @return 返回下个工作日
     */
    public static Date getNextWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 2);
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取当周的第一个工作日
     * 
     * @return 返回第一个工作日
     */
    public static Date getFirstWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 获取当周的最后一个工作日
     * 
     * @return 返回最后一个工作日
     */
    public static Date getLastWorkday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        return getIntegralStartTime(cal.getTime());
    }
 
    /**
     * 判断指定日期是否是工作日
     * 
     * @param date
     *            指定日期
     * @return 如果是工作日返回true，否则返回false
     */
    public static boolean isWorkday(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return !(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
 
    /**
     * 获取指定日期是星期几
     * 
     * @param date
     *            指定日期
     * @return 返回星期几的描述
     */
    public static String getWeekdayDesc(Date date) {
        final String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四",
                "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        return weeks[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }
 
    /**
     * 获取指定日期距离当前时间的时间差描述（如3小时前、1天前）
     * 
     * @param date
     *            指定日期
     * @return 返回时间差的描述
     */
    public static String getTimeOffsetDesc(Date date) {
        int seconds = getOffsetSeconds(date, new Date());
        if (Math.abs(seconds) < 60) {
            return Math.abs(seconds) + "秒" + (seconds > 0 ? "前" : "后");
        }
        int minutes = seconds / 60;
        if (Math.abs(minutes) < 60) {
            return Math.abs(minutes) + "分钟" + (minutes > 0 ? "前" : "后");
        }
        int hours = minutes / 60;
        if (Math.abs(hours) < 60) {
            return Math.abs(hours) + "小时" + (hours > 0 ? "前" : "后");
        }
        int days = hours / 24;
        if (Math.abs(days) < 7) {
            return Math.abs(days) + "天" + (days > 0 ? "前" : "后");
        }
        int weeks = days / 7;
        if (Math.abs(weeks) < 5) {
            return Math.abs(weeks) + "周" + (weeks > 0 ? "前" : "后");
        }
        int monthes = days / 30;
        if (Math.abs(monthes) < 12) {
            return Math.abs(monthes) + "个月" + (monthes > 0 ? "前" : "后");
        }
        int years = monthes / 12;
        return Math.abs(years) + "年" + (years > 0 ? "前" : "后");
    }
 
    public static Date formateDate(String str) throws ParseException {
    	for(Entry<Pattern, FastDateFormat> entry : fastDateFormatMap.entrySet()){
    		if(DateUtils.match(entry.getKey(), str)){
    			return entry.getValue().parse(str);
    		}
    	}
    	
		if(DateUtils.match(PATTERN_GRACE_REGULAR, str)){
    		return DateUtils.PATTERN_GRACE_FORMAT.parse(str);
    	}else if(DateUtils.match(PATTERN_GRACE_NORMAL_REGULAR, str)){
    		return DateUtils.PATTERN_GRACE_NORMAL_FORMAT.parse(str);
    	}else if(DateUtils.match(PATTERN_GRACE_SIMPLE_REGULAR, str)){
    		return DateUtils.PATTERN_GRACE_SIMPLE_FORMAT.parse(str);
    	}
    	
    	else if(DateUtils.match(PATTERN_CLASSICAL_REGULAR, str)){
    		return DateUtils.PATTERN_CLASSICAL_FORMAT.parse(str);
    	}else if(DateUtils.match(PATTERN_CLASSICAL_NORMAL_REGULAR, str)){
    		return DateUtils.PATTERN_CLASSICAL_NORMAL_FORMAT.parse(str);
    	}else if(DateUtils.match(PATTERN_CLASSICAL_SIMPLE_REGULAR, str)){
    		return DateUtils.PATTERN_CLASSICAL_SIMPLE_FORMAT.parse(str);
    	}
    	
    	else if(DateUtils.match(PATTERN_DATETIME_CLOSE_REGULAR, str)){
    		return DateUtils.PATTERN_DATETIME_CLOSE_FORMAT.parse(str);
    	}else if(DateUtils.match(PATTERN_DATETIME_FLIGHT_DATE, str)){
    		return DateUtils.PATTERN_DD_MMM_YY_FORMAT.parse(str);
    	}
		
    	else if(DateUtils.match(PATTERN_DATETIME_MILL_REGULAR, str)){
    	    Long lDate = new Long(str);
    	    return new Date(lDate);
    	}
		
    	else{
    		throw new RuntimeException(String.format("parser %s to Date fail", str));
    	}
		
    }
    
    public static boolean match(Pattern pattern, String str){
    	Matcher matcher = pattern.matcher(str);
    	return matcher.matches();
    }
    

    /**
     * 返回某个时间的年月日
     * 比如：2016-08-09
     * 返回    年：2016 月：8 日：9
     */
    public static int getDetailNumber(int timeType,Date date){
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	int detailNumber=calendar.get(timeType);
    	if(timeType==Calendar.MONTH){
    		detailNumber=detailNumber+=1;
    	}
		return detailNumber;
    }
    
    /**
     * 获取重置指定年或月份的日时分秒后的时间
     * 负数则表示不重置该时间的年或月或日
     * 
     * @param date
     *            指定月份
     * @param day
     * 			  指定某一天         
     *            
     * @param hour
     *            指定小时
     * @param minute
     *            指定分钟
     * @param second
     *            指定秒
     * @return 返回重置时分秒后的时间
     */
    public static Date getResetTime(Date date,int month,int day,int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        if(month>=0){
        	cal.set(Calendar.MONTH, month);
        }
        if(day>0){
        	cal.set(Calendar.DAY_OF_MONTH, day);
        }
        if(hour>=0){
        	cal.set(Calendar.HOUR_OF_DAY, hour);
        }
        cal.set(Calendar.SECOND, minute);
        cal.set(Calendar.MINUTE, second);
        return cal.getTime();
    }
    
    /**
     * 获取指定日期的前一天的起始时间
     * @return
     */
    public static Date getLastDayStartTime(Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	return getResetTime(cal.getTime(), 0, 0, 0);
    }
    
    /**
     * 返回当前日期的第二天
     * @param date
     * @return
     */
    public static Date getNextDay(Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DAY_OF_MONTH,1);
    	return cal.getTime();
    }
    
    public static Date getNextMonth(Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH,1);
    	return cal.getTime();
    }
    
    public static Date getNextYear(Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.YEAR,1);
    	return cal.getTime();
    }
    
    public static Date getNextTime(int timeType, Date date){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(timeType, 1);
    	return cal.getTime();
    }
    
    /**
	 * 返回指定时间的起始或结束时间
	 * 
	 * @param date
	 * @param timeType
	 *            获取某年，或某月，或某日，或某分钟的起始时间或结束时间
	 * @param isStart
	 *            是否要获得起始时间，false则表示获取结束时间
	 * @return
	 */
	public static Date getTimeStartOrEnd(Date date, int timeType, boolean isStart) {
		int month = -1;
		int day = -1;
		int hour = -1;
		int minute = -1;
		int second = -1;
		if (timeType == Calendar.YEAR) {
			month = isStart ? 0 : 11;
			timeType = Calendar.MONTH;
		}
		if (timeType == Calendar.MONTH) {
			day = isStart ? 1 : getDayOfMonth(date);
			timeType = Calendar.DAY_OF_MONTH;
		}
		if (timeType == Calendar.DAY_OF_MONTH) {
			hour = isStart ? 0 : 23;
			timeType = Calendar.HOUR_OF_DAY;
		}
		if (timeType == Calendar.HOUR_OF_DAY) {
			minute = isStart ? 0 : 59;
		}
		second = isStart ? 0 : 59;

		return getResetTime(date, month, day, hour, minute, second);
	}
	
	/**
	 * 重置分钟
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date resetMinuteOfTime(Date date, int minute){
		 Calendar cal = Calendar.getInstance();
	        if (date != null) {
	            cal.setTime(date);
	        }
	        cal.set(Calendar.MINUTE, minute);
	        return cal.getTime();
	}
	
	public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }  
	/** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }
    
    public static Calendar getCurrYearLastCalendar(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLastCalendar(currentYear);  
    }
    
    public static Calendar getYearLastCalendar(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
          
        return calendar;  
    }
    
    public static boolean isSameDay(Date src, Date dest){
    	return org.apache.commons.lang3.time.DateUtils.isSameDay(src, dest);
    }
    
    public static boolean isSameDay(Calendar src, Calendar dest){
    	return org.apache.commons.lang3.time.DateUtils.isSameDay(src, dest);
    }
}
