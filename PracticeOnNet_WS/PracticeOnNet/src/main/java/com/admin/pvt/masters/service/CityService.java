/**
 * 
 */
package com.admin.pvt.masters.service;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.CityMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface CityService extends FieldValueWithParentIdExists{
	public DataTableResults<CityMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	

    public CityMasterDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public CityMasterDTO saveAndUpdate(CityMasterDTO cityMasterDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
}
