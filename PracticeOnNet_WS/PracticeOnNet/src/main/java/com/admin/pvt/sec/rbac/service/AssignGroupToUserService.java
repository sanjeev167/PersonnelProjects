/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.sec.rbac.dto.AppUserGroupDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AssignGroupToUserService extends FieldValueWithParentIdExists{
public DataTableResults<AppUserGroupDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	
    public AppUserGroupDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppUserGroupDTO saveAndUpdate(AppUserGroupDTO appUserGroupDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

}