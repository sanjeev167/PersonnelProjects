/**
 * 
 */
package com.admin.pvt.sec.acl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.acl.dto.AccessRightsAclDTO;
import com.admin.pvt.sec.acl.dto.DomainMasterDTO;
import com.admin.pvt.sec.acl.entity.AclDomainAccessRight;
import com.admin.pvt.sec.rbac.entity.AppRole;
import com.custom.exception.CustomRuntimeException;
import com.grid_pagination.DataTableResults;

/**
 * @author Sanjeev
 * 
 */
public interface AccessRightsAclService {
	
	//public DataTableResults<AccessRightsAclDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery);
	public DataTableResults<DomainMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery)throws CustomRuntimeException;
	public AccessRightsAclDTO getReordById(Integer id)throws CustomRuntimeException;

	public boolean saveAndUpdate(String accessRightsAclId, String domainId, String roleId,String userId, String recordIdArray[],String permissionBase)throws CustomRuntimeException;

	public boolean deleteRecordById(Integer id)throws CustomRuntimeException;

	public boolean deleteMultipleRecords(Integer[] recordIdArray)throws CustomRuntimeException;

	public List<NameValue> getAllActionRightsOnThisDomain(Integer accessRightsAclId)throws CustomRuntimeException;

	public List<NameValue> getRoleListHavingRightsOnThisDomain(List<AclDomainAccessRight> accessRightsAclList)throws CustomRuntimeException;

	public List<NameValue> getRoleListInDepartment(List<AppRole> roleListInDepartment,
			List<AclDomainAccessRight> accessRightsAclList)throws CustomRuntimeException;

	public List<NameValue> getAllActionDefinedOnThisDomain(Integer domainId)throws CustomRuntimeException;

	public AccessRightsAclDTO loadActionsOnRoleSelection(Integer roleId, Integer domainId)throws CustomRuntimeException;
	public AccessRightsAclDTO loadActionsOnUserSelection(Integer roleId,Integer userId, Integer domainId)throws CustomRuntimeException;
}
