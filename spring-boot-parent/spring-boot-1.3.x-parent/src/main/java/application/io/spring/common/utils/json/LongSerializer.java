package application.io.spring.common.utils.json;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LongSerializer implements JsonSerializer<Long>, JsonDeserializer<Long> {

	@Override
	public Long deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		
		String str = json.getAsString();
		
		if(StringUtils.isBlank(str)){
			return null;
		}
		
		return Long.parseLong(str);
	}

	@Override
	public JsonElement serialize(Long arg0, Type arg1, JsonSerializationContext arg2) {	
		return new JsonPrimitive(arg0);
	}  
}  
