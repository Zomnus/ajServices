/**
 * Copyright(c) SUPCON 2008-2012. 浙江浙大中控信息技术有限公司
 */

package its.webservice.util;

/**
 * 系统名称：智能交通集成平台(JCPTWS)
 * 所属模块：文件管理模块
 * 功能描述：删除文件工具类
 * 文件名：its.webservice.util.DeleteFileUtil.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：2012-3-2 上午9:51:00
 * 修改者： lzk
 * 修改时间：2012-3-2 上午9:51:00
 */

import java.io.File;   

import org.apache.log4j.Logger;
  
/**  
 * 删除文件或目录  
 */  
public class DeleteFileUtil {   
	
	/** 获取Log4j实例 */
    protected static Logger log = Logger.getLogger(DeleteFileUtil.class.getName());
    
    /**  
     * 删除文件，可以是单个文件或文件夹  
     * @param   fileName    待删除的文件名  
     * @return 文件删除成功返回true,否则返回false  
     */  
    public static boolean delete(String fileName){   
        File file = new File(fileName);   
        if(!file.exists()){   
        	log.error("删除文件失败："+fileName+"文件不存在");   
            return false;   
        }else{   
            if(file.isFile()){   
                   
                return deleteFile(fileName);   
            }else{   
                return deleteDirectory(fileName);   
            }   
        }   
    }   
       
    /**  
     * 删除单个文件  
     * @param   fileName    被删除文件的文件名  
     * @return 单个文件删除成功返回true,否则返回false  
     */  
    public static boolean deleteFile(String fileName){   
        File file = new File(fileName);   
        if(file.isFile() && file.exists()){   
            file.delete();   
           log.debug("删除单个文件"+fileName+"成功！");   
            return true;   
        }else{   
        	log.error("删除单个文件"+fileName+"失败！");   
            return false;   
        }   
    }   
       
    /**  
     * 删除目录（文件夹）以及目录下的文件  
     * @param   dir 被删除目录的文件路径  
     * @return  目录删除成功返回true,否则返回false  
     */  
    public static boolean deleteDirectory(String dir){   
        //如果dir不以文件分隔符结尾，自动添加文件分隔符   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
        	log.error("删除目录失败"+dir+"目录不存在！");   
            return false;   
        }   
        boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)   
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //删除子文件   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //删除子目录   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
           
        if(!flag){   
        	log.error("删除目录失败");   
            return false;   
        }   
           
        //删除当前目录   
        if(dirFile.delete()){   
        	log.info("删除目录"+dir+"成功！");   
            return true;   
        }else{   
        	log.error("删除目录"+dir+"失败！");   
            return false;   
        }   
    }   
       
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {   
        //String fileName = "g:/temp/xwz.txt";   
        //DeleteFileUtil.deleteFile(fileName);   
        String fileDir = "D:/ftpserver/picserver/wf_pic/20120223";   
        //DeleteFileUtil.deleteDirectory(fileDir);   
        DeleteFileUtil.deleteDirectory(fileDir);   
           
    }   
}  
