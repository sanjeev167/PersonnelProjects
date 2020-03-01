/**
 * 
 */
package com.admin.pvt.sec.acl.dto;

import java.util.List;

import com.admin.pvt.masters.dto.NameValue;
/**
 * @author Sanjeev
 *
 */

public class AccessRightsAclDTO {	
	private Integer id;
	private String accessRightsAclId;
	private String departmentName;
	private String moduleName;
	private String domainName;
	private String roleName;
	private String roleNameId;
	
	private String userName;
	private String userNameId;
	
	//private String opIdArray
	private List<NameValue> allActionsAssignedToThisRoleOnDomain;
	private List<NameValue> roleListInDepartment;	
	private List<NameValue> allRoleIdsForAccessOnThisDomain;
	private List<NameValue> allActionsDefinedOnThisDomain;
	
	//@NotEmpty(message = "Select a department")
	private String departmentNameId;
	
	//@NotEmpty(message = "Select a module")
	private String moduleNameId;
	
	//@NotEmpty(message = "Select a page")
	private String domainNameId;		
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public AccessRightsAclDTO() {}
	
	//Will be utilized by grid
	public AccessRightsAclDTO(Integer id, String departmentName, String moduleName, String domainName,
			Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.moduleName = moduleName;
		this.domainName = domainName;					
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized while fetching a record
		public AccessRightsAclDTO(Integer id, String departmentNameId, String moduleNameId, String domainNameId) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.moduleNameId = moduleNameId;
			this.domainNameId = domainNameId;			
		}

		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return the accessRightsAclId
		 */
		public String getAccessRightsAclId() {
			return accessRightsAclId;
		}

		/**
		 * @param accessRightsAclId the accessRightsAclId to set
		 */
		public void setAccessRightsAclId(String accessRightsAclId) {
			this.accessRightsAclId = accessRightsAclId;
		}

		/**
		 * @return the departmentName
		 */
		public String getDepartmentName() {
			return departmentName;
		}

		/**
		 * @param departmentName the departmentName to set
		 */
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		/**
		 * @return the moduleName
		 */
		public String getModuleName() {
			return moduleName;
		}

		/**
		 * @param moduleName the moduleName to set
		 */
		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}

		/**
		 * @return the domainName
		 */
		public String getDomainName() {
			return domainName;
		}

		/**
		 * @param domainName the domainName to set
		 */
		public void setDomainName(String domainName) {
			this.domainName = domainName;
		}

		/**
		 * @return the roleName
		 */
		public String getRoleName() {
			return roleName;
		}

		/**
		 * @param roleName the roleName to set
		 */
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		/**
		 * @return the roleNameId
		 */
		public String getRoleNameId() {
			return roleNameId;
		}

		/**
		 * @param roleNameId the roleNameId to set
		 */
		public void setRoleNameId(String roleNameId) {
			this.roleNameId = roleNameId;
		}

		
		
		
		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

		/**
		 * @return the userNameId
		 */
		public String getUserNameId() {
			return userNameId;
		}

		/**
		 * @param userNameId the userNameId to set
		 */
		public void setUserNameId(String userNameId) {
			this.userNameId = userNameId;
		}

		/**
		 * @return the allActionsAssignedToThisRoleOnDomain
		 */
		public List<NameValue> getAllActionsAssignedToThisRoleOnDomain() {
			return allActionsAssignedToThisRoleOnDomain;
		}

		/**
		 * @param allActionsAssignedToThisRoleOnDomain the allActionsAssignedToThisRoleOnDomain to set
		 */
		public void setAllActionsAssignedToThisRoleOnDomain(List<NameValue> allActionsAssignedToThisRoleOnDomain) {
			this.allActionsAssignedToThisRoleOnDomain = allActionsAssignedToThisRoleOnDomain;
		}

		/**
		 * @return the roleListInDepartment
		 */
		public List<NameValue> getRoleListInDepartment() {
			return roleListInDepartment;
		}

		/**
		 * @param roleListInDepartment the roleListInDepartment to set
		 */
		public void setRoleListInDepartment(List<NameValue> roleListInDepartment) {
			this.roleListInDepartment = roleListInDepartment;
		}

		/**
		 * @return the allRoleIdsForAccessOnThisDomain
		 */
		public List<NameValue> getAllRoleIdsForAccessOnThisDomain() {
			return allRoleIdsForAccessOnThisDomain;
		}

		/**
		 * @param allRoleIdsForAccessOnThisDomain the allRoleIdsForAccessOnThisDomain to set
		 */
		public void setAllRoleIdsForAccessOnThisDomain(List<NameValue> allRoleIdsForAccessOnThisDomain) {
			this.allRoleIdsForAccessOnThisDomain = allRoleIdsForAccessOnThisDomain;
		}

		/**
		 * @return the allActionsDefinedOnThisDomain
		 */
		public List<NameValue> getAllActionsDefinedOnThisDomain() {
			return allActionsDefinedOnThisDomain;
		}

		/**
		 * @param allActionsDefinedOnThisDomain the allActionsDefinedOnThisDomain to set
		 */
		public void setAllActionsDefinedOnThisDomain(List<NameValue> allActionsDefinedOnThisDomain) {
			this.allActionsDefinedOnThisDomain = allActionsDefinedOnThisDomain;
		}

		/**
		 * @return the departmentNameId
		 */
		public String getDepartmentNameId() {
			return departmentNameId;
		}

		/**
		 * @param departmentNameId the departmentNameId to set
		 */
		public void setDepartmentNameId(String departmentNameId) {
			this.departmentNameId = departmentNameId;
		}

		/**
		 * @return the moduleNameId
		 */
		public String getModuleNameId() {
			return moduleNameId;
		}

		/**
		 * @param moduleNameId the moduleNameId to set
		 */
		public void setModuleNameId(String moduleNameId) {
			this.moduleNameId = moduleNameId;
		}

		/**
		 * @return the domainNameId
		 */
		public String getDomainNameId() {
			return domainNameId;
		}

		/**
		 * @param domainNameId the domainNameId to set
		 */
		public void setDomainNameId(String domainNameId) {
			this.domainNameId = domainNameId;
		}

		/**
		 * @return the totalrecords
		 */
		public Integer getTotalrecords() {
			return totalrecords;
		}

		/**
		 * @param totalrecords the totalrecords to set
		 */
		public void setTotalrecords(Integer totalrecords) {
			this.totalrecords = totalrecords;
		}
		
		
		

		
}
