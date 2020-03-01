/**
 * 
 */
package com.admin.pvt.api_mgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.admin.pvt.masters.service.CityService;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/admin/pvt/api_mgr/test_api/")
public class TestApiController {

	static final Logger log = LoggerFactory.getLogger(TestApiController.class);
	@Autowired
	CityService cityService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listCities(Model model) {
		log.info("TestApiController :==> listTestApies :==> Started");
		String target = "/admin/pvt/api_mgr/test_api";
		log.info("TestApiController :==> listTestApies :==> End");
		return target;
	}
}//End of TestApiController
