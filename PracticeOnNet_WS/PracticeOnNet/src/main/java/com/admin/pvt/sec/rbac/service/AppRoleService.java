/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.rbac.dto.AppRoleDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AppRoleService extends FieldValueWithParentIdExists{
	
	public DataTableResults<AppRoleDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

    public AppRoleDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppRoleDTO saveAndUpdate(AppRoleDTO appRoleDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	public List<NameValue> getAppRoleList(Integer id)throws CustomRuntimeException;
	
	public List<NameValue> getAppRoleBasedUsersList(Integer selectedRoleId)throws CustomRuntimeException;
}
