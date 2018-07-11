package application.io.spring.common.utils.json;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import application.io.spring.common.utils.date.DateUtils;

public class DateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private static String DATE_TRANSFER_TYPE;
	
	static{
//		try {
//			Properties properties = PropUtil.getProperties("/config/utils.properties", CommonErrorType.class);
//			DATE_TRANSFER_TYPE = properties.getProperty("OUT_DATE_TRANSFER_TYPE", "");
//		} catch (IOException e) {
//			log.error("",e);
//		}
		DATE_TRANSFER_TYPE = "ISO_DATETIME_TIME_ZONE_FORMAT";
	}
	
	@Override
	public Date deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		
		String str = json.getAsString();
		
		if(StringUtils.isBlank(str)){
			return null;
		}
		
		try {
			return DateUtils.formateDate(str);
		} catch (ParseException e) {
			throw new JsonParseException("Conversion from String to Date fails: " + str, e);
		}
	}

	@Override
	public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
		if("ISO_DATETIME_TIME_ZONE_FORMAT".equals(DATE_TRANSFER_TYPE)){
			return new JsonPrimitive(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(arg0));
		}else{
			return new JsonPrimitive(DateUtils.PATTERN_CLASSICAL_FORMAT.format(arg0));			
		}
	}
}