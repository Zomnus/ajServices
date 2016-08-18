package its.webservice.util;
import java.lang.reflect.Field;
import java.util.TimerTask;


public abstract class ReschedulableTimerTask  extends TimerTask {   
    public void setPeriod(long period) {   
        setDeclaredField(TimerTask.class, this, "period", period);    //修改定时器执行时间周期
    }   
       
    static boolean setDeclaredField(Class<?> clazz, Object obj,   
            String name, Object value) {   //  通过反射修改字段的值   
        try {   
            Field field = clazz.getDeclaredField(name);   
            field.setAccessible(true);   
            field.set(obj, value);   
            return true;   
        } catch (Exception ex) {   
            ex.printStackTrace();   
            return false;   
        }   
    }

}
