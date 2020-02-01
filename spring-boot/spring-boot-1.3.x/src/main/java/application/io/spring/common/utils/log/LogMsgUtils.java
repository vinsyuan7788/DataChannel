package application.io.spring.common.utils.log;

import lombok.extern.log4j.Log4j2;

/**
 * 	【12/19/2019】日志信息相关工具类
 * 
 * @author vinsy
 *
 */
@Log4j2
public class LogMsgUtils {

	private static final String MSG_PREFIX = "=== ";
	private static final String MSG_SUFFIX = " ===";
	private static final String MSG_SEPARATOR = " | ";
	private static final String PARAM_SEPARATOR = ": ";
	private static final String PARAM_DEFAULT_VALUE = "null";
	private static final String ERROR_DEFAULT_MSG = "记录异常信息";
	
	// 私有化构造器: 该类无法被实例化
	private LogMsgUtils() {}
	
	public static void logDebug(String methodName, String msg) {
		log.debug(MSG_PREFIX + getEntityMsg(methodName) + MSG_SEPARATOR + msg + MSG_SUFFIX);
	}
	
	public static void logDebug(String methodName, String msg, String[] parameterNames, Object[] parameterValues) {
		log.debug(MSG_PREFIX + getEntityMsg(methodName) + MSG_SEPARATOR + msg + getParameterMsg(parameterNames, parameterValues) + MSG_SUFFIX);
	}
	
	public static void logDebug(String className, String methodName, String msg) {
		log.debug(MSG_PREFIX + getEntityMsg(className, methodName) + MSG_SEPARATOR + msg + MSG_SUFFIX);
	}
	
	public static void logDebug(String className, String methodName, String msg, String[] parameterNames, Object[] parameterValues) {
		log.debug(MSG_PREFIX + getEntityMsg(className, methodName) + MSG_SEPARATOR + msg + getParameterMsg(parameterNames, parameterValues) + MSG_SUFFIX);
	}
	
	public static void logInfo(String methodName, String msg) {
		log.info(MSG_PREFIX + getEntityMsg(methodName) + MSG_SEPARATOR + msg + MSG_SUFFIX);
	}
	
	public static void logInfo(String methodName, String msg, String[] parameterNames, Object[] parameterValues) {
		log.info(MSG_PREFIX + getEntityMsg(methodName) + MSG_SEPARATOR + msg + getParameterMsg(parameterNames, parameterValues) + MSG_SUFFIX);
	}
	
	public static void logInfo(String className, String methodName, String msg) {
		log.info(MSG_PREFIX + getEntityMsg(className, methodName) + MSG_SEPARATOR + msg + MSG_SUFFIX);
	}
	
	public static void logInfo(String className, String methodName, String msg, String[] parameterNames, Object[] parameterValues) {
		log.info(MSG_PREFIX + getEntityMsg(className, methodName) + MSG_SEPARATOR + msg + getParameterMsg(parameterNames, parameterValues) + MSG_SUFFIX);
	}
	
	public static void logError(String methodName, Throwable t) {
		log.error(MSG_PREFIX + getEntityMsg(methodName) + MSG_SEPARATOR + ERROR_DEFAULT_MSG + MSG_SUFFIX, t);
	}
	
	public static void logError(String className, String methodName, Throwable t) {
		log.error(MSG_PREFIX + getEntityMsg(className, methodName) + MSG_SEPARATOR + ERROR_DEFAULT_MSG + MSG_SUFFIX, t);
	}
	
	private static String getEntityMsg(String methodName) {
		return methodName + "中";
	}
	
	private static String getEntityMsg(String className, String methodName) {
		return className + "的" + getEntityMsg(methodName);
	}
	
	private static String getParameterMsg(String[] parameterNames, Object[] parameterValues) {
		if (parameterNames == null) {
			return "";
		} else {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < parameterNames.length; i++) {
				String parameterName = parameterNames[i];
				Object parameterValue = parameterValues != null && i < parameterValues.length ? parameterValues[i] : PARAM_DEFAULT_VALUE;
				stringBuffer.append(MSG_SEPARATOR + parameterName + PARAM_SEPARATOR + parameterValue);
			}
			return stringBuffer.toString();
		}
	}
}
