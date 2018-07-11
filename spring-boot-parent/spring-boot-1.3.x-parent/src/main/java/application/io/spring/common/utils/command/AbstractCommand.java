/**
 * CopyRight (C) 2014 DBAppSecurity Inc., All Rights Reserved
 */
package application.io.spring.common.utils.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import application.io.spring.common.enumeration.SystemEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * @author lijun.huang
 * @date 2014年3月5日 上午7:00:16
 * @version 1.0
 *
 */
@Log4j2
public class AbstractCommand {

	protected static final String LINUX_SHELL_PATH = "/bin/bash";
	protected static final String WINDOWS_SHELL_PATH = "cmd.exe";

	protected static final String LINUX_SHELL_PARAM = "-c";
	protected static final String WINDOWS_SHELL_PARAM = "/c";
	
	protected static final String LINUX_CHARSET_NAME = "UTF-8";
	protected static final String WINDOWS_CHARSET_NAME = "GBK";

	@Getter
	@Setter
	private SystemEnum systemEnum;

	@Getter
	@Setter
	private String shellPath;

	@Getter
	@Setter
	private String shellParam;

	@Getter
	@Setter
	private String charsetName;
	/**
	 * 执行shell命令
	 * 
	 * @param cmd
	 * @throws IOException
	 */
	public void excuteShell(String cmd) throws IOException {
		this.excuteShell(cmd, null);
	}

	public void excuteShell(String cmd, File runDir) throws IOException {
		String[] cmdArray = getCmdArray(cmd);

		this.excuteShell(cmdArray, runDir);
	}
	
	public void excuteShell(String[] cmdArray) throws IOException {
		this.excuteShell(cmdArray, null);
	}

	public void excuteShell(String[] cmdArray, File runDir) throws IOException {
		excuteWaitForEnd(cmdArray, runDir);
	}
	
	protected String[] getCmdArray(String cmd) {
		String[] cmdArray = { shellPath, shellParam, cmd };
		return cmdArray;
	}
	
	protected Process getProcess(String cmd) throws IOException{
		return this.getProcess(cmd, null);
	}
	
	protected Process getProcess(String[] cmdArray) throws IOException{
		return this.getProcess(cmdArray, null);
	}
	
	protected Process getProcess(String cmd, File runDir) throws IOException{
		String[] cmdArray = getCmdArray(cmd);
		return this.getProcess(cmdArray, runDir);
	}
	
	protected Process getProcess(String[] cmdArray, File runDir) throws IOException{
		Runtime runComm = Runtime.getRuntime();
		return runComm.exec(cmdArray, null, runDir);
	}

	public int excuteWaitForEnd(String cmd) throws IOException {
		return excuteWaitForEnd(cmd, null);
	}
	
	public int excuteWaitForEnd(String[] cmdArray) throws IOException {
		return excuteWaitForEnd(cmdArray, null);
	}
	
	public int excuteWaitForEnd(String cmd, File runDir) throws IOException {
		if (StringUtils.isBlank(cmd)) {
			return -1;
		}

		Process ps = this.getProcess(cmd, runDir);

		return excuteWaitForEnd(ps);
	}
	
	public int excuteWaitForEnd(String[] cmdArray, File runDir) throws IOException {
		if (cmdArray == null || cmdArray.length == 0) {
			return -1;
		}

		Process ps = this.getProcess(cmdArray, runDir);

		return excuteWaitForEnd(ps);
	}

	public int excuteWaitForEnd(Process ps) throws IOException {
		try {
			ps.getOutputStream().close();

			int i = ps.waitFor();
			int exitValue = ps.exitValue();
			log.info("command result: " + i + ";exitValue:" + exitValue);

			return exitValue;
		} catch (InterruptedException e) {
			log.error("命令阻塞失败", e);
		} finally {
			IOUtils.closeQuietly(ps.getErrorStream());
			IOUtils.closeQuietly(ps.getInputStream());
			IOUtils.closeQuietly(ps.getOutputStream());
		}

		return -1;
	}

