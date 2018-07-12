package application.io.spring.bottomware.utility;

import java.util.Map;

import application.io.spring.common.utils.json.GsonUtils;

public class BeanUtils {

	private BeanUtils() {}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object bean) {
		
		if (bean != null) {
			
			return GsonUtils.GSON.fromJson(GsonUtils.GSON.toJson(bean), Map.class);
			
		} else {
			
			return null;
		}
	}
}
