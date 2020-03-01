/**
 * 
 */
package com.admin.pvt.masters.dto;

/**
 * @author Sanjeev
 *
 */
public class NameValue {
  private Integer id;
  private String name;
  private String allReadyAssigned="";
  private Integer accessCount=0;
 /**
 * @return the accessCount
 */
public Integer getAccessCount() {
	return accessCount;
}

/**
 * @param accessCount the accessCount to set
 */
public void setAccessCount(Integer accessCount) {
	this.accessCount = accessCount;
}

public NameValue(Integer id, String name) {
	 this.id=id;
	 this.name=name;
	 
 }
public NameValue(Integer id, String name,Integer accessCount) {
	 this.id=id;
	 this.name=name;
	 this.accessCount=accessCount;
	 
}

public NameValue(Integer id, String name,String  allReadyAssigned) {
	 this.id=id;
	 this.name=name;
	 this.allReadyAssigned=allReadyAssigned;
	 
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getAllReadyAssigned() {
	return allReadyAssigned;
}

public void setAllReadyAssigned(String allReadyAssigned) {
	this.allReadyAssigned = allReadyAssigned;
}
  
  
  
}
