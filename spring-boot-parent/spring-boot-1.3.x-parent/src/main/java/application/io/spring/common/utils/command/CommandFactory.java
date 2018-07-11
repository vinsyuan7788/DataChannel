/**
 * CopyRight (C) 2014 DBAppSecurity Inc., All Rights Reserved
 */
package application.io.spring.common.utils.command;

import application.io.spring.common.enumeration.SystemEnum;

/**
 * @Description: 命令工厂
 * <p>
 *     (Change Log v1.0)
 * </p>
 * <p>
 *     <li></li>
 * </p>
 * @author lijun.huang
 * @date 2014年3月5日 上午7:00:16
 * @version 1.0
 *
 */
public class CommandFactory {
	private static SystemEnum currentSystemEnum;
	private static AbstractCommand currentCommand;
	static {
		currentSystemEnum = getCurrentSystem();
		currentCommand = getCommand(currentSystemEnum);
	}
	
	private CommandFactory(){
		
	}
    
	/**
	 * @获取当前操作系统
	 * @author lijun.huang
	 * @date 2014年3月9日 下午3:08:38
	 * @version 1.0
	 *
	 */
	public static SystemEnum getCurrentSystem() {
		String os = System.getProperty("os.name");

		if (os == null) {
			return null;
		}
		if (os.startsWith("Windows")) {
			return SystemEnum.WINDOWS;
		}
		if (os.startsWith("Linux")) {
			return SystemEnum.LINUX;
		}
		if (os.startsWith("Mac OS X")) {
            return SystemEnum.LINUX;
        }
		return null;
	}

	public static AbstractCommand getCommand(SystemEnum systemEnum){
		switch(systemEnum){
		case LINUX:
			return new LinuxCommand();
		case WINDOWS:
			return new WindowsCommand();
		default:
			return null;	
		}
	}
	
	public static AbstractCommand getCurrentCommand(){
		return currentCommand;
	}
}
