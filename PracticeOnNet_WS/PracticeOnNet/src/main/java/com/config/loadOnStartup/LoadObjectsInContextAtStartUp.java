/**
 * 
 */
package com.config.loadOnStartup;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.admin.pvt.menu_mgr.service.MenuManagerService;

/**
 * @author Sanjeev
 *
 */
@Component
@Scope(value = "prototype")
public class LoadObjectsInContextAtStartUp implements InitializingBean {
 
    static final Logger LOG = LoggerFactory.getLogger(LoadObjectsInContextAtStartUp.class);
	static int initializationOrder=0;
	
	@Autowired
	MenuManagerService menuManagerService;
	
	@Autowired
	ServletContext servletContext;
	
	
	
	
	 /**
		 * Constructor Injection: If you are injecting fields using Constructor
		 * Injection, you can simply include your logic in a constructor:
		 **/
	    public LoadObjectsInContextAtStartUp() {
	    	initializationOrder++;
	        LOG.info("Sanjeev : Loading through Constructor. InitializationOrder [ "+initializationOrder+" ]");
	        
	    }
    /****/
    @Override
    public void afterPropertiesSet() throws Exception {
    	initializationOrder++;
        LOG.info("Sanjeev : Loading through InitializingBean. InitializationOrder [ "+initializationOrder+" ]");
    }
 
	/**
	 * @PostConstruct annotation can be used for annotating a method that should be
	 *  run once immediately after the bean’s initialization. Keep in mind that the 
	 *  annotated method will be executed by Spring even if there is nothing to inject.
	 **/
    @PostConstruct
    public void postConstruct() {
    	initializationOrder++;
        LOG.info("Sanjeev : Loading through PostConstruct. InitializationOrder [ "+initializationOrder+" ]");
    }
 
    /**
     * @Bean initMethod attribute : The initMethod property can be used to execute a method after a bean’s initialization.
     * the initialization method specified as init-method in XML
     * **/
    public void init() {
    	initializationOrder++;
        LOG.info("Sanjeev : Loading through init-method. InitializationOrder [ "+initializationOrder+" ]");
    }
    
   
}//End of LoadObjectsInContextAtStartUp
