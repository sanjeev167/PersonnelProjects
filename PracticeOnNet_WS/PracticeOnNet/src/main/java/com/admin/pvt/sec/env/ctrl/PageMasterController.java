/**
 * 
 */
package com.admin.pvt.sec.env.ctrl;

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
import com.admin.pvt.sec.env.dto.PageMasterDTO;
import com.admin.pvt.sec.env.service.PageService;
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
@RequestMapping(value = "/admin/pvt/sec/env/page/")
public class PageMasterController extends BaseController {
	
	static final Logger log = LoggerFactory.getLogger(PageMasterController.class);
	
	@Autowired
	PageService pageService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listPages(Model model) {
		
		log.info("PageMasterController :==> listPages :==> Started");
		
		String target = "/admin/pvt/sec/env/page_master";
		
		log.info("PageMasterController :==> listPages :==> Ended");
		
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listPagePaginated(HttpServletRequest request, HttpServletResponse response, String departmentNameId,
			String moduleNameId) {

		log.info("PageMasterController :==> listPagePaginated :==> Started");
		
		DataTableResults<PageMasterDTO> dataTableResult = null;
		try {
			String whereClauseForBaseQuery = "";

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && moduleNameId.isEmpty()
					&& moduleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId);

			if (!departmentNameId.isEmpty() && !departmentNameId.equals("") && !moduleNameId.isEmpty()
					&& !moduleNameId.equals(""))
				whereClauseForBaseQuery = " dm.id=" + Integer.parseInt(departmentNameId) + " and pm.module_id="
						+ Integer.parseInt(moduleNameId);
			dataTableResult = pageService.loadGrid(request, whereClauseForBaseQuery);

		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause = ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal = ExceptionApplicationUtility.wrapRunTimeException(ex);
			// Handle this exception
			String exceptionCause = exLocal.getExceptionInfo().exceptionCause;
		}
		
		log.info("PageMasterController :==> listPagePaginated :==> Ended");

		return new Gson().toJson(dataTableResult);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> getRecord :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(pageService.getReordById(id));
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

		log.info("PageMasterController :==> getRecord :==> Ended");

		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid PageMasterDTO pageMasterDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> saveAndUpdateRecord :==> Started");

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
				jsonResponse.setFormObject(pageService.saveAndUpdate(pageMasterDTO));
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

		log.info("PageMasterController :==> saveAndUpdateRecord :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> deleteRecordById :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse.setFormObject(pageService.deleteOneRecord(id));
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

		log.info("PageMasterController :==> deleteRecordById :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedReords(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("PageMasterController :==> deleteSelectedReords :==> Started");

		JsonResponse jsonResponse = new JsonResponse();
		try {
			pageService.deleteMultipleRecords(recordIdArray);
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

		log.info("PageMasterController :==> deleteSelectedReords :==> Ended");

		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public String pageList(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> pageList :==> Started");
		
		JsonResponse jsonResponse = new JsonResponse();
		try {
			List<NameValue> moduleList = pageService.getPageList(id);
			jsonResponse.setComboList(moduleList);
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

		log.info("PageMasterController :==> pageList :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "listBaseUrl", method = RequestMethod.GET)
	@ResponseBody
	public String listBaseUrl(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		log.info("PageMasterController :==> listBaseUrl :==> Started");
		
		JsonResponse jsonResponse = new JsonResponse();
		try {
			List<NameValue> moduleList = pageService.getListBaseUrl(id);
			jsonResponse.setComboList(moduleList);
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

		log.info("PageMasterController :==> listBaseUrl :==> Ended");
		
		return new Gson().toJson(jsonResponse);
	}

	
	
}// End of StateMasterController
