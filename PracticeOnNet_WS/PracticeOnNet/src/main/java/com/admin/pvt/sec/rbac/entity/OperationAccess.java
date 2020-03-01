package com.admin.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.admin.pvt.sec.env.entity.OperationMaster;


/**
 * The persistent class for the operation_access database table.
 * 
 */
@Entity
@Table(name="operation_access")
@NamedQuery(name="OperationAccess.findAll", query="SELECT o FROM OperationAccess o")
public class OperationAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to AccessRightsRbac
	@ManyToOne
	@JoinColumn(name="access_rights_rbac_id")
	private AccessRightsRbac accessRightsRbac;

	//bi-directional many-to-one association to OperationMaster
	@ManyToOne
	@JoinColumn(name="operation_master_id")
	private OperationMaster operationMaster;

	public OperationAccess() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AccessRightsRbac getAccessRightsRbac() {
		return this.accessRightsRbac;
	}

	public void setAccessRightsRbac(AccessRightsRbac accessRightsRbac) {
		this.accessRightsRbac = accessRightsRbac;
	}

	public OperationMaster getOperationMaster() {
		return this.operationMaster;
	}

	public void setOperationMaster(OperationMaster operationMaster) {
		this.operationMaster = operationMaster;
	}

}