/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.rbac.dto.AppGroupDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AppGroupService extends FieldValueWithParentIdExists{
	
	public DataTableResults<AppGroupDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

    public AppGroupDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public AppGroupDTO saveAndUpdate(AppGroupDTO appGroupDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	public List<NameValue> getAppGroupList(Integer id)throws CustomRuntimeException;
}
