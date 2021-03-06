/**
 * 
 */
package com.admin.pvt.sec.env.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.env.dto.DepartmentMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface DepartmentService extends FieldValueExists{
	
  public DataTableResults<DepartmentMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException;

    public DepartmentMasterDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public DepartmentMasterDTO saveAndUpdate(DepartmentMasterDTO departmentMasterDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

	public List<NameValue> getDepartmentList()throws CustomRuntimeException;
    
  
}
