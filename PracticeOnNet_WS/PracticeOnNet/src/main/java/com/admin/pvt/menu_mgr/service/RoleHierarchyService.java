/**
 * 
 */
package com.admin.pvt.menu_mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.menu_mgr.dto.RoleHierarchyDTO;
import com.admin.pvt.menu_mgr.dto.RoleHierarchyJSON;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

public interface RoleHierarchyService extends FieldValueWithParentIdExists{
	
	public DataTableResults<RoleHierarchyDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	

    public RoleHierarchyDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public RoleHierarchyDTO saveAndUpdate(RoleHierarchyDTO menuManagerDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	
	
	public List<NameValue> getRoleList()throws CustomRuntimeException;
	public List<NameValue> getTreeParentNodeList()throws CustomRuntimeException;
	
	
	public ArrayList<Object> getRoleHierarchyLevelWiseStructure()throws CustomRuntimeException;
	
	public Integer getMinId()throws CustomRuntimeException;
	
	public Map<String, List<String>> getRoleHierarchyConfiguredForSecurity()throws CustomRuntimeException;
	
		
}
