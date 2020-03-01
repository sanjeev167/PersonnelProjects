package com.admin.pvt.masters.entity;

import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.admin.pvt.masters.dto.CityMasterDTO;

/**
 * The persistent class for the city_master database table.
 * 
 */
@Entity
@Table(name="city_master")
@NamedQuery(name="CityMaster.findAll", query="SELECT c FROM CityMaster c")
@SqlResultSetMapping(
        name = "CityMasterDTOMapping",
        classes = @ConstructorResult(
                targetClass = CityMasterDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "countryName"), 
                            @ColumnResult(name = "stateName"), 
                            @ColumnResult(name = "cityName"), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))

public class CityMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to StateMaster
	@ManyToOne
	@JoinColumn(name="state_id")
	private StateMaster stateMaster;

	//bi-directional many-to-one association to UserAddress
	//@OneToMany(mappedBy="cityMaster")
	//private List<UserAddress> userAddresses;

	public CityMaster() {
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

	public StateMaster getStateMaster() {
		return this.stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

	/*public List<UserAddress> getUserAddresses() {
		return this.userAddresses;
	}

	public void setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public UserAddress addUserAddress(UserAddress userAddress) {
		getUserAddresses().add(userAddress);
		userAddress.setCityMaster(this);

		return userAddress;
	}

	public UserAddress removeUserAddress(UserAddress userAddress) {
		getUserAddresses().remove(userAddress);
		userAddress.setCityMaster(null);

		return userAddress;
	}*/

}