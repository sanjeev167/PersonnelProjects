package com.admin.pvt.sec.env.entity;


import java.io.Serializable;
import java.util.List;

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

import com.admin.pvt.sec.env.dto.OpMasterDTO;
import com.admin.pvt.sec.rbac.entity.OperationAccess;


/**
 * The persistent class for the operation_master database table.
 * 
 */
@Entity
@Table(name="operation_master")
@NamedQuery(name="OperationMaster.findAll", query="SELECT o FROM OperationMaster o")
@SqlResultSetMapping(
        name = "OpMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = OpMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                		
                            @ColumnResult(name = "departmentName"), 
                            
                            @ColumnResult(name = "moduleName"), 
                            
                            @ColumnResult(name = "pageName"), 
                            @ColumnResult(name = "baseurl"), 
                            
                            @ColumnResult(name = "opName"),                            
                            
                            @ColumnResult(name = "opUrl"), 
                            
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))

public class OperationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String opurl;

	

	//bi-directional many-to-one association to OperationAccess
	@OneToMany(mappedBy="operationMaster")
	private List<OperationAccess> operationAccesses;

	//bi-directional many-to-one association to PageMaster
	@ManyToOne
	@JoinColumn(name="page_id")
	private PageMaster pageMaster;

	public OperationMaster() {
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

	public String getOpurl() {
		return this.opurl;
	}

	public void setOpurl(String opurl) {
		this.opurl = opurl;
	}

	

	public List<OperationAccess> getOperationAccesses() {
		return this.operationAccesses;
	}

	public void setOperationAccesses(List<OperationAccess> operationAccesses) {
		this.operationAccesses = operationAccesses;
	}

	public OperationAccess addOperationAccess(OperationAccess operationAccess) {
		getOperationAccesses().add(operationAccess);
		operationAccess.setOperationMaster(this);

		return operationAccess;
	}

	public OperationAccess removeOperationAccess(OperationAccess operationAccess) {
		getOperationAccesses().remove(operationAccess);
		operationAccess.setOperationMaster(null);

		return operationAccess;
	}

	public PageMaster getPageMaster() {
		return this.pageMaster;
	}

	public void setPageMaster(PageMaster pageMaster) {
		this.pageMaster = pageMaster;
	}

}