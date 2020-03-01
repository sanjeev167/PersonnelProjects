package com.admin.pvt.sec.acl.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the acl_domain_action_access database table.
 * 
 */
@Entity
@Table(name="acl_domain_action_access")
@NamedQuery(name="AclDomainActionAccess.findAll", query="SELECT a FROM AclDomainActionAccess a")
public class AclDomainActionAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to AclDomainAccessRight
	@ManyToOne
	@JoinColumn(name="acl_domain_access_rights_id")
	private AclDomainAccessRight aclDomainAccessRight;

	//bi-directional many-to-one association to AclDomainActionMaster
	@ManyToOne
	@JoinColumn(name="acl_domain_action_master_id")
	private AclDomainActionMaster aclDomainActionMaster;

	public AclDomainActionAccess() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AclDomainAccessRight getAclDomainAccessRight() {
		return this.aclDomainAccessRight;
	}

	public void setAclDomainAccessRight(AclDomainAccessRight aclDomainAccessRight) {
		this.aclDomainAccessRight = aclDomainAccessRight;
	}

	public AclDomainActionMaster getAclDomainActionMaster() {
		return this.aclDomainActionMaster;
	}

	public void setAclDomainActionMaster(AclDomainActionMaster aclDomainActionMaster) {
		this.aclDomainActionMaster = aclDomainActionMaster;
	}

}