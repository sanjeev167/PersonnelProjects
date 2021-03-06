/**
 * 
 */
package com.admin.pvt.masters.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.CountryMasterDTO;
import com.admin.pvt.masters.dto.NameValue;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface CountryService extends FieldValueExists{
	
  public DataTableResults<CountryMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException;

    public CountryMasterDTO getReordById(Integer id)throws CustomRuntimeException;
    
    public CountryMasterDTO saveAndUpdate(CountryMasterDTO countryMasterDTO)throws CustomRuntimeException;

	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException;

	void deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

	public List<NameValue> getCountryList()throws CustomRuntimeException;
    
  
}
