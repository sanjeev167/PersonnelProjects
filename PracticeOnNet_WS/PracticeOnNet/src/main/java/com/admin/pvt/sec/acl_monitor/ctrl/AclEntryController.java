/**
 * 
 */
package com.admin.pvt.sec.acl_monitor.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.pvt.sec.acl_monitor.dto.AclEntryDTO;
import com.admin.pvt.sec.acl_monitor.service.AclEntryService;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/admin/pvt/sec/acl/monitor/entry/")
public class AclEntryController {

	static final Logger log = LoggerFactory.getLogger(AclEntryController.class);
	@Autowired
	AclEntryService aclEntryService;

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listAclEntryPaginated(HttpServletRequest request, HttpServletResponse response,
			String objectIdentity) {

		log.info("AclEntryController :==> listAclEntryPaginated :==> Started");
		DataTableResults<AclEntryDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";
			if (!objectIdentity.isEmpty())
				whereClauseForBaseQuery = "where object_id_class=" + Integer.parseInt(objectIdentity);
			dataTableResult = aclEntryService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AclEntryController :==> listAclEntryPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}
}// End of AclEntryController
