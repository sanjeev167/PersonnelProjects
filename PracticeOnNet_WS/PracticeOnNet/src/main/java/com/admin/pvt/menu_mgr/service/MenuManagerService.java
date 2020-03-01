/**
 * 
 */
package com.admin.pvt.menu_mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.menu_mgr.dto.MenuDTO;
import com.admin.pvt.menu_mgr.dto.MenuManagerDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */

public interface MenuManagerService extends FieldValueWithParentIdExists{
	
	public DataTableResults<MenuManagerDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	

    public MenuManagerDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public MenuManagerDTO saveAndUpdate(MenuManagerDTO menuManagerDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
	
	public List<NameValue> getListTreeMenuType()throws CustomRuntimeException;
	public List<NameValue> getListTreeParentNode()throws CustomRuntimeException;
	public ArrayList<MenuDTO> getSpecificTreeTypeStructure(Integer specificTreeTypeId )throws CustomRuntimeException;
	public Map<String, Map<String, List<MenuDTO>>>  getRoleWiseMenuCollectionMap()throws CustomRuntimeException;
	public Integer getMinId()throws CustomRuntimeException;
	
}
