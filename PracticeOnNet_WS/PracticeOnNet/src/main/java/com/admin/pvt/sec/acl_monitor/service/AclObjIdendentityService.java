/**
 * 
 */
package com.admin.pvt.sec.acl_monitor.service;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.sec.acl_monitor.dto.AclObjectDTO;
import com.custom.exception.CustomRuntimeException;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 *
 */
public interface AclObjIdendentityService {

	DataTableResults<AclObjectDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)throws CustomRuntimeException ;

}
