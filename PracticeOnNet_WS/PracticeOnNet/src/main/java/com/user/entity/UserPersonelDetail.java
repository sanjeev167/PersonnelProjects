package com.user.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.admin.pvt.sec.rbac.entity.UserReg;

import java.util.Date;


/**
 * The persistent class for the user_personel_details database table.
 * 
 */
@Entity
@Table(name="user_personel_details")
@NamedQuery(name="UserPersonelDetail.findAll", query="SELECT u FROM UserPersonelDetail u")
public class UserPersonelDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	private String height;

	@Column(name="last_name")
	private String lastName;

	private Integer phone;

	private String weight;

	//bi-directional many-to-one association to UserReg
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserReg userReg;

	public UserPersonelDetail() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getPhone() {
		return this.phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

}