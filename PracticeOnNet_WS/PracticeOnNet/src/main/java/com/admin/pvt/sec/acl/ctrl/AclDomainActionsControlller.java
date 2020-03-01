/**
 * 
 */
package com.admin.pvt.sec.acl.ctrl;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JsonResponse.JsonResponse;
import com.admin.pvt.sec.acl.dto.DomainActionMasterDTO;
import com.admin.pvt.sec.acl.service.DomainActionService;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
@Controller
@RequestMapping(value = "/admin/pvt/sec/acl/dmnActionMaster/")
public class AclDomainActionsControlller {

	static final Logger log = LoggerFactory.getLogger(AclDomainActionsControlller.class);

	@Autowired
	DomainActionService domainActionService;

	@RequestMapping(value = "")
	public String listDomainActions(Model model) {
		log.info("AclDomainActionsControlller :==> listDomainActions :==> Started");

		String target = "/admin/pvt/sec/acl/domain_actions_master";

		log.info("AclDomainActionsControlller :==> listDomainActions :==> Ended");

		return target;
	}// End of openRecordDomain

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listDomainOperationPaginated(HttpServletRequest request, HttpServletResponse response,
			String departmentNameId, String moduleNameId, String domainNameId) {
		log.info("AclDomainActionsControlller :==> listDomainOperationPaginated :==> Started");
		DataTableResults<DomainActionMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && moduleNameId.isEmpty()
					&& moduleNameId.equals("") && domainNameId.isEmpty() && domainNameId.equals(""))
				whereClauseForBaseQuery = " mm.department_id=" + Integer.parseInt(departmentNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals("") && domainNameId.isEmpty() && domainNameId.equals(""))
				whereClauseForBaseQuery = " mm.department_id=" + Integer.parseInt(departmentNameId)
						+ " and adm.module_id=" + Integer.parseInt(moduleNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals("") && !domainNameId.isEmpty() && !domainNameId.equals(""))
				whereClauseForBaseQuery = " mm.department_id=" + Integer.parseInt(departmentNameId)
						+ " and adm.module_id=" + Integer.parseInt(moduleNameId) + " and adam.acl_domain_master_id="
						+ Integer.parseInt(domainNameId);
			dataTableResult = domainActionService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AclDomainActionsControlller :==> listDomainOperationPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("AclDomainActionsControlller :==> getRecord :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(domainActionService.getReordById(id));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record found.");
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

		log.info("AclDomainActionsControlller :==> getRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid DomainActionMasterDTO domainActionMasterDTO,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		log.info("AclDomainActionsControlller :==> saveAndUpdateRecord :==> Started");
		JsonResponse jsonResponse = new JsonResponse();

		try {
			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errors = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
				jsonResponse.setStatus(false);
				jsonResponse.setStatusMsg("");
				jsonResponse.setFieldErrMsgMap(errors);
			} else {
				// Implement business logic to save record into database
				jsonResponse.setFormObject(domainActionService.saveAndUpdate(domainActionMasterDTO));
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record has been saved or updated successfully.");
			}
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

		log.info("AclDomainActionsControlller :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("AclDomainActionsControlller :==> listDomainActions :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(domainActionService.deleteRecordById(id));
			jsonResponse.setStatusMsg("Record has been deleted successfully.");
			jsonResponse.setStatus(true);
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
		log.info("AclDomainActionsControlller :==> deleteRecordById :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("AclDomainActionsControlller :==> deleteSelectedRecords :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			domainActionService.deleteMultipleRecords(recordIdArray);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All selected records have been deleted.");
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

		log.info("AclDomainActionsControlller :==> deleteSelectedRecords :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

}// End of StateMasterController
