import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class stringFormatTime {
	
	
	public String SFTime(long msecs) 
	{	
		GregorianCalendar cal = new GregorianCalendar();
    	StringBuffer sBuf = new StringBuffer(8);
    	
		cal.setTime(new Date(msecs));
	 	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	if (hour == 0) 
    		hour = 12;
   		if (hour < 10)
      		sBuf.append("0");
    	sBuf.append(Integer.toString(hour));
    	sBuf.append(":");

    	int minute = cal.get(Calendar.MINUTE);
    	if (minute < 10)
      		sBuf.append("0");
    	sBuf.append(Integer.toString(minute));
    	sBuf.append(":");
    	
    	int second = cal.get(Calendar.SECOND);
    	if (second < 10) 
    		sBuf.append("0");
    	sBuf.append(Integer.toString(second));
    	
    	sBuf.append(":");
    	int msecond = cal.get(Calendar.MILLISECOND);
    	sBuf.append(Integer.toString(msecond));
 
    	return (sBuf.toString());
	}
}