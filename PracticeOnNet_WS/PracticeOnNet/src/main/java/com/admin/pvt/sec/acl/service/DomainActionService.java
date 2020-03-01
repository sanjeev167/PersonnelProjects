/**
 * 
 */
package com.admin.pvt.sec.acl.service;

import javax.servlet.http.HttpServletRequest;

import com.grid_pagination.DataTableResults;
import com.admin.pvt.sec.acl.dto.DomainActionMasterDTO;
import com.custom.exception.CustomRuntimeException;
import com.custom.validation.interfaceForServices.FieldValueWithParentIdExists;

/**
 * @author Sanjeev
 *
 */
public interface DomainActionService extends FieldValueWithParentIdExists{
	public DataTableResults<DomainActionMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException ;	

    public DomainActionMasterDTO getReordById(Integer id)throws CustomRuntimeException ;
    
    public DomainActionMasterDTO saveAndUpdate(DomainActionMasterDTO opMasterDTO)throws CustomRuntimeException ;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException ;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException ;
}
