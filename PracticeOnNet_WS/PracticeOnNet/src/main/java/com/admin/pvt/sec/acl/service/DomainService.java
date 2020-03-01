/**
 * 
 */
package com.admin.pvt.sec.acl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.acl.dto.DomainMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface DomainService extends FieldValueWithParentIdExists{
	public DataTableResults<DomainMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException ;
	

    public DomainMasterDTO getReordById(Integer id)throws CustomRuntimeException ;
    
    public DomainMasterDTO saveAndUpdate(DomainMasterDTO domainMasterDTO)throws CustomRuntimeException ;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException ;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException ;
	
	public List<NameValue> getDomainList(Integer id)throws CustomRuntimeException ;
	
}
