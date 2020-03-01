package com.admin.pvt.masters.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.admin.pvt.masters.service.CountryService;
import com.custom.validation.classLevelValidator.Unique;



@Unique(fieldName = "name", service = CountryService.class, message = "{country.unique.value.violation}",id="id")
public class CountryMasterDTO {
	// @Pattern(regexp="[^0-9]*",message="Should be an integer only.")
	// @NotNull(message = "Country phonecode is a required field")
	// @ShouldInteger(message="It is required and must be an integer.")
	// @Size(min = 3, max = 20, message ="{countryMasterDTO.name.Size}")

	private Integer id;

	@NotNull()	
	@NotBlank(message = "Required.")
	@Size(max = 50)
	private String name;
    
	@NotNull()	
	@NotBlank(message = "Required. No blank space.")
	@Size(max = 20)
	private String sortname;

	@NotNull(message = "Required.")	
	@Min(1)
	@Max(999999)	
	private Integer phonecode;	
	
	private Integer totalrecords;	
	
	private String status = "ErrorFree";
	private String errMsg = "No record found";

	List<Integer> recordIdArray;

	public List<Integer> getRecordIdArray() {
		return recordIdArray;
	}

	public void setRecordIdArray(List<Integer> recordIdArray) {
		this.recordIdArray = recordIdArray;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CountryMasterDTO() {
	};

	// This will be used for finding single record which will not be used in the
	// grid
	public CountryMasterDTO(Integer id, String name, String sortname, Integer phonecode) {
		this.id = id;
		this.name = name;
		this.sortname = sortname;
		this.phonecode = phonecode;
	}

	public CountryMasterDTO(Integer id, String name, String sortname, Integer phonecode, Integer totalrecords) {
		this.id = id;
		this.name = name;
		this.phonecode = phonecode;
		this.sortname = sortname;
		this.totalrecords = totalrecords;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhonecode() {
		return this.phonecode;
	}

	public void setPhonecode(Integer phonecode) {
		this.phonecode = phonecode;
	}

	public String getSortname() {
		return this.sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public Integer getTotalrecords() {
		return this.totalrecords;
	}

	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}

}