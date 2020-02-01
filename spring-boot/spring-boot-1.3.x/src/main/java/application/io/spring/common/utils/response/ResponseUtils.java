package application.io.spring.common.utils.response;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import application.io.spring.common.utils.io.IOUtils;
import application.io.spring.common.utils.log.LogMsgUtils;
import jodd.json.JsonSerializer;

/**
 * 	This is a utility class regarding response
 * 
 * @author vinsy
 *
 */
public class ResponseUtils {

	// 私有化构造器
	private ResponseUtils() {}
	
	/**
	 * 	获取保存结果的映射
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K, V> Map<K, V> getResult() {
		return new HashMap<K, V>();
	}
	
	/**
	 * 	获取返回数据
	 * 
	 * @param status
	 * @param msg
	 * @param result
	 * @return
	 */
	public static <K, V> Map<String, Object> getReturnData(int status, String msg, Map<K, V> result) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(ResponseFormatEnum.STATUS.getKey(), status);
		data.put(ResponseFormatEnum.MSG.getKey(), msg);
		data.put(ResponseFormatEnum.RESULT.getKey(), result);
		return data;
	}
	
	/**
	 * 	返回响应结果（JSON格式）
	 * 
	 * @param response
	 * @param result
	 */
	public static <K, V> void writeResponse(HttpServletResponse response, int status, String msg, Map<K, V> result) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(ResponseFormatEnum.STATUS.getKey(), status);
		data.put(ResponseFormatEnum.MSG.getKey(), msg);
		data.put(ResponseFormatEnum.RESULT.getKey(), result);
		
		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(new JsonSerializer().serialize(data).getBytes());
			servletOutputStream.flush();
		} catch (Exception e) {
			LogMsgUtils.logError("ResponseUtils", "writeResponse", e);
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}
}
