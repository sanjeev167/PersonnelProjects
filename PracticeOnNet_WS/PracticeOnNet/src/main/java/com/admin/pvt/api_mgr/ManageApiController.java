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
@RequestMapping(value = "/admin/pvt/api_mgr/manage_api/")
public class ManageApiController {

	static final Logger log = LoggerFactory.getLogger(ManageApiController.class);
	//@Autowired
	//ManageApiService manageApiService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listCities(Model model) {
		log.info("ManageApiController :==> listApies :==> Started");
		String target = "/admin/pvt/api_mgr/manage_api";
		log.info("ManageApiController :==> listApies :==> End");
		return target;
	}
}//End of ManageApiController
