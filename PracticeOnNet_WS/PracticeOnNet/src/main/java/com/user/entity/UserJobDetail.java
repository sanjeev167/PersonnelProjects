package com.user.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.admin.pvt.sec.rbac.entity.UserReg;


/**
 * The persistent class for the user_job_details database table.
 * 
 */
@Entity
@Table(name="user_job_details")
@NamedQuery(name="UserJobDetail.findAll", query="SELECT u FROM UserJobDetail u")
public class UserJobDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="job_title")
	private String jobTitle;

	@Column(name="job_type")
	private String jobType;

	//bi-directional many-to-one association to UserReg
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserReg userReg;

	public UserJobDetail() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

}