	/**
	 * @Description: 获取包含字符串processName的进程个数
	 * @author lijun.huang
	 * @date 2014-3-2 下午10:38:51
	 * @version 1.0
	 *
	 * @param processName
	 * @return
	 * @throws IOException
	 */
	public int getProcessAmountByName(String processName) throws IOException {
		List<String> resultList = this.getCmdExecResultStrList("ps -ef ");

		if (resultList == null || resultList.isEmpty()) {
			return 0;
		}

		int amount = 0;

		for (int i = 0; i < resultList.size(); i++) {
			String lineStr = resultList.get(i);

			if (lineStr != null && lineStr.indexOf(processName) != -1) {
				amount += 1;
			}
		}

		return amount;
	}

	/**
	 * @Description: 获取包含字符串processName的进程列表
	 * @author lijun.huang
	 * @date 2014-3-2 下午10:39:37
	 * @version 1.0
	 *
	 * @param processName
	 * @return
	 * @throws IOException
	 */
	public List<String> getProcessInfoByName(String processName) throws IOException {
		List<String> resultList = this.getCmdExecResultStrList("ps -ef ");

		List<String> processInfoList = new ArrayList<String>();

		if (resultList == null || resultList.isEmpty()) {
			return processInfoList;
		}

		for (int i = 0; i < resultList.size(); i++) {
			String lineStr = resultList.get(i);

			if (lineStr != null && lineStr.indexOf(processName) != -1) {
				processInfoList.add(lineStr);
			}
		}

		return processInfoList;
	}

	/**
	 * @Description: 获取复制字符串
	 * @author lijun.huang
	 * @date 2014-3-2 下午10:39:56
	 * @version 1.0
	 *
	 * @param srcPath
	 * @param distPATH
	 * @return
	 */
	public String getCopyStr(String srcPath, String distPATH) {

		return CommandStrUtil.getUnixCopyStr(srcPath, distPATH);
	}
	
	/**
	 * 获取文件下的文件个数
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public int getFileAmount(File file) throws IOException {

		if (file == null || !file.exists()) {
			return 1;
		}
		Runtime runComm = Runtime.getRuntime();

		String[] cmdArray = getCmdArray("ls -lR|grep '^[-|d]'|wc -l");

		Process ps = runComm.exec(cmdArray, null, file);

		List<String> resultList = this.getCmdExecResultStrList(ps);
		if (resultList == null || resultList.isEmpty()) {
			return 0;
		}

		int fileAmount = 0;
		for (String line : resultList) {
			if (NumberUtils.isDigits(line)) {
				fileAmount += Integer.parseInt(line);
				break;
			}
		}

		return fileAmount;
	}

	/**
	 * /** 为目录授权
	 * 
	 * @param changeFile
	 * @throws IOException
	 */
	public void changeMod(File changeFile) throws IOException {
		String[] cmdArray = getCmdArray("chmod -R a+wxr .");

		Runtime runComm = Runtime.getRuntime();

		Process ps = runComm.exec(cmdArray, null, changeFile);

		this.excuteWaitForEnd(ps);
	}

	public void diff(String bathPath, String newPath, String runPath, String resultFile) throws IOException {
		StringBuffer buff = new StringBuffer().append("diff -rq ").append(bathPath).append(" ").append(newPath).append(" > ").append(resultFile);

		String cmdArray[] = getCmdArray(buff.toString());
		Runtime runComm = Runtime.getRuntime();

		File runFile = new File(runPath);
		Process ps = runComm.exec(cmdArray, null, runFile);

		this.excuteWaitForEnd(ps);
	}

