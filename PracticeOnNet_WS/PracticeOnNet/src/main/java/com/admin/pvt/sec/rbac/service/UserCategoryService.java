/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.rbac.dto.UserCategoryDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface UserCategoryService extends FieldValueWithParentIdExists{
	
	public DataTableResults<UserCategoryDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

    public UserCategoryDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public UserCategoryDTO saveAndUpdate(UserCategoryDTO userCategoryDTO)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	public List<NameValue> getUserCategoryList(Integer id)throws CustomRuntimeException;
}
