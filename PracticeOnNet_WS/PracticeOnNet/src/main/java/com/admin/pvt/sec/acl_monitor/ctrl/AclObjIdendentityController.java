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

import com.admin.pvt.sec.acl_monitor.dto.AclObjectDTO;
import com.admin.pvt.sec.acl_monitor.service.AclObjIdendentityService;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/admin/pvt/sec/acl/monitor/objIdentity/")
public class AclObjIdendentityController {

	static final Logger log = LoggerFactory.getLogger(AclObjIdendentityController.class);

	@Autowired
	AclObjIdendentityService aclObjIdendentityService;

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listAclEntryPaginated(HttpServletRequest request, HttpServletResponse response, String sid,
			String classWithPkg) {

		log.info("AclObjIdendentityController :==> listAclEntryPaginated :==> Started");
		DataTableResults<AclObjectDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";
			if (!sid.isEmpty() && classWithPkg.isEmpty())
				whereClauseForBaseQuery = "where sid LIKE '%" + sid + "%'";
			if (sid.isEmpty() && !classWithPkg.isEmpty())
				whereClauseForBaseQuery = "where class LIKE '%" + classWithPkg + "%'";

			if (!sid.isEmpty() && !classWithPkg.isEmpty())
				whereClauseForBaseQuery = "where sid LIKE '%" + sid + "%' and class LIKE '%" + classWithPkg + "%'";
			dataTableResult = aclObjIdendentityService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AclObjIdendentityController :==> listAclEntryPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

}// End of AclObjIdendentityController
