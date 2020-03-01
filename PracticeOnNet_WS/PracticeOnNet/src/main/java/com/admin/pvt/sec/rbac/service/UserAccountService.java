/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.rbac.dto.AppAdminUserDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface UserAccountService extends FieldValueExists{
	
	    public DataTableResults<AppAdminUserDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

	    public AppAdminUserDTO getReordById(Integer id)throws CustomRuntimeException;
	    
	    public AppAdminUserDTO saveAndUpdate(AppAdminUserDTO appAdminUserDTO)throws CustomRuntimeException;

		public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

		public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
		public List<NameValue> getAppAdminUserList(Integer id)throws CustomRuntimeException;
}
