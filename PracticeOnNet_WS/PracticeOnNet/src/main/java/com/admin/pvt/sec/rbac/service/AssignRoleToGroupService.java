/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.sec.rbac.dto.AppGroupRoleDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AssignRoleToGroupService extends FieldValueWithParentIdExists{
public DataTableResults<AppGroupRoleDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	
    public AppGroupRoleDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppGroupRoleDTO saveAndUpdate(AppGroupRoleDTO appGroupRoleDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

}
