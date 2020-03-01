/**
 * 
 */
package com.admin.pvt.api_mgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/admin/pvt/api_mgr/api_permission/")
public class ApiPermissionController {

	static final Logger log = LoggerFactory.getLogger(ApiPermissionController.class);
	//@Autowired
	//ApiPermissionService apiPermissionService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listCities(Model model) {
		log.info("ApiPermissionController :==> listCities :==> Started");
		String target = "/admin/pvt/api_mgr/api_permission";
		log.info("ApiPermissionController :==> listCities :==> End");
		return target;
	}
}//End of ApiPermissionController
