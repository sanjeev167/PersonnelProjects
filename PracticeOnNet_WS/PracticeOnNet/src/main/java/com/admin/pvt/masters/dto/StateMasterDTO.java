/**
 * 
 */
package com.admin.pvt.masters.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.admin.pvt.masters.service.StateService;
import com.custom.validation.classLevelValidator.UniqueWithParentId;



/**
 * @author Sanjeev
 *
 */
@UniqueWithParentId(parentId="countryId",fieldName = "stateName", service = StateService.class, message = "{state.unique.value.violation}",
id="id")	
public class StateMasterDTO {
	
	private Integer id;
	private String countryName;	
	
	@NotNull
	@NotBlank(message = "Select one.")
	private String countryId;	
	
	@NotNull
	@NotBlank(message = "Required.")	
	private String stateName;

	private Integer totalrecords;

	public StateMasterDTO(Integer id, String countryName,String stateName, Integer totalrecords) {
		this.id = id;
		this.countryName = countryName;
		this.stateName = stateName;
		this.totalrecords = totalrecords;
	}

	public StateMasterDTO(Integer id, String countryName, Integer countryId, String stateName) {
		this.id = id;
		this.countryName = countryName;		
		this.countryId = countryId+"";
		this.stateName = stateName;
	}

	public StateMasterDTO(Integer id, String countryId, String stateName) {
		this.id = id;			
		this.countryId = countryId;
		this.stateName = stateName;
	}
	
	//Will be utilized by @Valid
	public StateMasterDTO() {}

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
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
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
