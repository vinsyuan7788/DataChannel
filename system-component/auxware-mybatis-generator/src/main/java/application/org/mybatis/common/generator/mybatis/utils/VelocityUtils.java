package application.org.mybatis.common.generator.mybatis.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityUtils {

	private static final Map<String, VelocityEngine> engineMap = new ConcurrentHashMap<String, VelocityEngine>();
	private static final int size = 10;
	private static final String CLASS_PATH_PRE = "templates/";
	
	private static VelocityEngine getVelocityEngine(String name){
		return engineMap.get(name);
	}
	
	public static String replace(String templateFileName, Map<String, Object> params) throws IOException{
		VelocityEngine ve = getVelocityEngine(templateFileName);
		
		if(ve == null){
			ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			if(engineMap.size() > size){
				engineMap.clear();
			}
			
			engineMap.put(templateFileName, ve);
		}
		
		VelocityContext context = new VelocityContext();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
		
		Template template = ve.getTemplate(CLASS_PATH_PRE + templateFileName);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
}
