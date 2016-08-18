
package its.webservice.common;

import its.webservice.util.ItsUtility;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
 
/**
 * This is a base row mapper, As mapped with database object<br>
 * 
 * @author SUPCON
 * @version Version 1.0
 * @changeLog N/A
 */
public class BaseRowMapper implements RowMapper {

    /** Get a log4j instance */
    protected Logger log = Logger.getLogger(BaseRowMapper.class.getName());
    //private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

    /**
     * Define a classname attribute
     */
    private String classname;

    /**
     * If there are some attributes of entity bean which
     * don't want be mapped with database object, 
     * then, you can put those attributes name into
     * this array.
     */
    private String[] exclusions;

    /**
     * Constructor function
     * 
     * @param classname String
     */
    public BaseRowMapper(String classname) {
        this.classname = classname;
    }

    /**
     * Constructor function
     * 
     * @param classname String
     * @param exclusions This is a very useful parameter <br>
     *        when some attributes in entity bean, but no <br>
     *        corresponding fields in sql resultSet.
     */
    public BaseRowMapper(String classname, String[] exclusions) {
        this.classname = classname;
        this.exclusions = exclusions;
    }

    /**
     * Constructor function
     * 
     * @param classname Class
     */
    public BaseRowMapper(Class classname) {
        this.classname = classname.getName();
    }

    /**
     * Constructor function
     * 
     * @param classname Class
     * @param exclusions This is a very useful parameter <br>
     *        when some attributes in entity bean, but no <br>
     *        corresponding fields in sql resultSet.
     */
    public BaseRowMapper(Class classname, String[] exclusions) {
        this.classname = classname.getName();
        this.exclusions = exclusions;
    }

    /**
     * This function can map any entity bean with database object.
     * 
     * @param rs ResultSet
     * @param arg1 int
     * @return Object entity bean
     */
    public Object mapRow(ResultSet rs, int arg1) throws SQLException {

        // a entity java bean will be set into this variable
        Object newObject = null;

        try {
            // Create a class
            Class newClass = Class.forName(classname);

            // Get a instance of the class
            newObject = newClass.newInstance();

            // Get all methods of the class
            Method methlist[] = newClass.getDeclaredMethods();

            // Access all methods of the class
            for (int i = 0; i < methlist.length; i++) {
                Method meth = methlist[i];

                // exclude those methods which name not start with "set"
                if (!meth.getName().startsWith("set"))
                    continue;
                // exclude those methods which name contains "_"
                // if (meth.getName().indexOf("_") > 0)
                // continue;

                // exclude those methods which name are in the exclusions array
                if (ItsUtility.arrayContainsIgnoreCase(this.exclusions, meth.getName().substring(3))) {
                    continue;
                }

                // get first parameter types of the method
                Class pvec = meth.getParameterTypes()[0];

                // Saves values which get from database
                Object arglist[] = new Object[1];
                try {
                    if (pvec.isInstance("")) {
                        arglist[0] = rs.getString(this.getParamenter(meth.getName()));
                    } else if (pvec.toString().indexOf("int") != -1
                            || pvec.toString().indexOf("Integer") != -1) {
                        arglist[0] = new Integer(rs.getInt(this.getParamenter(meth.getName())));
                    } else if (pvec.toString().indexOf("Double") != -1
                            || pvec.toString().indexOf("double") != -1) {
                        arglist[0] = new Double(rs.getDouble(this.getParamenter(meth.getName())));
                    } else if (pvec.toString().indexOf("Date") != -1) {   
                    	arglist[0] = rs.getTimestamp(this.getParamenter(meth.getName()));                                                                   	
                    } else if (pvec.toString().indexOf("Timestamp") != -1) {
                        arglist[0] = rs.getTimestamp(this.getParamenter(meth.getName()));
                    } else if (pvec.toString().indexOf("Float") != -1
                            || pvec.toString().indexOf("float") != -1) {
                        arglist[0] = new Float(rs.getFloat(this.getParamenter(meth.getName())));
                    } else if (pvec.toString().indexOf("String") != -1) {
                        arglist[0] = new String(rs.getString(this.getParamenter(meth.getName())));
                    } else if (pvec.isInstance(Long.decode("1"))) {
                        arglist[0] = new Long(rs.getLong(this.getParamenter(meth.getName())));
                    }
                    meth.invoke(newObject, arglist);                    
                } catch (Exception e) {
                    log.warn("*******************warn********************");
                    log.warn("BaseRowMapper class name=" + this.classname);
                    log.warn("BaseRowMapper method name=" + meth.getName());
                    log.warn(e.getMessage());
                    log.warn("*******************warn********************");
                }
            }
        } catch (Exception e) {
            log.error("*******************error********************");
            log.error("BaseRowMapper class name=" + this.classname);
            log.error(e.getMessage());
            log.error("*******************error********************");
        }

        //log.debug("BaseRowMapper==newObject===" + newObject);
        return newObject;
    }

    /**
     * get database field name
     * 
     * @param methname String
     * @return String database field name
     */
    public String getParamenter(String methname) {
        String rs = methname;
        rs = rs.substring(3);
        //rs = rs.toUpperCase();
        rs=rs.toLowerCase();
        return rs;
    }

}
