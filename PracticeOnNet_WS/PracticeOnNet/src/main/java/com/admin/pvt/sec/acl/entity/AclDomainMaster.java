package com.admin.pvt.sec.acl.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;


import com.admin.pvt.sec.acl.dto.DomainMasterDTO;
import com.admin.pvt.sec.env.entity.ModuleMaster;


/**
 * The persistent class for the acl_domain_master database table.
 * 
 */
@Entity
@Table(name="acl_domain_master")
@NamedQuery(name="AclDomainMaster.findAll", query="SELECT a FROM AclDomainMaster a")
@SqlResultSetMapping(
        name = "DomainMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = DomainMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "moduleName"), 
                            @ColumnResult(name = "domainName"), 
                            @ColumnResult(name = "packageName"), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class AclDomainMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="acl_domain_ref_id")
	private Long aclDomainRefId;

	private String name;

	@Column(name="package_name")
	private String packageName;

	//bi-directional many-to-one association to AclDomainAccessRight
	@OneToMany(mappedBy="aclDomainMaster")
	private List<AclDomainAccessRight> aclDomainAccessRights;

	//bi-directional many-to-one association to AclDomainActionMaster
	@OneToMany(mappedBy="aclDomainMaster", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private List<AclDomainActionMaster> aclDomainActionMasters;

	//bi-directional many-to-one association to ModuleMaster
	@ManyToOne
	@JoinColumn(name="module_id")
	private ModuleMaster moduleMaster;

	public AclDomainMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAclDomainRefId() {
		return this.aclDomainRefId;
	}

	public void setAclDomainRefId(Long aclDomainRefId) {
		this.aclDomainRefId = aclDomainRefId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<AclDomainAccessRight> getAclDomainAccessRights() {
		return this.aclDomainAccessRights;
	}

	public void setAclDomainAccessRights(List<AclDomainAccessRight> aclDomainAccessRights) {
		this.aclDomainAccessRights = aclDomainAccessRights;
	}

	public AclDomainAccessRight addAclDomainAccessRight(AclDomainAccessRight aclDomainAccessRight) {
		getAclDomainAccessRights().add(aclDomainAccessRight);
		aclDomainAccessRight.setAclDomainMaster(this);

		return aclDomainAccessRight;
	}

	public AclDomainAccessRight removeAclDomainAccessRight(AclDomainAccessRight aclDomainAccessRight) {
		getAclDomainAccessRights().remove(aclDomainAccessRight);
		aclDomainAccessRight.setAclDomainMaster(null);

		return aclDomainAccessRight;
	}

	public List<AclDomainActionMaster> getAclDomainActionMasters() {
		return this.aclDomainActionMasters;
	}

	public void setAclDomainActionMasters(List<AclDomainActionMaster> aclDomainActionMasters) {
		this.aclDomainActionMasters = aclDomainActionMasters;
	}

	public AclDomainActionMaster addAclDomainActionMaster(AclDomainActionMaster aclDomainActionMaster) {
		getAclDomainActionMasters().add(aclDomainActionMaster);
		aclDomainActionMaster.setAclDomainMaster(this);

		return aclDomainActionMaster;
	}

	public AclDomainActionMaster removeAclDomainActionMaster(AclDomainActionMaster aclDomainActionMaster) {
		getAclDomainActionMasters().remove(aclDomainActionMaster);
		aclDomainActionMaster.setAclDomainMaster(null);

		return aclDomainActionMaster;
	}

	public ModuleMaster getModuleMaster() {
		return this.moduleMaster;
	}

	public void setModuleMaster(ModuleMaster moduleMaster) {
		this.moduleMaster = moduleMaster;
	}

}