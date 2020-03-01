/**
 * 
 */
package com.admin.pvt.sec.env.dto;

import javax.validation.constraints.NotEmpty;

import com.admin.pvt.sec.env.service.OpService;
import com.custom.validation.classLevelValidator.SecondChildUniqueWithParentId;
import com.custom.validation.classLevelValidator.ThirdChildUniqueWithParentId;
import com.custom.validation.classLevelValidator.UniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@UniqueWithParentId(parentId="pageNameId", fieldName = "opName", service = OpService.class, message = "{op.unique.value.violation}",
id="id")


@ThirdChildUniqueWithParentId(parentId="pageNameId", fieldName = "opUrl", service = OpService.class, message = "{op.unique.opUrl.violation}",
id="id")

public class OpMasterDTO {

	private Integer id;
	private String departmentName;
	private String moduleName;
	private String pageName;
	private String opNameUniqueness;
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Select a module")
	private String moduleNameId;
	
	@NotEmpty(message = "Select a page")
	private String pageNameId;
	
	//@NotEmpty(message = "Sanjee Base url is required.")
	private String baseurl;
	
	@NotEmpty(message = "Operation name is required.")		
	private String opName;
	
	@NotEmpty(message = "Operation url is required.")
	private String opUrl;
	
	
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public OpMasterDTO() {}
	
	//Will be utilized by grid
	public OpMasterDTO(Integer id, String departmentName, String moduleName, String pageName,String baseurl, 
			String opName,String opUrl,
			Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.moduleName = moduleName;
		this.pageName = pageName;
		this.baseurl=baseurl;
		this.opName=opName;
		this.opUrl=opUrl;
					
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized while fetching a record
		public OpMasterDTO(Integer id, String departmentNameId, String moduleNameId, String pageNameId,String baseurl,
				String opName,String opUrl) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.moduleNameId = moduleNameId;
			this.pageNameId = pageNameId;
			this.baseurl=baseurl;
			this.opName=opName;
			this.opUrl=opUrl;
			
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
		 * @return the baseurl
		 */
		public String getBaseurl() {
			return baseurl;
		}

		/**
		 * @param baseurl the baseurl to set
		 */
		public void setBaseurl(String baseurl) {
			this.baseurl = baseurl;
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
		 * @return the opName
		 */
		public String getOpName() {
			return opName;
		}

		/**
		 * @param opName the opName to set
		 */
		public void setOpName(String opName) {
			this.opName = opName;
		}

		/**
		 * @return the opUrl
		 */
		public String getOpUrl() {
			return opUrl;
		}

		/**
		 * @param opUrl the opUrl to set
		 */
		public void setOpUrl(String opUrl) {
			this.opUrl = opUrl;
		}

		
		
		
		/**
		 * @return the opNameUniqueness
		 */
		public String getOpNameUniqueness() {
			return opNameUniqueness;
		}

		/**
		 * @param opNameUniqueness the opNameUniqueness to set
		 */
		public void setOpNameUniqueness(String opNameUniqueness) {
			this.opNameUniqueness = opNameUniqueness;
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
