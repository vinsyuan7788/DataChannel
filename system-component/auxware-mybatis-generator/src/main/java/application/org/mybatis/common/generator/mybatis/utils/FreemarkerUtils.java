package application.org.mybatis.common.generator.mybatis.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtils {

	public static String replaceStr(String templateStr, Map<String, Object> params) throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		String template = "content";
		stringLoader.putTemplate(template, templateStr);
		cfg.setTemplateLoader(stringLoader);
		Template templateCon = cfg.getTemplate(template);
		StringWriter writer = new StringWriter();
		templateCon.process(params, writer);
		return writer.toString();
	}
	
}
