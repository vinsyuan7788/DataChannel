package application.io.spring.common.utils.json;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 	This is a class to provide utility regarding GSON
 * 
 * @author vinsy
 *
 */
@SuppressWarnings("unchecked")
public class GsonUtils {

	// Privatize the constructor so that this class cannot be instantiated
	private GsonUtils() {}
	
	public static final Gson GSON = new GsonBuilder()
			.serializeNulls()
			.registerTypeAdapter(Long.class, new LongSerializer())
			.registerTypeAdapter(Date.class, new DateSerializer())
			.create();
	
	public static Map<String, Object> Object2Map(Object obj) {
		
		if(obj == null){
			return new HashMap<String,Object>();
		}
		
		return GSON.fromJson(GSON.toJson(obj), Map.class);
	}
	
	public static Map<String, String> Object2MapString(Object obj) {
		
		if(obj == null){
			return new HashMap<String,String>();
		}
		
		return GSON.fromJson(GSON.toJson(obj), Map.class);
	}
}
