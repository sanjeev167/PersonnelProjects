package com.admin.pvt.masters.entity;

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

import com.admin.pvt.masters.dto.StateMasterDTO;
/**
 * The persistent class for the state_master database table.
 * 
 */
@Entity
@Table(name="state_master")
@NamedQuery(name="StateMaster.findAll", query="SELECT s FROM StateMaster s")
@SqlResultSetMapping(
        name = "StateMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = StateMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "countryName"), 
                            @ColumnResult(name = "stateName"),                             
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class StateMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to CityMaster
	@OneToMany(mappedBy="stateMaster")
	private List<CityMaster> cityMasters;

	//bi-directional many-to-one association to CountryMaster
	@ManyToOne
	@JoinColumn(name="country_id")
	private CountryMaster countryMaster;

	public StateMaster() {
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

	public List<CityMaster> getCityMasters() {
		return this.cityMasters;
	}

	public void setCityMasters(List<CityMaster> cityMasters) {
		this.cityMasters = cityMasters;
	}

	public CityMaster addCityMaster(CityMaster cityMaster) {
		getCityMasters().add(cityMaster);
		cityMaster.setStateMaster(this);

		return cityMaster;
	}

	public CityMaster removeCityMaster(CityMaster cityMaster) {
		getCityMasters().remove(cityMaster);
		cityMaster.setStateMaster(null);

		return cityMaster;
	}

	public CountryMaster getCountryMaster() {
		return this.countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

}