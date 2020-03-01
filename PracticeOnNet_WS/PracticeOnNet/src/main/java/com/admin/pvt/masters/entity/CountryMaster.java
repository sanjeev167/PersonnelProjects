package com.admin.pvt.masters.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.admin.pvt.masters.dto.CountryMasterDTO;

/**
 * The persistent class for the country_master database table.
 * 
 */
@Entity
@Table(name="country_master")
@NamedQuery(name="CountryMaster.findAll", query="SELECT c FROM CountryMaster c")
@SqlResultSetMapping(
        name = "CountryMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = CountryMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "name"), 
                            @ColumnResult(name = "sortname"), 
                            @ColumnResult(name = "phonecode", type = Integer.class),
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))

public class CountryMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private Integer phonecode;

	private String sortname;

	//bi-directional many-to-one association to StateMaster
	@OneToMany(mappedBy="countryMaster")
	private List<StateMaster> stateMasters;

	public CountryMaster() {
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

	public Integer getPhonecode() {
		return this.phonecode;
	}

	public void setPhonecode(Integer phonecode) {
		this.phonecode = phonecode;
	}

	public String getSortname() {
		return this.sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public List<StateMaster> getStateMasters() {
		return this.stateMasters;
	}

	public void setStateMasters(List<StateMaster> stateMasters) {
		this.stateMasters = stateMasters;
	}

	public StateMaster addStateMaster(StateMaster stateMaster) {
		getStateMasters().add(stateMaster);
		stateMaster.setCountryMaster(this);

		return stateMaster;
	}

	public StateMaster removeStateMaster(StateMaster stateMaster) {
		getStateMasters().remove(stateMaster);
		stateMaster.setCountryMaster(null);

		return stateMaster;
	}

}