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
import com.admin.pvt.sec.rbac.dto.AppAdminUserDTO;
import com.admin.pvt.sec.rbac.service.UserAccountService;
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
@RequestMapping(value = "/admin/pvt/sec/rbac/appAdminUser/")
public class UserAccountController extends BaseController {
	static final Logger log = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	UserAccountService userAccountService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listUserAccounts(Model model) {
		log.info("UserAccountController :==> listUserAccounts :==> Started");
		String target = "/admin/pvt/sec/rbac/appAdminUser";
		log.info("UserAccountController :==> listUserAccounts :==> Ended");
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listUserAccountPaginated(HttpServletRequest request, HttpServletResponse response,
			String departmentId, String categoryNameId) {
		log.info("UserAccountController :==> listUserAccountPaginated :==> Started");
		DataTableResults<AppAdminUserDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			/*
			 * if (!departmentId.isEmpty()&&!departmentId.equals(""))
			 * whereClauseForBaseQuery ="  dm.id=" + Integer.parseInt(departmentId); if
			 * (!categoryNameId.isEmpty()) whereClauseForBaseQuery ="  uc.id=" +
			 * Integer.parseInt(categoryNameId);
			 * 
			 * if (!departmentId.isEmpty() && !categoryNameId.isEmpty())
			 * whereClauseForBaseQuery = "  dm.id=" + Integer.parseInt(departmentId)+
			 * " and uc.id="+Integer.parseInt(categoryNameId);
			 */
			dataTableResult = userAccountService.loadGrid(request, whereClauseForBaseQuery);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		log.info("UserAccountController :==> listUserAccountPaginated :==> Ended");
		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("UserAccountController :==> getRecord :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(userAccountService.getReordById(id));
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
		log.info("UserAccountController :==> getRecord :==> Started");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid AppAdminUserDTO appAdminUserDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("UserAccountController :==> saveAndUpdateRecord :==> Started");
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
				jsonResponse.setFormObject(userAccountService.saveAndUpdate(appAdminUserDTO));
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

		log.info("UserAccountController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("UserAccountController :==> deleteRecordById :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(userAccountService.deleteRecordById(id));
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

		log.info("UserAccountController :==> deleteRecordById :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("UserAccountController :==> deleteSelectedRecords :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			userAccountService.deleteMultipleRecords(recordIdArray);
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
		log.info("UserAccountController :==> deleteSelectedRecords :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public String userAccountList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {
		log.info("UserAccountController :==> userAccountList :==> Started");
		JsonResponse jsonResponse = new JsonResponse();
		try {
			List<NameValue> appAdminUserList = userAccountService.getAppAdminUserList(id);
			jsonResponse.setComboList(appAdminUserList);
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
		log.info("UserAccountController :==> userAccountList :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

}// End of AppAdminUserController
