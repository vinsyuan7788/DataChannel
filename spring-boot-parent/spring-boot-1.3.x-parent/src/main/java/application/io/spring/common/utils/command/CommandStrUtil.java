/**
 * CopyRight (C) 2014 DBAppSecurity Inc., All Rights Reserved
 */
package application.io.spring.common.utils.command;

import java.io.File;

/**
 * @ClassCName:获取命令字符串工具类
 * @Description: 根据参数获取命令字符串信息
 * <p>
 *     (Change Log v1.0 createuser lijun.huang 获取复制命令、wget命令)
 * </p>
 * <p>
 *     <li>复制文件：{@link #getUnixCopyCmd(String, String)}</li>
 *     </li>wget命令：{@link #getWgetStr(String, String)}</li>
 * </p>
 * @author lijun.huang
 * @date 2014年3月8日 下午12:22:18
 * @version 1.0
 *
 */
public class CommandStrUtil {
    /**
     * @Description: 获取Unix复制文件的命令字符串
     * @author lijun.huang
     * @date 2014-3-2 下午10:42:04
     * @version 1.0
     *
     * @param srcPath 如果是文件夹则复制文件夹全部信息，如果是文件，则复制文件
     * @param distPATH
     * <pre>
     *          1、不存在：创建文件夹
     *                         2、文件夹：创建文件夹
     *                         3、存在且是文件：复制进上一级目录
     * </pre>
     * @return
     */
    public static String getUnixCopyStr(String srcPath, String distPATH) {
        File src = new File(srcPath);

        if (!src.exists()) {
            return null;
        }

        String cmdParam = "";

        if (src.isDirectory()) {
            cmdParam = " -r ";
        }

        File curDist = new File(distPATH);
        File dist = curDist.getParentFile();
        
        if (!dist.exists()) {
            dist.mkdirs();
        } else if (dist.isFile()) {
            dist = dist.getParentFile();
        }

        StringBuffer cmdBuff = new StringBuffer();
        cmdBuff.append("cp ").append(cmdParam).append(" ").append(src.getAbsolutePath()).append(" ")
               .append(dist.getAbsolutePath());

        return cmdBuff.toString();
    }

    /**
     * @Description: 通过进行名称删除一类进程的命令
     * @author lijun.huang
     * @date 2014年3月9日 下午2:12:13
     * @version 1.0
     *
     * @param processName
     * @return
     */
    public static String getKillStrByName(String processName) {
        StringBuffer cmdBuff = new StringBuffer();

        cmdBuff.append("ps -ef|grep ").append(processName).append("|grep -v grep|cut -c 9-15|xargs kill -9");

        return cmdBuff.toString();
    }
    
    public static String[] getCmdArray(String shellPath, String shellParam, String cmd){
    	String[] cmdArray = { shellPath, shellParam, cmd };
    	return cmdArray;
    }
    
}
