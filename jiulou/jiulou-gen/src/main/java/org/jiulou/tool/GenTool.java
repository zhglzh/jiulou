package org.jiulou.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class GenTool {
	
	public GenTool(){
		init();
	}
	private Configuration cfg;
	private TableMetadata tm = new TableMetadata();
	
	private void init() {
        // Initialize the FreeMarker configuration;
        // - Create a configuration instance
        cfg = new Configuration();
        // - Templates are stoted in the WEB-INF/templates directory of the Web app.
        cfg.setClassForTemplateLoading(GenTool.class, "templates");
        // - Set update dealy to 0 for now, to ease debugging and testing.
        //   Higher value should be used in production environment.
        cfg.setTemplateUpdateDelay(0);
        // - Set an error handler that prints errors so they are readable with
        //   a HTML browser.
        cfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        // - Use beans wrapper (recommmended for most applications)
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        // - Set the default charset of the template files
        cfg.setDefaultEncoding("UTF-8");
        // - Set the charset of the output. This is actually just a hint, that
        //   templates may require for URL encoding and for generating META element
        //   that uses http-equiv="Content-type".
        cfg.setOutputEncoding("UTF-8");
        // - Set the default locale
        cfg.setLocale(Locale.US);
	}
	
	public void parseMetadata(String str){
		tm.parse(str);
	}
	
	public String genDto() throws IOException, TemplateException{
        // Get the templat object
        Template t = cfg.getTemplate("vo.ftl");
        Writer out = new StringWriter();
        
        Map<String,Object> rootMap = new HashMap<String,Object>();
        rootMap.put("message", "Test message ok.");
        rootMap.put("tm", tm);
//        rootMap.put("cm", cm);
//        rootMap.put("columns", tm.getColumns());
        t.process(rootMap, out);
        
        return out.toString();
	}
	
	public String genSqlMapper() throws IOException, TemplateException{
        // Get the templat object
        Template t = cfg.getTemplate("sqlMapper.ftl");
        Writer out = new StringWriter();
        
        Map<String,Object> rootMap = new HashMap<String,Object>();
        rootMap.put("message", "Test message ok.");
        rootMap.put("tm", tm);
//        rootMap.put("cm", cm);
//        rootMap.put("columns", tm.getColumns());
        t.process(rootMap, out);
        
        return out.toString();
	}	
}
