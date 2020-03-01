package com.user.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.admin.pvt.masters.entity.CityMaster;
import com.admin.pvt.sec.rbac.entity.UserReg;


/**
 * The persistent class for the user_address database table.
 * 
 */
@Entity
@Table(name="user_address")
@NamedQuery(name="UserAddress.findAll", query="SELECT u FROM UserAddress u")
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="land_mark")
	private String landMark;

	@Column(name="pin_code")
	private Integer pinCode;

	@Column(name="street_address")
	private String streetAddress;

	//bi-directional many-to-one association to CityMaster
	@ManyToOne
	@JoinColumn(name="city_id")
	private CityMaster cityMaster;

	//bi-directional many-to-one association to UserReg
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserReg userReg;

	public UserAddress() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLandMark() {
		return this.landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public Integer getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public CityMaster getCityMaster() {
		return this.cityMaster;
	}

	public void setCityMaster(CityMaster cityMaster) {
		this.cityMaster = cityMaster;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

}