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
@RequestMapping(value = "/admin/pvt/api_mgr/manage_client/")
public class MonitorApiClientController {

	static final Logger log = LoggerFactory.getLogger(MonitorApiClientController.class);
	//@Autowired
	//MonitorApiClientService monitorApiClientService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listCities(Model model) {
		log.info("MonitorApiClientController :==> listClient :==> Started");
		String target = "/admin/pvt/api_mgr/manage_client";
		log.info("MonitorApiClientController :==> listClient :==> End");
		return target;
	}
}//End of MonitorApiClientController
