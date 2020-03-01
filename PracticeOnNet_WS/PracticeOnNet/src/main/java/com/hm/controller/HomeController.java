/**
 * 
 */
package com.hm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.custom.exception.CustomBusinessException;
import com.custom.exception.ExceptionApplicationUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Sanjeev
 *
 */

@Controller
public class HomeController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/")
	public String welcome() {
		
		String target="hm";
		LOGGER.debug("This is a debug message");
	    LOGGER.info("This is an info message");
	    LOGGER.warn("This is a warn message");
	    LOGGER.error("This is an error message"); 
	   
	    	int j = 3;
	        int y;
	        try {
	            y = 21 / j;
	            // Write the code to be examined under exception strategy
	            if (y > 3) {
	                //Do some business process here            	
	            } else {
	                //Now prepare the business exception to be thrown  	            
	                 ExceptionApplicationUtility.wrapBusinessException(new CustomBusinessException("be_1.error"));            
	            }
	    	
	    }catch(Exception e) {ExceptionApplicationUtility.wrapRunTimeException(e);}
				
		return target;		
	}//End of welcome

}//End of HomeController
