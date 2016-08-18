package its.webservice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public abstract class TimeUtil {   
	  
	  
//	---当前日期的年，月，日，时，分，秒   
	public static Calendar now   = Calendar.getInstance();   
	int    year = now.get( Calendar.YEAR );   
	int    date = now.get( Calendar.DAY_OF_MONTH );   
	int    month = now.get( Calendar.MONTH ) + 1;   
	int    hour = now.get( Calendar.HOUR );   
	int    min   = now.get( Calendar.MINUTE );   
	int    sec   = now.get( Calendar.SECOND );   
	  
//	-------------------------------日期类型转换---------------------------------------------------------------------------   
	/**  
	* 字符型日期转化util.Date型日期  
	* @Param:p_strDate 字符型日期   
	* @param p_format 格式:"yyyy-MM-dd" / "yyyy-MM-dd hh:mm:ss"  
	* @Return:java.util.Date util.Date型日期  
	* @Throws: ParseException  
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static java.util.Date toUtilDateFromStrDateByFormat( String p_strDate, String p_format )   
	    throws ParseException {   
	   java.util.Date l_date = null;   
	   java.text.DateFormat df = new java.text.SimpleDateFormat( p_format );   
	   if ( p_strDate != null && ( !"".equals( p_strDate ) ) && p_format != null && ( !"".equals( p_format ) ) ) {   
	    l_date = df.parse( p_strDate );   
	   }   
	   return l_date;   
	}   
	  
	  
	  
	/**  
	* 字符型日期转化成sql.Date型日期  
	* @param p_strDate    字符型日期  
	* @return java.sql.Date sql.Date型日期  
	* @throws ParseException   
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static java.sql.Date toSqlDateFromStrDate( String p_strDate ) throws ParseException {   
	   java.sql.Date returnDate = null;   
	   java.text.DateFormat sdf = new java.text.SimpleDateFormat();   
	   if ( p_strDate != null && ( !"".equals( p_strDate ) ) ) {   
	    returnDate = new java.sql.Date( sdf.parse( p_strDate ).getTime() );   
	   }   
	   return returnDate;   
	}   
	     
	/**   
	* util.Date型日期转化指定格式的字符串型日期  
	* @param   p_date    Date   
	* @param   p_format String   
	* 格式1:"yyyy-MM-dd"   
	* 格式2:"yyyy-MM-dd hh:mm:ss EE"   
	* 格式3:"yyyy年MM月dd日 hh:mm:ss EE"   
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写  
	* @return String   
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static String toStrDateFromUtilDateByFormat( java.util.Date p_utilDate, String p_format ) throws ParseException {   
	   String l_result = "";   
	   if ( p_utilDate != null ) {   
	    SimpleDateFormat sdf = new SimpleDateFormat( p_format );   
	    l_result = sdf.format( p_utilDate );   
	   }   
	   return l_result;   
	}   
	  
	/**  
	* util.Date型日期转化转化成Calendar日期  
	* @param p_utilDate Date  
	* @return Calendar  
	* @Author: zhuqx  
	* @Date: 2006-10-31  
	*/   
	public static Calendar toCalendarFromUtilDate(java.util.Date p_utilDate) {   
	   Calendar c = Calendar.getInstance();   
	   c.setTime(p_utilDate);   
	   return c;   
	}   
	  
	/**  
	* util.Date型日期转化sql.Date(年月日)型日期  
	* @Param: p_utilDate util.Date型日期  
	* @Return: java.sql.Date sql.Date型日期  
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static java.sql.Date toSqlDateFromUtilDate( java.util.Date p_utilDate ) {   
	   java.sql.Date returnDate = null;   
	   if ( p_utilDate != null ) {   
	    returnDate = new java.sql.Date( p_utilDate.getTime() );   
	   }   
	   return returnDate;   
	}   
	  
	/**  
	* util.Date型日期转化sql.Time(时分秒)型日期  
	* @Param: p_utilDate util.Date型日期  
	* @Return: java.sql.Time sql.Time型日期  
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static java.sql.Time toSqlTimeFromUtilDate( java.util.Date p_utilDate ) {   
	   java.sql.Time returnDate = null;   
	   if ( p_utilDate != null ) {   
	    returnDate = new java.sql.Time( p_utilDate.getTime() );   
	   }   
	   return returnDate;   
	}   
	  
	/**  
	* util.Date型日期转化sql.Date(时分秒)型日期  
	* @Param: p_utilDate util.Date型日期  
	* @Return: java.sql.Timestamp sql.Timestamp型日期  
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static java.sql.Timestamp toSqlTimestampFromUtilDate( java.util.Date p_utilDate ) {   
	   java.sql.Timestamp returnDate = null;   
	   if ( p_utilDate != null ) {   
	    returnDate = new java.sql.Timestamp( p_utilDate.getTime() );   
	   }   
	   return returnDate;   
	}   
	  
	/**  
	* sql.Date型日期转化util.Date型日期  
	* @Param: sqlDate sql.Date型日期  
	* @Return: java.util.Date util.Date型日期  
	* @Author: zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static java.util.Date toUtilDateFromSqlDate( java.sql.Date p_sqlDate ) {   
	   java.util.Date returnDate = null;   
	   if ( p_sqlDate != null ) {   
	    returnDate = new java.util.Date( p_sqlDate.getTime() );   
	   }   
	   return returnDate;   
	}
	
	public static String toStrDateTimeFromCalendarByFormat(Calendar calendar){
		int year =calendar.get(Calendar.YEAR);    
		  
		int month=calendar.get(Calendar.MONTH)+1;    
		  
		int day =calendar.get(Calendar.DAY_OF_MONTH);    
		  
		int hour =calendar.get(Calendar.HOUR_OF_DAY);    
		  
		int minute =calendar.get(Calendar.MINUTE);    
		  
		int seconds =calendar.get(Calendar.SECOND);  
		
		String _yystr = "", _mmstr = "", _ddstr = "", _hourstr = "", _minstr = "",_secondstr = "";
		_yystr = ""+year;
		_mmstr = "" + month;
		if (month < 10) {
            _mmstr = "0" + month;
        }
		_ddstr = ""+day;
		if (day < 10) {
            _ddstr = "0" + day;
        }
		_hourstr = ""+hour;
		if(hour<10){
			_hourstr = "0" + hour;
		}
		_minstr = ""+minute;
		if(minute<10){
			_minstr = "0" + minute;
		}
		_secondstr = ""+seconds;
		if(seconds<10){
			_secondstr = "0" + seconds;
		}
		String time = _yystr+"-"+_mmstr+"-"+_ddstr+" "+_hourstr+":"+_minstr+":"+_secondstr;
		
		return time;
	}
	
	public static String toStrDateFromCalendarByFormat(Calendar calendar){
		int year =calendar.get(Calendar.YEAR);    
		  
		int month=calendar.get(Calendar.MONTH)+1;    
		  
		int day =calendar.get(Calendar.DAY_OF_MONTH);   
		
		String _yystr = "", _mmstr = "", _ddstr = "";
		_yystr = ""+year;
		_mmstr = "" + month;
		if (month < 10) {
            _mmstr = "0" + month;
        }
		_ddstr = ""+day;
		if (day < 10) {
            _ddstr = "0" + day;
        }
		
		String daystr = _yystr+"-"+_mmstr+"-"+_ddstr;
		
		return daystr;
	}
	  
//	-----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------   
	/**   
	* 获取指定日期的年份   
	* @param p_date util.Date日期   
	* @return int   年份   
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static int getYearOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.get( java.util.Calendar.YEAR );   
	}   
	     
	/**   
	* 获取指定日期的月份   
	* @param p_date util.Date日期   
	* @return int   月份   
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static int getMonthOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.get( java.util.Calendar.MONTH ) + 1;   
	}   
	  
	/**   
	* 获取指定日期的日份   
	* @param p_date util.Date日期   
	* @return int   日份   
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static int getDayOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.get( java.util.Calendar.DAY_OF_MONTH );   
	}   
	  
	/**   
	* 获取指定日期的小时   
	* @param p_date util.Date日期   
	* @return int   日份   
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static int getHourOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.get( java.util.Calendar.HOUR_OF_DAY );   
	}   
	     
	/**   
	* 获取指定日期的分钟   
	* @param p_date util.Date日期   
	* @return int   分钟   
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static int getMinuteOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.get( java.util.Calendar.MINUTE );   
	}   
	     
	/**   
	* 获取指定日期的秒钟   
	* @param p_date util.Date日期   
	* @return int   秒钟   
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static int getSecondOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.get( java.util.Calendar.SECOND );   
	}   
	     
	/**   
	* 获取指定日期的毫秒     
	* @param p_date util.Date日期   
	* @return long   毫秒     
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static long getMillisOfDate( java.util.Date p_date ) {   
	   java.util.Calendar c = java.util.Calendar.getInstance();   
	   c.setTime( p_date );   
	   return c.getTimeInMillis();   
	}   
	  
//	-----------------获取当前/系统日期(指定日期格式)-----------------------------------------------------------------------------------   
	/**  
	* 获取指定日期格式当前日期的字符型日期  
	* @param p_format 日期格式  
	* 格式1:"yyyy-MM-dd"   
	* 格式2:"yyyy-MM-dd hh:mm:ss EE"   
	* 格式3:"yyyy年MM月dd日 hh:mm:ss EE"   
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写  
	* @return String 当前时间字符串  
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static String getNowOfDateByFormat( String p_format ) {   
	   Date d = new Date();   
	   SimpleDateFormat sdf = new SimpleDateFormat( p_format );   
	   String dateStr = sdf.format( d );   
	   return dateStr;   
	}   
	  
	/**  
	* 获取指定日期格式系统日期的字符型日期  
	* @param p_format 日期格式  
	* 格式1:"yyyy-MM-dd"   
	* 格式2:"yyyy-MM-dd hh:mm:ss EE"   
	* 格式3:"yyyy年MM月dd日 hh:mm:ss EE"   
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写  
	* @return String 系统时间字符串  
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static String getSystemOfDateByFormat( String p_format ) {   
	   long time = System.currentTimeMillis();   
	   Date d = new Date( time );   
	   SimpleDateFormat sdf = new SimpleDateFormat( p_format );   
	   String dateStr = sdf.format( d );   
	   return dateStr;   
	}   
	  
	/**  
	* 获取字符日期一个月的天数  
	* @param p_date  
	* @return 天数  
	* @author zhuqx  
	*/   
	public static long getDayOfMonth( Date p_date ) throws ParseException {   
	   int year = getYearOfDate(p_date);   
	   int month = getMonthOfDate( p_date )-1;   
	   int day = getDayOfDate( p_date );   
	   int hour = getHourOfDate( p_date );   
	   int minute = getMinuteOfDate( p_date );   
	   int second = getSecondOfDate( p_date );   
	   Calendar l_calendar = new GregorianCalendar(year,month,day,hour,minute,second);   
	   return l_calendar.getActualMaximum( Calendar.DAY_OF_MONTH );   
	}   
	  
//	 -----------------获取指定月份的第一天,最后一天 ---------------------------------------------------------------------------   	
	/**   
	* 获取指定月份的第一天   
	* @param p_strDate 指定月份  
	* @param p_formate 日期格式  
	* @return String 时间字符串  
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static String getDateOfMonthBegin( String p_strDate, String p_format ) throws ParseException {   
	   java.util.Date date = toUtilDateFromStrDateByFormat( p_strDate,p_format );   
	   return toStrDateFromUtilDateByFormat( date,"yyyy-MM" ) + "-01";   
	}   
	     
	/**   
	* 获取指定月份的最后一天   
	* @param p_strDate 指定月份  
	* @param p_formate 日期格式  
	* @return String 时间字符串  
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static String getDateOfMonthEnd( String p_strDate, String p_format ) throws ParseException {   
	   java.util.Date date = toUtilDateFromStrDateByFormat( getDateOfMonthBegin( p_strDate,p_format ),p_format );   
	   Calendar calendar = Calendar.getInstance();   
	   calendar.setTime( date );   
	   calendar.add( Calendar.MONTH,1 );   
	   calendar.add( Calendar.DAY_OF_YEAR,-1 );   
	   return toStrDateFromUtilDateByFormat( calendar.getTime(),p_format );   
	}   
	
	/**
	 * 获取指定年份的第一天   
	 * @param p_strDate  指定年份
	 * @param p_format
	 * @return
	 * @throws ParseException
	 */
	public static String getDateOfYearBegin( String p_strDate, String p_format ) throws ParseException {   
		   java.util.Date date = toUtilDateFromStrDateByFormat( p_strDate,p_format );   
		   return toStrDateFromUtilDateByFormat(date,"yyyy") + "-01-01";   
	}
	
	/**   
	* 获取指定年份的最后一天   
	* @param p_strDate 指定月份  
	* @param p_formate 日期格式  
	* @return String 时间字符串  
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/   
	public static String getDateOfYearEnd( String p_strDate, String p_format ) throws ParseException {   
	   java.util.Date date = toUtilDateFromStrDateByFormat( getDateOfYearBegin( p_strDate,p_format ),p_format );   
	   Calendar calendar = Calendar.getInstance();   
	   calendar.setTime( date );   
	   calendar.add( Calendar.YEAR,1 );   
	   calendar.add( Calendar.DAY_OF_YEAR,-1 );   
	   return toStrDateFromUtilDateByFormat( calendar.getTime(),p_format );   
	} 
	
	/**
	 * 获取当前年份及之前5年 年份列表
	 * @param n 当前年份向前年份数
	 * @return
	 */
	public static List<Integer> getYearList(int n){
		Calendar calendar = Calendar.getInstance();
		List<Integer> yearList = new ArrayList<Integer>();
		int nowYear = calendar.get(Calendar.YEAR);
		for(int i=0;i<n+1;i++){
			yearList.add(nowYear-i);
		}
		return yearList;
	}
	
	/**
	 * 获取月份列表
	 * @return
	 */
	public static List<String> getMonthList(){
		List<String> monthList = new ArrayList<String>();
		for(int i=0;i<12;i++){
			monthList.add(StringUtils.repeat(i+1+"", 2));
		}
		return monthList;
	}
	/**
	 * 获取小时列表
	 * @return
	 */
	public static List<String> getHourList(){
		List<String> hourList = new ArrayList<String>();
		for(int i=-1;i<23;i++){
			hourList.add(StringUtils.repeat(i+1+"", 2));
		}
		return hourList;
	}
	
	//**********************************************00 2010-05-30加入***********************************************************
	
    //用来全局控制 上一周，本周，下一周的周数变化     
    private static int weeks = 0;     
//    private static int MaxDate;//一月最大天数     
//    private static int MaxYear;//一年最大天数  
	 /**    
        * 得到二个日期间的间隔天数    
        */     
    public static String getTwoDay(String sj1, String sj2) {     
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");     
        long day = 0;     
        try {     
         java.util.Date date = myFormatter.parse(sj1);     
         java.util.Date mydate = myFormatter.parse(sj2);     
         day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);     
        } catch (Exception e) {     
         return "";     
        }     
        return day + "";     
    }     
     
     
    /**    
        * 根据一个日期，返回是星期几的字符串          
        */     
    public static String getWeek(String sdate) {     
        // 再转换为时间     
        Date date = TimeUtil.strToDate(sdate);     
        Calendar c = Calendar.getInstance();     
        c.setTime(date);     
        return new SimpleDateFormat("EEEE").format(c.getTime());     
    }     
     
    /**    
        * 将短时间格式字符串转换为时间 yyyy-MM-dd 
        */     
    public static Date strToDate(String strDate) {     
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");     
        ParsePosition pos = new ParsePosition(0);     
        Date strtodate = formatter.parse(strDate, pos);     
        return strtodate;     
    }     
     
    /**    
        * 两个时间之间的天数 
        */     
    public static long getDays(String date1, String date2) {     
        if (date1 == null || date1.equals(""))     
         return 0;     
        if (date2 == null || date2.equals(""))     
         return 0;     
        // 转换为标准时间     
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");     
        java.util.Date date = null;     
        java.util.Date mydate = null;     
        try {     
         date = myFormatter.parse(date1);     
         mydate = myFormatter.parse(date2);     
        } catch (Exception e) {     
        }     
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);     
        return day;     
    }     
     
     
     
         
    // 计算当月最后一天,返回字符串     
    public static String getDefaultDay(){       
       String str = "";     
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");         
     
       Calendar lastDate = Calendar.getInstance();     
       lastDate.set(Calendar.DATE,1);//设为当前月的1号     
       lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号     
       lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天     
            
       str=sdf.format(lastDate.getTime());     
       return str;       
    }     
         
    // 上月第一天     
    public static String getPreviousMonthFirst(){       
       String str = "";     
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");         
     
       Calendar lastDate = Calendar.getInstance();     
       lastDate.set(Calendar.DATE,1);//设为当前月的1号     
       lastDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1号     
        str=sdf.format(lastDate.getTime());     
       return str;       
    }     
         
    //获取当月第一天     
    public static String getFirstDayOfMonth(){       
       String str = "";     
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");         
     
       Calendar lastDate = Calendar.getInstance();     
       lastDate.set(Calendar.DATE,1);//设为当前月的1号     
       str=sdf.format(lastDate.getTime());     
       return str;       
    }    
         
         
    //获取当天时间      
    public static String getNowTime(String dateformat){     
        Date   now   =   new   Date();        
        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(dateformat);//可以方便地修改日期格式        
        String hehe = dateFormat.format(now);        
        return hehe;     
    }     
    
  //获取当天时间      
    public static String returnNowDate(String dateformat ){
    	Calendar calendar_now = Calendar.getInstance();
    	SimpleDateFormat   sdf = new  SimpleDateFormat (dateformat);
    	String now_date_str =   sdf.format(calendar_now.getTime());
    	return  now_date_str;
    }
    
         
    // 获得当前日期与本周日相差的天数     
    private static int getMondayPlus() {     
        Calendar cd = Calendar.getInstance();     
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......     
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1  
        //System.out.println("相差天数:"+dayOfWeek);     
        if (dayOfWeek == 1) {     
            return 0;     
        } else {     
            return 1 - dayOfWeek;     
        }     
    }     
         
    //获得本周一的日期     
    public static String getMondayOFWeek(){
         int mondayPlus = getMondayPlus();     
         GregorianCalendar currentDate = new GregorianCalendar();     
         currentDate.add(GregorianCalendar.DATE, mondayPlus);     
         Date monday = currentDate.getTime();     
              
         DateFormat df = DateFormat.getDateInstance();     
         String preMonday = df.format(monday);     
         return preMonday;     
    }     
         
    //获得相应周的周六的日期     
    public static String getSaturday() {     
        int mondayPlus = getMondayPlus();     
        GregorianCalendar currentDate = new GregorianCalendar();     
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * 0 + 6);     
        Date monday = currentDate.getTime();     
        DateFormat df = DateFormat.getDateInstance();     
        String preMonday = df.format(monday);     
        return preMonday;     
    }          
         
    // 获得本周星期日的日期       
    public static String getCurrentWeekday() {
        int mondayPlus = getMondayPlus();     
        GregorianCalendar currentDate = new GregorianCalendar();     
        currentDate.add(GregorianCalendar.DATE, mondayPlus+6);     
        Date monday = currentDate.getTime();     
             
        DateFormat df = DateFormat.getDateInstance();     
        String preMonday = df.format(monday);     
        return preMonday;     
    } 
         
    // 获得上周星期日的日期     
    public static String getPreviousWeekSunday() {  
        int mondayPlus = getMondayPlus();     
        GregorianCalendar currentDate = new GregorianCalendar();     
        currentDate.add(GregorianCalendar.DATE, mondayPlus+weeks);     
        Date monday = currentDate.getTime();     
        DateFormat df = DateFormat.getDateInstance();     
        String preMonday = df.format(monday);     
        return preMonday;     
    }     
     
    // 获得上周星期一的日期     
    public static String getPreviousWeekday() {  
        int mondayPlus = getMondayPlus();     
        GregorianCalendar currentDate = new GregorianCalendar();     
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);     
        Date monday = currentDate.getTime();     
        DateFormat df = DateFormat.getDateInstance();     
        String preMonday = df.format(monday);     
        return preMonday;     
    }     
         
    // 获得下周星期一的日期     
    public static String getNextMonday() {     
        int mondayPlus = getMondayPlus();     
        GregorianCalendar currentDate = new GregorianCalendar();     
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);     
        Date monday = currentDate.getTime();     
        DateFormat df = DateFormat.getDateInstance();     
        String preMonday = df.format(monday);     
        return preMonday;     
    }
	//*******************************************************************************************************************
}