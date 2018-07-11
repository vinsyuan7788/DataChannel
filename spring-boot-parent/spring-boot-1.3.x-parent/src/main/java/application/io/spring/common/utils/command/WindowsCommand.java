/**
 * CopyRight (C) 2014 DBAppSecurity Inc., All Rights Reserved
 */
package application.io.spring.common.utils.command;

import application.io.spring.common.enumeration.SystemEnum;

public class WindowsCommand extends AbstractCommand {
	
	public WindowsCommand(){
		super.setShellPath( WINDOWS_SHELL_PATH );
		super.setSystemEnum( SystemEnum.WINDOWS );
		super.setShellParam( WINDOWS_SHELL_PARAM );
		super.setCharsetName(WINDOWS_CHARSET_NAME);
	}
	
}
