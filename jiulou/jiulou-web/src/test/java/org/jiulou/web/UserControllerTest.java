package org.jiulou.web;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.util.ApplicationContextFactory;
import org.unitils.spring.util.ApplicationContextManager;

public class UserControllerTest {
	
    private static HandlerMapping handlerMapping;  
    private static HandlerAdapter handlerAdapter;  
      
    private static MockServletContext msc;  
    
    @BeforeClass  
    public static void setUp() {
    	
        String[] configs = {
                "/spring/applicationContext-*.xml",
                "/web/dispatcher-servlet.xml" };
        XmlWebApplicationContext context = new XmlWebApplicationContext();    
        context.setConfigLocations(configs);
        msc = new MockServletContext();
        context.setServletContext(msc);
        context.refresh();
        msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context); 
    
        handlerMapping = (HandlerMapping) context.getBean(DefaultAnnotationHandlerMapping.class);
        Assert.assertNotNull("Can't get handlerMapping. ",handlerMapping);
        handlerAdapter = (HandlerAdapter) context.getBean(AnnotationMethodHandlerAdapter.class);
        Assert.assertNotNull("Can't get handlerAdapter. ",handlerAdapter);
    }
  
    @Test  
    public void list() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
          
        request.setRequestURI("/users/add.do");
        request.addParameter("password", "123");
        request.addParameter("email", "email@email.com");
        request.addParameter("username", "username");
        
        request.setMethod("POST");
          
        //HandlerMapping  
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        Assert.assertEquals(true, chain.getHandler() instanceof UserController);
          
        //HandlerAdapter
        final ModelAndView mav = handlerAdapter.handle(request, response, chain.getHandler());  
          
        //Assert logic
        Assert.assertEquals("addSucc.jsp", mav.getViewName());
    }
}
