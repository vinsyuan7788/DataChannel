package application.io.spring.technique.shiro.gateway.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	This is a class to test Shiro coding (both encoding and decoding)
 * 
 * @author vinsy
 *
 */
@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/shiro/coding")
public class ShiroCodingController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/testCoding", method = RequestMethod.GET)
	public Map<String, Object> testCoding() throws Exception {
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		
		String originalData = "Hello Shiro!";
		result.put("originalData", originalData);
		
		// Here performs Base64 encoding and decoding
		String encodedDataByBase64 = Base64.encodeToString(originalData.getBytes());
		result.put("encodedDataByBase64", encodedDataByBase64);
		
		String decodedDataByBase64 = Base64.decodeToString(encodedDataByBase64);
		result.put("decodedDataByBase64", decodedDataByBase64);
		
		// Here performs Hex encoding and decoding
		String encodedDataByHex = Hex.encodeToString(originalData.getBytes());
		result.put("encodedDataByHex", encodedDataByHex);
		
		String decodedDataByHex = new String(Hex.decode(encodedDataByHex));
		result.put("decodedDataByHex", decodedDataByHex);
		
		// Return data
		data.put("status", 1);
		data.put("msg", "success");
		data.put("result", result);
		return data;
	}
}
