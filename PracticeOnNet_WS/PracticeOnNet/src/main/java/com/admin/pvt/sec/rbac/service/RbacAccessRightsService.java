/**
 * 
 */
package com.admin.pvt.sec.rbac.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.rbac.dto.AccessRightsRbacDTO;
import com.admin.pvt.sec.rbac.entity.AccessRightsRbac;
import com.admin.pvt.sec.rbac.entity.AppRole;
import com.custom.exception.CustomRuntimeException;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *   
 */
public interface RbacAccessRightsService {
	public DataTableResults<AccessRightsRbacDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException;

	public AccessRightsRbacDTO getReordById(Integer id)throws CustomRuntimeException;

	public boolean saveAndUpdate(String accessRightsRbacId,String pageId,String roleId,String recordIdArray[])throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

	public List<NameValue> getAllOperationRightsOnThisPage(Integer accessRightsRbacId)throws CustomRuntimeException;

	public List<NameValue> getRoleListHavingRightsOnThisPage(List<AccessRightsRbac> accessRightsRbacsList)throws CustomRuntimeException;

	public List<NameValue> getRoleListInDepartment(List<AppRole> roleListInDepartment,
			                                       List<AccessRightsRbac> accessRightsRbacList)throws CustomRuntimeException;
		
	
	public List<NameValue> getAllOperationDefinedOnThisPage(Integer pageId)throws CustomRuntimeException;

	public AccessRightsRbacDTO loadOperationOnPage(Integer roleId, Integer pageId)throws CustomRuntimeException;
}
