/**
 * 
 */
package com.admin.pvt.masters.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.masters.dto.StateMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface StateService extends FieldValueWithParentIdExists{
	
	    public DataTableResults<StateMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException;

	    public StateMasterDTO getReordById(Integer id)throws CustomRuntimeException;
	    
	    public StateMasterDTO saveAndUpdate(StateMasterDTO stateMasterDTO)throws CustomRuntimeException;

		public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

		boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
		public List<NameValue> getStateList(Integer id)throws CustomRuntimeException;
}
