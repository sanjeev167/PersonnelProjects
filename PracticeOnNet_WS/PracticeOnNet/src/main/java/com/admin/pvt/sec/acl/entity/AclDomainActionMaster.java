package com.admin.pvt.sec.acl.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.admin.pvt.sec.acl.dto.DomainActionMasterDTO;
import com.admin.pvt.sec.env.dto.OpMasterDTO;


/**
 * The persistent class for the acl_domain_action_master database table.
 * 
 */
@Entity
@Table(name="acl_domain_action_master")
@NamedQuery(name="AclDomainActionMaster.findAll", query="SELECT a FROM AclDomainActionMaster a")
@SqlResultSetMapping(
        name = "DomainActionMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = DomainActionMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                		
                            @ColumnResult(name = "departmentName"), 
                            
                            @ColumnResult(name = "moduleName"), 
                            
                            @ColumnResult(name = "domainName"), 
                            @ColumnResult(name = "packageName"), 
                            
                            @ColumnResult(name = "actionName"),                            
                            @ColumnResult(name = "sortName"), 
                            @ColumnResult(name = "actionNumber", type=Integer.class), 
                            
                            
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))

public class AclDomainActionMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="action_number")
	private Integer actionNumber;

	private String name;

	@Column(name="sort_name")
	private String sortName;

	//bi-directional many-to-one association to AclDomainActionAccess
	@OneToMany(mappedBy="aclDomainActionMaster")
	private List<AclDomainActionAccess> aclDomainActionAccesses;

	//bi-directional many-to-one association to AclDomainMaster
	@ManyToOne
	@JoinColumn(name="acl_domain_master_id")
	private AclDomainMaster aclDomainMaster;

	public AclDomainActionMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActionNumber() {
		return this.actionNumber;
	}

	public void setActionNumber(Integer actionNumber) {
		this.actionNumber = actionNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public List<AclDomainActionAccess> getAclDomainActionAccesses() {
		return this.aclDomainActionAccesses;
	}

	public void setAclDomainActionAccesses(List<AclDomainActionAccess> aclDomainActionAccesses) {
		this.aclDomainActionAccesses = aclDomainActionAccesses;
	}

	public AclDomainActionAccess addAclDomainActionAccess(AclDomainActionAccess aclDomainActionAccess) {
		getAclDomainActionAccesses().add(aclDomainActionAccess);
		aclDomainActionAccess.setAclDomainActionMaster(this);

		return aclDomainActionAccess;
	}

	public AclDomainActionAccess removeAclDomainActionAccess(AclDomainActionAccess aclDomainActionAccess) {
		getAclDomainActionAccesses().remove(aclDomainActionAccess);
		aclDomainActionAccess.setAclDomainActionMaster(null);

		return aclDomainActionAccess;
	}

	public AclDomainMaster getAclDomainMaster() {
		return this.aclDomainMaster;
	}

	public void setAclDomainMaster(AclDomainMaster aclDomainMaster) {
		this.aclDomainMaster = aclDomainMaster;
	}

}