package com.admin.pvt.sec.acl.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.admin.pvt.sec.rbac.entity.AppRole;
import com.admin.pvt.sec.rbac.entity.UserReg;

import java.util.List;


/**
 * The persistent class for the acl_domain_access_rights database table.
 * 
 */
@Entity
@Table(name="acl_domain_access_rights")
@NamedQuery(name="AclDomainAccessRight.findAll", query="SELECT a FROM AclDomainAccessRight a")
public class AclDomainAccessRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="permisssion_to")
	private String permisssionTo;

	
	@Column(name="sid_ref")
	private Long sidRef;
	//bi-directional many-to-one association to AclDomainMaster
	@ManyToOne
	@JoinColumn(name="acl_domain_master_id")
	private AclDomainMaster aclDomainMaster;

	//bi-directional many-to-one association to AppRole
	@ManyToOne
	@JoinColumn(name="app_role_id")
	private AppRole appRole;

	//bi-directional many-to-one association to UserReg
	@ManyToOne
	@JoinColumn(name="user_reg_id")
	private UserReg userReg;

	//bi-directional many-to-one association to AclDomainActionAccess
	@OneToMany(mappedBy="aclDomainAccessRight",cascade = CascadeType.ALL)
	private List<AclDomainActionAccess> aclDomainActionAccesses;

	public AclDomainAccessRight() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermisssionTo() {
		return this.permisssionTo;
	}

	public void setPermisssionTo(String permisssionTo) {
		this.permisssionTo = permisssionTo;
	}
	
	

	public Long getSidRef() {
		return sidRef;
	}

	public void setSidRef(Long sidRef) {
		this.sidRef = sidRef;
	}

	public AclDomainMaster getAclDomainMaster() {
		return this.aclDomainMaster;
	}

	public void setAclDomainMaster(AclDomainMaster aclDomainMaster) {
		this.aclDomainMaster = aclDomainMaster;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

	public List<AclDomainActionAccess> getAclDomainActionAccesses() {
		return this.aclDomainActionAccesses;
	}

	public void setAclDomainActionAccesses(List<AclDomainActionAccess> aclDomainActionAccesses) {
		this.aclDomainActionAccesses = aclDomainActionAccesses;
	}

	public AclDomainActionAccess addAclDomainActionAccess(AclDomainActionAccess aclDomainActionAccess) {
		getAclDomainActionAccesses().add(aclDomainActionAccess);
		aclDomainActionAccess.setAclDomainAccessRight(this);

		return aclDomainActionAccess;
	}

	public AclDomainActionAccess removeAclDomainActionAccess(AclDomainActionAccess aclDomainActionAccess) {
		getAclDomainActionAccesses().remove(aclDomainActionAccess);
		aclDomainActionAccess.setAclDomainAccessRight(null);

		return aclDomainActionAccess;
	}

}