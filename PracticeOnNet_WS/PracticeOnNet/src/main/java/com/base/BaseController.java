/**
 * 
 */
package com.base;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.PracticeOnNetApplication;

/**
 * @author Sanjeev
 *
 */
@Controller
public class BaseController {
	/**
     * {@inheritDoc}
     * Sets the require fields and the disallowed fields from the
     * HttpServletRequest.
     *
     * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest,
     * org.springframework.web.bind.ServletRequestDataBinder)
     */

	@InitBinder
	protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) throws Exception {
	   // binder.setRequiredFields(new String[] { "description", "serviceId", "name", "allowedToProxy", "enabled", "ssoEnabled", "anonymousAccess", "evaluationOrder" });
	   // binder.setDisallowedFields(new String[] { "id" });
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	
	// it will handle all request for /viewSessionData
	@RequestMapping(value = "/viewSessionData", method = RequestMethod.GET)
	    public java.util.Map<String, Integer> start(HttpServletRequest request) {
		    // create session if not exists and get attribute
	        Integer integer = (Integer) request.getSession().getAttribute("hitCounter");	        
	        if (integer == null) {
	            integer = 0;
	            integer++;
	         // replace session attribute
	            request.getSession().setAttribute("hitCounter", integer);               
	        } else {
	            integer++;
	         // replace session attribute
	            request.getSession().setAttribute("hitCounter", integer);          
	        }
	        java.util.Map<String, Integer> hitCounter = new HashMap<>();
	        hitCounter.put("Hit Counter", integer);
	        return hitCounter;
	    }
	
	
	// it will handle all request for /viewSessionData
		@RequestMapping(value = "/restartServer", method = RequestMethod.GET)
		    public void restartServer() {
			try {
				
			//PracticeOnNetApplication.restart();
			}catch(Exception ex) {ex.printStackTrace();}
		}
	
}