	/**
	 * @Description: 判断该网站的Wget是否存在
	 * @author lijun.huang
	 * @date 2014年3月8日 下午1:03:56
	 * @version 1.0
	 * 
	 * @param weburl
	 * @return
	 * @throws IOException
	 */
	public boolean isWgetExist(String webUrl, List<String> processInfoList) throws IOException {

		// 添加执行完成的链接
		for (String processInfo : processInfoList) {
			if (processInfo.indexOf(webUrl) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除进程名称相关的所有进程
	 * 
	 * @param processName
	 * @throws IOException
	 */
	public void killAllProcessByName(String processName) throws IOException {
		String cmdStr = CommandStrUtil.getKillStrByName(processName);

		this.excuteShell(cmdStr);
	}

	/**
	 * 根据进程名称获取进程id列表
	 * 
	 * @param processName
	 * @return
	 */
	public List<Integer> getPIdListByName(String processName) {
		String cmd = "ps -ef|grep " + processName + "|grep -v grep|awk '{print $2}'";

		String[] cmdArray = getCmdArray(cmd);
		List<Integer> result = new ArrayList<Integer>();

		try {
			Runtime runComm = Runtime.getRuntime();
			Process ps = runComm.exec(cmdArray);

			List<String> resultList = this.getCmdExecResultStrList(ps);
			if (resultList == null || resultList.isEmpty()) {
				return result;
			}

			for (String line : resultList) {
				Integer pid = Integer.parseInt(line);
				result.add(pid);
			}
		} catch (Exception e) {
			result = null;
			log.error("", e);
		}

		return result;
	}

	public List<String> getCmdExecResultStrList(String cmd) throws IOException{
		return this.getCmdExecResultStrList(cmd, null);
	}
	
	public List<String> getCmdExecResultStrList(String cmd, File runDir) throws IOException {
		Process ps = this.getProcess(cmd, runDir);

		return getCmdExecResultStrList(ps);
	}

	public List<String> getCmdExecResultStrList(Process ps) throws IOException {
		return this.getCmdExecResultStrList(ps, charsetName);
	}
	
	public List<String> getCmdExecResultStrList(Process ps, String charsetName) throws IOException {
		List<String> cmdExecResultStrList = new ArrayList<String>();

		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			isr = new InputStreamReader(ps.getInputStream(), charsetName);
			br = new BufferedReader(isr);
			String line;

			while ((line = br.readLine()) != null) {
				cmdExecResultStrList.add(line);
			}
		} finally {
			IOUtils.closeQuietly(isr);
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(ps.getErrorStream());
			IOUtils.closeQuietly(ps.getInputStream());
			IOUtils.closeQuietly(ps.getOutputStream());
		}

		return cmdExecResultStrList;
	}
	
	public StringBuffer getCmdExecResultBuffer(String commandStr, boolean isShell) throws IOException{
		return getCmdExecResultBuffer(commandStr, isShell, null);
	}
	
	public StringBuffer getCmdExecResultBuffer(String commandStr, boolean isShell, File runDir) throws IOException {
		StringBuffer buff = new StringBuffer();

		Runtime runComm = Runtime.getRuntime();
		Process ps = null;

		if (isShell) {
			String[] cmdArray = getCmdArray(commandStr);
			ps = runComm.exec(cmdArray, null, runDir);
		} else {
			ps = runComm.exec(commandStr, null, runDir);
		}

		List<String> resultList = this.getCmdExecResultStrList(ps);
		if (resultList == null || resultList.isEmpty()) {
			return buff;
		}

		for (String line : resultList) {
			buff.append(line);
		}

		return buff;
	}

	public void changeMod(String file) throws IOException {
		File changeFile = new File(file);
		this.changeMod(changeFile);
	}

	public String getSysVersion() {
		String version = "5";
		try {
			StringBuffer buff = this.getCmdExecResultBuffer("cat /etc/redhat-release", true);
			String sysinfo = buff.toString();
			if (sysinfo.startsWith("CentOS release 5")) {
				version = "5";
			} else if (sysinfo.startsWith("CentOS release 6")) {
				version = "6";
			}
			log.info("centos version is :" + version);
		} catch (IOException e) {
			log.error("", e);
		}
		return version;
	}

}
