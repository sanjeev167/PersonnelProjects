/**
 * 
 */
package com.config.security.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sanjeev
 *
 */
public interface UrlAndRoleMatcherService {

	public Map<String,ArrayList<String>> getUrlAndRoleMatcherList();
}//End of UrlAndRoleMatcherService
