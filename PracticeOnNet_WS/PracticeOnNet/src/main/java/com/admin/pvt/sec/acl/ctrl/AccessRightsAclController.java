/**
 * 
 */
package com.admin.pvt.sec.acl.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JsonResponse.JsonResponse;
import com.admin.pvt.sec.acl.dto.DomainMasterDTO;
import com.admin.pvt.sec.acl.service.AccessRightsAclService;
import com.admin.pvt.sec.acl.service.DomainService;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/admin/pvt/sec/acl/aclPermissions/")
public class AccessRightsAclController {

	static final Logger log = LoggerFactory.getLogger(AccessRightsAclController.class);
	@Autowired
	DomainService domainService;

	@Autowired
	AccessRightsAclService accessRightsAclService;

	@RequestMapping(value = "")
	public String listAccessRightsAcl(Model model) {
		log.info("AccessRightsAclController :==> listAccessRightsAcl :==> Started");
		String target = "/admin/pvt/sec/acl/domain-permissions";
		log.info("AccessRightsAclController :==> listAccessRightsAcl :==> Ended");
		return target;
	}// End of openRecordDomain

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listAccessRightsAclPaginated(HttpServletRequest request, HttpServletResponse response,
			String departmentNameId, String moduleNameId) {
		log.info("AccessRightsAclController :==> listAccessRightsAclPaginated :==> Started");
		DataTableResults<DomainMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && moduleNameId.isEmpty()
					&& moduleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and adm.module_id="
						+ Integer.parseInt(moduleNameId);
			dataTableResult = domainService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AccessRightsAclController :==> listAccessRightsAclPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("AccessRightsAclController :==> getRecord :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(accessRightsAclService.getReordById(id));
			jsonResponse.setStatus(true);
			// jsonResponse.setStatusMsg("Record found.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("AccessRightsAclController :==> getRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "loadActionsOnRoleSelection", method = RequestMethod.GET)
	@ResponseBody
	public String loadActionsOnRoleSelection(@RequestParam Integer roleId, @RequestParam Integer domainId,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("AccessRightsAclController :==> loadActionsOnRoleSelection :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(accessRightsAclService.loadActionsOnRoleSelection(roleId, domainId));
			jsonResponse.setStatus(true);
			// jsonResponse.setStatusMsg("Record found.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}

		log.info("AccessRightsAclController :==> loadActionsOnRoleSelection :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "loadActionsOnUserSelection", method = RequestMethod.GET)
	@ResponseBody
	public String loadActionsOnUserSelection(@RequestParam Integer roleId, @RequestParam Integer userId,
			@RequestParam Integer domainId, HttpServletRequest request, HttpServletResponse response) {
		log.info("AccessRightsAclController :==> loadActionsOnUserSelection :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(accessRightsAclService.loadActionsOnUserSelection(roleId, userId, domainId));
			jsonResponse.setStatus(true);
			// jsonResponse.setStatusMsg("Record found.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("AccessRightsAclController :==> loadActionsOnUserSelection :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestParam String accessRightsAclId, @RequestParam String domainId,
			@RequestParam String roleId, @RequestParam String userId, @RequestParam String[] recordIdArray,
			@RequestParam String permissionBase,

			HttpServletRequest request, HttpServletResponse response) {
		log.info("AccessRightsAclController :==> saveAndUpdateRecord :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			// Implement business logic to save record into database
			jsonResponse.setFormObject(accessRightsAclService.saveAndUpdate(accessRightsAclId, domainId, roleId, userId,
					recordIdArray, permissionBase));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Updated successfully.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}

		log.info("AccessRightsAclController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

}// End of AccessRightsAclController