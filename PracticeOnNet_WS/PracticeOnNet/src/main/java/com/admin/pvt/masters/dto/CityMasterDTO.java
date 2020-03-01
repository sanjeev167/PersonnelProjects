/**
 * 
 */
package com.admin.pvt.masters.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.admin.pvt.masters.service.CityService;
import com.custom.validation.classLevelValidator.UniqueWithParentId;

/**
 * @author Sanjeev
 *
 */
@UniqueWithParentId(parentId="stateNameId",fieldName = "cityName", service = CityService.class, message = "{city.unique.value.violation}",id="id")
	
public class CityMasterDTO {

	private Integer id;
	private String countryName;
	private String stateName;
	
	@NotNull()
	@NotBlank(message = "Select one.")
	private String countryNameId;
	
	@NotNull()
	@NotBlank(message = "Select one.")
	private String stateNameId;
	
	@NotNull()
	@NotBlank(message = "Required.")		
	private String cityName;
	
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public CityMasterDTO() {}
	
	//Will be utilized by grid
	public CityMasterDTO(Integer id, String countryName, String stateName, String cityName, Integer totalrecords) {
		this.id = id;
		this.countryName = countryName;
		this.stateName = stateName;
		this.cityName = cityName;
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized by grid
		public CityMasterDTO(Integer id, String countryNameId, String stateNameId, String cityName) {
			this.id = id;
			this.countryNameId = countryNameId;
			this.stateNameId = stateNameId;
			this.cityName = cityName;
			
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
		 * @return the countryNameId
		 */
		public String getCountryNameId() {
			return countryNameId;
		}

		/**
		 * @param countryNameId the countryNameId to set
		 */
		public void setCountryNameId(String countryNameId) {
			this.countryNameId = countryNameId;
		}

		/**
		 * @return the stateNameId
		 */
		public String getStateNameId() {
			return stateNameId;
		}

		/**
		 * @param stateNameId the stateNameId to set
		 */
		public void setStateNameId(String stateNameId) {
			this.stateNameId = stateNameId;
		}

		/**
		 * @return the cityName
		 */
		public String getCityName() {
			return cityName;
		}

		/**
		 * @param cityName the cityName to set
		 */
		public void setCityName(String cityName) {
			this.cityName = cityName;
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
