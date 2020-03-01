/**
 * 
 */
package com.admin.pvt.masters.ctrl;

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
import com.admin.pvt.masters.dto.CountryMasterDTO;
import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.masters.service.CountryService;
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
@RequestMapping(value = "/admin/pvt/masters/country/")
public class CountryMasterController extends BaseController{

	static final Logger log = LoggerFactory.getLogger(CountryMasterController.class);

	@Autowired
	CountryService countryService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listCountries(Model model) {
		log.info("CountryMasterController :==> listCountries :==> Started");
		String target = "/admin/pvt/masters/country_master";
		log.info("CountryMasterController :==> listCountries :==> Ended");
		return target;
	}

	@RequestMapping(value = "paginated", method = RequestMethod.POST)
	@ResponseBody
	public String listCountriesPaginated(HttpServletRequest request, HttpServletResponse response, String name,
			String sortname, String phonecode) {
		log.info("CountryMasterController :==> listCountriesPaginated :==> Started");
		String whereClauseForBaseQuery = "";
		DataTableResults<CountryMasterDTO> dataTableResults=null;
		try {
			if (!name.isEmpty() && sortname.isEmpty())
				whereClauseForBaseQuery = "where name LIKE '%" + name + "%'";
			if (!sortname.isEmpty() && name.isEmpty())
				whereClauseForBaseQuery = "where sortname LIKE '%" + sortname + "%'";
			if (!name.isEmpty() && !sortname.isEmpty())
				whereClauseForBaseQuery = "where name LIKE '%" + name + "%'" + " OR " + " sortname LIKE '%" + sortname
				+ "%'"; 
			dataTableResults = countryService.loadGrid(request, whereClauseForBaseQuery);
		}
		catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
		}

		log.info("CountryMasterController :==> listCountriesPaginated :==> Ended");
		return new Gson().toJson(dataTableResults);
	}

	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getReordById(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("CountryMasterController :==> getReordById :==> Started");
		JsonResponse jsonResponse=new JsonResponse();
		try {			 
			jsonResponse.setFormObject(countryService.getReordById(id));
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("Record found.");
		}
		catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}		 
		log.info("CountryMasterController :==> getReordById :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

	@RequestMapping(value = "saveAndUpdateRecord", method = RequestMethod.POST)	
	@ResponseBody
	public String saveAndUpdateRecord(@RequestBody @Valid CountryMasterDTO countryMasterDTO, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("CountryMasterController :==> saveAndUpdateRecord :==> Started");
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
				jsonResponse.setFormObject(countryService.saveAndUpdate(countryMasterDTO));
				jsonResponse.setStatus(true);
				jsonResponse.setStatusMsg("Record has been saved or updated successfully.");
			} 
		}
		catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("CountryMasterController :==> saveAndUpdateRecord :==> Ended"); 
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRecord(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response)throws Exception {
		log.info("CountryMasterController :==> deleteRecord :==> Started");
		JsonResponse jsonResponse=new JsonResponse();
		try {			 
			jsonResponse.setFormObject(countryService.deleteOneRecord(id));
			jsonResponse.setStatusMsg("Record has been deleted successfully.");
			jsonResponse.setStatus(true);
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("CountryMasterController :==> deleteRecord :==> Ended");
		return new Gson().toJson(jsonResponse);

	}

	@RequestMapping(value = "deleteSelected", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelected(@RequestBody Integer[] recordIdArray, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("CountryMasterController :==> deleteSelected :==> Started");
		JsonResponse jsonResponse=new JsonResponse();
		try {			 
			countryService.deleteMultipleRecords(recordIdArray);
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All selected records have been deleted.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("CountryMasterController :==> deleteSelected :==> Ended");
		return new Gson().toJson(jsonResponse);


	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody public String countryList(HttpServletRequest request,	HttpServletResponse response) {
		log.info("CountryMasterController :==> countryList :==> Started");
		JsonResponse jsonResponse=new JsonResponse();		
		try {			 	
			List<NameValue> countryList=countryService.getCountryList();	
			jsonResponse.setComboList(countryList);			 
			jsonResponse.setStatus(true);
			jsonResponse.setStatusMsg("All records have been fetched.");
		} catch (CustomRuntimeException ex) {
			// Handle this exception
			String exceptionCause= ex.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		} catch (Exception ex) {
			CustomRuntimeException exLocal=ExceptionApplicationUtility.wrapRunTimeException(ex);
			//Handle this exception
			String exceptionCause= exLocal.getExceptionInfo().exceptionCause;
			jsonResponse.setStatus(false);
			jsonResponse.setStatusMsg(exceptionCause);
		}
		log.info("CountryMasterController :==> countryList :==> Ended");
		return new Gson().toJson(jsonResponse);
	}

}// End of CountryMasterController
