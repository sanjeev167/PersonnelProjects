/**
 * 
 */
package com.admin.pvt.sec.env.ctrl;

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
import com.admin.pvt.sec.env.dto.OpMasterDTO;
import com.admin.pvt.sec.env.service.OpService;
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
@RequestMapping(value = "/admin/pvt/sec/env/op/")
public class OperationMasterController extends BaseController {

	static final Logger log = LoggerFactory.getLogger(OperationMasterController.class);

	@Autowired
	OpService opService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listOperations(Model model) {
		log.info("OperationMasterController :==> listOperations :==> Started");
		String target = "/admin/pvt/sec/env/op_master";
		log.info("OperationMasterController :==> listOperations :==> Ended");

		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listOperationPaginated(HttpServletRequest request, HttpServletResponse response, String departmentNameId,
			String moduleNameId, String pageNameId) {

		log.info("OperationMasterController :==> listOperationPaginated :==> Started");

		DataTableResults<OpMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && moduleNameId.isEmpty()
					&& moduleNameId.equals("") && pageNameId.isEmpty() && pageNameId.equals(""))
				whereClauseForBaseQuery = " mm.department_id=" + Integer.parseInt(departmentNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals("") && pageNameId.isEmpty() && pageNameId.equals(""))
				whereClauseForBaseQuery = " mm.department_id=" + Integer.parseInt(departmentNameId)
						+ " and pm.module_id=" + Integer.parseInt(moduleNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals("") && !pageNameId.isEmpty() && !pageNameId.equals(""))
				whereClauseForBaseQuery = " mm.department_id=" + Integer.parseInt(departmentNameId)
						+ " and pm.module_id=" + Integer.parseInt(moduleNameId) + " and om.page_id="
						+ Integer.parseInt(pageNameId);
			dataTableResult = opService.loadGrid(request, whereClauseForBaseQuery);

		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}

		log.info("OperationMasterController :==> listOperationPaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("OperationMasterController :==> getRecord :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(opService.getReordById(id));
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

		log.info("OperationMasterController :==> getRecord :==> Ended");

		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid OpMasterDTO opMasterDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("OperationMasterController :==> saveAndUpdateRecord :==> Started");

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
				jsonResponse.setFormObject(opService.saveAndUpdate(opMasterDTO));
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

		log.info("OperationMasterController :==> saveAndUpdateRecord :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("OperationMasterController :==> deleteRecordById :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(opService.deleteOneRecord(id));
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

		log.info("OperationMasterController :==> deleteRecordById :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedRecords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("OperationMasterController :==> deleteSelectedRecords :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			opService.deleteMultipleRecords(recordIdArray);
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
		log.info("OperationMasterController :==> deleteSelectedRecords :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

}// End of StateMasterController
