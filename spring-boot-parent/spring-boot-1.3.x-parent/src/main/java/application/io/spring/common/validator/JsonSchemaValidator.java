package application.io.spring.common.validator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class JsonSchemaValidator {
	
	/**
	* @Title: validateJsonByFgeByJsonString 
	* @Description: TODO(校验json合法性，真正的json业务内容会被嵌套，需要解析；将检验结构和解析内容返回) 
	* @param @param json 待校验的字符串 {"data":{"phone":"13433233222","email":"3223@qq.com"}}   实际的json内容是 {"phone":"13433233222","email":"3223@qq.com"}
	* @param @param schema	校验的标准
	* @param @param jsonKey		包含真正内容的key，此例子相当于 data
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public static Map<String, Object> validateJsonByFgeByJsonString(String json, String schema,String jsonKey) {
        Map<String, Object> result = new HashMap<String, Object>();
        ProcessingReport report = null;
        JsonNode jsonNode = JsonSchemaValidator.getJsonNodeFromString(json);
		JsonNode schemaNode = JsonSchemaValidator.getJsonNodeFromString(schema);
        report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        //真正业务内容的json
        Object jsonValue = JSONObject.fromObject(json).get(jsonKey);
        if (report.isSuccess()) {
            // 校验成功
            result.put("message", "校验成功！");
            result.put(jsonKey, jsonValue);
            result.put("success", true);
            return result;
        } else {
            System.out.println("校验失败！");
            Iterator<ProcessingMessage> it = report.iterator();
            String ms = "";
            while (it.hasNext()) {
                ProcessingMessage pm = it.next();
                if (!LogLevel.WARNING.equals(pm.getLogLevel())) {
                    ms += pm;
                }
            }
            result.put("message", "校验失败！" + ms);
            result.put(jsonKey, jsonValue);
            result.put("success", false);
            return result;
        }
	}
	
	
    public static Map<String, Object> validateJsonByFgeByJsonNode(JsonNode jsonNode, JsonNode schemaNode) {
        Map<String, Object> result = new HashMap<String, Object>();
        ProcessingReport report = null;
        report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (report.isSuccess()) {
            // 校验成功
            result.put("message", "校验成功！");
            result.put("success", jsonNode.get("data"));
            result.put("success", true);
            return result;
        } else {
            System.out.println("校验失败！");
            Iterator<ProcessingMessage> it = report.iterator();
            String ms = "";
            while (it.hasNext()) {
                ProcessingMessage pm = it.next();
                if (!LogLevel.WARNING.equals(pm.getLogLevel())) {
                    ms += pm;
                }

            }
            result.put("message", "校验失败！" + ms);
            result.put("success", false);
            return result;
        }
    }

    public static JsonNode getJsonNodeFromString(String jsonStr) {
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonLoader.fromString(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    public static JsonNode getJsonNodeFromFile(String filePath) {
        JsonNode jsonNode = null;
        try {
            jsonNode = new JsonNodeReader().fromReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

}
