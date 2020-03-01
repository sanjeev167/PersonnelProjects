/**
 * 
 */
package com.user.pub.local_en.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.user.pub.local_en.dto.WebUserDTO;
import com.admin.pvt.masters.dto.NameValue;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface WebUserAccountService extends FieldValueExists{
	
	    public DataTableResults<WebUserDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

	    public WebUserDTO getReordById(Long id)throws CustomRuntimeException;
	    
	    public WebUserDTO saveAndUpdate(WebUserDTO webUserDTO)throws CustomRuntimeException;

		public boolean deleteRecordById(Long id)throws CustomRuntimeException;

		public boolean deleteMultipleRecords(Long[] recordIdArray)throws CustomRuntimeException;
		public List<NameValue> getWebUserList(Long id)throws CustomRuntimeException;
}
