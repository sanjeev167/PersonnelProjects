/**
 * 
 */
package com.admin.pvt.sec.rbac.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.admin.pvt.masters.dto.NameValue;
/**
 * @author Sanjeev
 *
 */

public class AccessRightsRbacDTO {
	
	private Integer id;
	private String accessRightsRbacId;
	private String departmentName;
	private String moduleName;
	private String pageName;
	private String roleName;
	private String roleNameId;
	//private String opIdArray
	private List<NameValue> allOperationsAssignedToThisRoleOnPage;
	
	
	//@NotEmpty(message = "Select a department")
	private String departmentNameId;
	
	//@NotEmpty(message = "Select a module")
	private String moduleNameId;
	
	//@NotEmpty(message = "Select a page")
	private String pageNameId;	 
	
	private List<NameValue> roleListInDepartment;	
	private List<NameValue> allRoleIdsForAccessOnThisPage;
	private List<NameValue> allOperationsDefinedOnThisPage;
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public AccessRightsRbacDTO() {}
	
	//Will be utilized by grid
	public AccessRightsRbacDTO(Integer id, String departmentName, String moduleName, String pageName,
			Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.moduleName = moduleName;
		this.pageName = pageName;
					
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized while fetching a record
		public AccessRightsRbacDTO(Integer id, String departmentNameId, String moduleNameId, String pageNameId) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.moduleNameId = moduleNameId;
			this.pageNameId = pageNameId;
			
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
		 * @return the pageName
		 */
		public String getPageName() {
			return pageName;
		}

		/**
		 * @param pageName the pageName to set
		 */
		public void setPageName(String pageName) {
			this.pageName = pageName;
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
		 * @return the pageNameId
		 */
		public String getPageNameId() {
			return pageNameId;
		}

		/**
		 * @param pageNameId the pageNameId to set
		 */
		public void setPageNameId(String pageNameId) {
			this.pageNameId = pageNameId;
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
		 * @return the allRoleIdsForAccessOnThisPage
		 */
		public List<NameValue> getAllRoleIdsForAccessOnThisPage() {
			return allRoleIdsForAccessOnThisPage;
		}

		/**
		 * @param allRoleIdsForAccessOnThisPage the allRoleIdsForAccessOnThisPage to set
		 */
		public void setAllRoleIdsForAccessOnThisPage(List<NameValue> allRoleIdsForAccessOnThisPage) {
			this.allRoleIdsForAccessOnThisPage = allRoleIdsForAccessOnThisPage;
		}

		/**
		 * @return the allOperationsDefinedOnThisPage
		 */
		public List<NameValue> getAllOperationsDefinedOnThisPage() {
			return allOperationsDefinedOnThisPage;
		}

		/**
		 * @param allOperationsDefinedOnThisPage the allOperationsDefinedOnThisPage to set
		 */
		public void setAllOperationsDefinedOnThisPage(List<NameValue> allOperationsDefinedOnThisPage) {
			this.allOperationsDefinedOnThisPage = allOperationsDefinedOnThisPage;
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
		 * @return the allOperationsAssignedToThisRoleOnPage
		 */
		public List<NameValue> getAllOperationsAssignedToThisRoleOnPage() {
			return allOperationsAssignedToThisRoleOnPage;
		}

		/**
		 * @param allOperationsAssignedToThisRoleOnPage the allOperationsAssignedToThisRoleOnPage to set
		 */
		public void setAllOperationsAssignedToThisRoleOnPage(List<NameValue> allOperationsAssignedToThisRoleOnPage) {
			this.allOperationsAssignedToThisRoleOnPage = allOperationsAssignedToThisRoleOnPage;
		}

		
		
		/**
		 * @return the accessRightsRbacId
		 */
		public String getAccessRightsRbacId() {
			return accessRightsRbacId;
		}

		/**
		 * @param accessRightsRbacId the accessRightsRbacId to set
		 */
		public void setAccessRightsRbacId(String accessRightsRbacId) {
			this.accessRightsRbacId = accessRightsRbacId;
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
