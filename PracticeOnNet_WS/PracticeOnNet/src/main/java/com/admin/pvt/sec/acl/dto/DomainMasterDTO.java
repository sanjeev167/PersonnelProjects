/**
 * 
 */
package com.admin.pvt.sec.acl.dto;

import javax.validation.constraints.NotEmpty;

import com.admin.pvt.sec.acl.service.DomainService;
import com.custom.validation.classLevelValidator.FirstChildUniqueWithParentId;
import com.custom.validation.classLevelValidator.SecondChildUniqueWithParentId;
import com.custom.validation.classLevelValidator.UniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@FirstChildUniqueWithParentId(parentId="moduleNameId", fieldName = "domainName", service = DomainService.class, message = "{domain.unique.domainValue.violation}",
id="id")

@SecondChildUniqueWithParentId(parentId="moduleNameId", fieldName = "packageName", service = DomainService.class, message = "{domain.unique.pkgvalue.violation}",
id="id")
public class DomainMasterDTO {

	private Integer id;
	private String departmentName;
	private String moduleName;
	private String domainNameUniqueness;
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Select a module")
	private String moduleNameId;
	
	@NotEmpty(message = "Domain name is required.")	
	private String domainName;
	@NotEmpty(message = "Package Name is required.")
	private String packageName;
	
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public DomainMasterDTO() {}
	
	//Will be utilized by grid
	public DomainMasterDTO(Integer id, String departmentName, String moduleName, String domainName,String packageName, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.moduleName = moduleName;
		this.domainName = domainName;
		this.packageName=packageName;
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized by grid
		public DomainMasterDTO(Integer id, String departmentNameId, String moduleNameId, String domainName,String packageName) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.moduleNameId = moduleNameId;
			this.domainName = domainName;
			this.packageName=packageName;
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
		 * @return the domainNameUniqueness
		 */
		public String getDomainNameUniqueness() {
			return domainNameUniqueness;
		}

		/**
		 * @param domainNameUniqueness the domainNameUniqueness to set
		 */
		public void setDomainNameUniqueness(String domainNameUniqueness) {
			this.domainNameUniqueness = domainNameUniqueness;
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
		 * @return the packageName
		 */
		public String getPackageName() {
			return packageName;
		}

		/**
		 * @param packageName the packageName to set
		 */
		public void setPackageName(String packageName) {
			this.packageName = packageName;
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
