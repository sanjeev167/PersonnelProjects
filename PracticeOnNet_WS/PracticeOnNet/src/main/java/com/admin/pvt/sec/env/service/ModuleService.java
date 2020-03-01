/**
 * 
 */
package com.admin.pvt.sec.env.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.grid_pagination.DataTableResults;
import com.admin.pvt.masters.dto.CountryMasterDTO;
import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.masters.dto.StateMasterDTO;
import com.admin.pvt.sec.env.dto.ModuleMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;

/**
 * @author Sanjeev
 *
 */
public interface ModuleService extends FieldValueWithParentIdExists{
	
	    public DataTableResults<ModuleMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;

	    public ModuleMasterDTO getReordById(Integer id)throws CustomRuntimeException;
	    
	    public ModuleMasterDTO saveAndUpdate(ModuleMasterDTO moduleMasterDTO)throws CustomRuntimeException;

		public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

		void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
		public List<NameValue> getModuleList(Integer id)throws CustomRuntimeException;
}
