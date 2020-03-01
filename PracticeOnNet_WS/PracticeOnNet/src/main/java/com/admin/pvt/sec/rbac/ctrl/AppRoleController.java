/**
 * 
 */
package com.admin.pvt.sec.rbac.ctrl;

import java.util.List;
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
import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.rbac.dto.AppRoleDTO;
import com.admin.pvt.sec.rbac.service.AppRoleService;
import com.base.BaseController;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
import com.google.gson.Gson;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

@Controller
@RequestMapping(value = "/admin/pvt/sec/rbac/role/")
public class AppRoleController extends BaseController {

	static final Logger log = LoggerFactory.getLogger(AppRoleController.class);

	@Autowired
	AppRoleService appRoleService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listRoles(Model model) {
		log.info("AppRoleController :==> listRoles :==> Started");
		String target = "/admin/pvt/sec/rbac/role_master";
		log.info("AppRoleController :==> listRoles :==> Ended");
		return target;

	}// End of open

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listRolePaginated(HttpServletRequest request, HttpServletResponse response, String roleName,
			String departmentNameId) {

		log.info("AppRoleController :==> listRolePaginated :==> Started");
		DataTableResults<AppRoleDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals(""))
				whereClauseForBaseQuery = "  ar.department_id=" + Integer.parseInt(departmentNameId);
			if (!roleName.isEmpty())
				whereClauseForBaseQuery = "  ar.role_name Like %" + roleName + "%";
			if (!departmentNameId.isEmpty() && !roleName.isEmpty())
				whereClauseForBaseQuery = "  ar.department_id=" + Integer.parseInt(departmentNameId)
						+ " and ar.role_name Like '%" + roleName + "%'";
			dataTableResult = appRoleService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("AppRoleController :==> listRolePaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}
	
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AppRoleController :==> getRecord :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(appRoleService.getReordById(id));
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

		log.info("AppRoleController :==> getRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid AppRoleDTO appRoleDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("AppRoleController :==> saveAndUpdateRecord :==> Started");

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
				jsonResponse.setFormObject(appRoleService.saveAndUpdate(appRoleDTO));
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

		log.info("AppRoleController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AppRoleController :==> deleteRecordById :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(appRoleService.deleteRecordById(id));
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

		log.info("AppRoleController :==> deleteRecordById :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("AppRoleController :==> deleteSelectedRecords :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			appRoleService.deleteMultipleRecords(recordIdArray);
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

		log.info("AppRoleController :==> deleteSelectedRecords :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public String roleList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("AppRoleController :==> roleList :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			List<NameValue> roleList = appRoleService.getAppRoleList(id);
			jsonResponse.setComboList(roleList);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
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
		log.info("AppRoleController :==> roleList :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "listRoleBasedUsers", method = RequestMethod.GET)
	@ResponseBody
	public String listRoleBasedUsers(@RequestParam Integer id, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("AppRoleController :==> listRoleBasedUsers :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			List<NameValue> roleBasedUserList = appRoleService.getAppRoleBasedUsersList(id);

			jsonResponse.setComboList(roleBasedUserList);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
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
		log.info("AppRoleController :==> listRoleBasedUsers :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

}// End of RoleMasterController
