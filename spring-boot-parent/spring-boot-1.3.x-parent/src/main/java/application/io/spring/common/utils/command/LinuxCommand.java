/**
 * CopyRight (C) 2014 DBAppSecurity Inc., All Rights Reserved
 */
package application.io.spring.common.utils.command;

import application.io.spring.common.enumeration.SystemEnum;

public class LinuxCommand extends AbstractCommand {
	
	public LinuxCommand(){
		super.setShellPath(LINUX_SHELL_PATH);
		super.setSystemEnum(SystemEnum.LINUX);
		super.setShellParam(LINUX_SHELL_PARAM);
		super.setCharsetName(LINUX_CHARSET_NAME);
	}
	
}
