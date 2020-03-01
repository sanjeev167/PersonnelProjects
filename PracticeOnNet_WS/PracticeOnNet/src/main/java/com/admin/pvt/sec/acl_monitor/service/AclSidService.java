/**
 * 
 */
package com.admin.pvt.sec.acl_monitor.service;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.sec.acl_monitor.dto.AclSidDTO;
import com.custom.exception.CustomRuntimeException;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AclSidService {

	DataTableResults<AclSidDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException ;

}
