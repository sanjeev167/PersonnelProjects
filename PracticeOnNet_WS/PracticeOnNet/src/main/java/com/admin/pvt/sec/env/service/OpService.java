/**
 * 
 */
package com.admin.pvt.sec.env.service;

import javax.servlet.http.HttpServletRequest;

import com.grid_pagination.DataTableResults;
import com.admin.pvt.masters.dto.CityMasterDTO;
import com.admin.pvt.masters.dto.StateMasterDTO;
import com.admin.pvt.sec.env.dto.OpMasterDTO;
import com.admin.pvt.sec.env.dto.PageMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;

/**
 * @author Sanjeev
 *
 */
public interface OpService extends FieldValueWithParentIdExists{
	public DataTableResults<OpMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	

    public OpMasterDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public OpMasterDTO saveAndUpdate(OpMasterDTO opMasterDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;
}
