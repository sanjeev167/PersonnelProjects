/**
 * 
 */
package com.admin.pvt.masters.repo;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.admin.pvt.masters.entity.CountryMaster;
/**
 * @author Sanjeev
 *
 */
@Repository
public class GenericRepoImpl implements GenericRepo {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CountryMaster> getCountryMasterList() {

		String qry = "SELECT id as id, name as name, sortname as sortname,phonecode as phonecode,"
		           + " (SELECT COUNT(1) FROM CountryMaster) AS totalrecords  FROM CountryMaster";
		
		Query query = entityManager.createNativeQuery(qry,CountryMaster.class);

		@SuppressWarnings("unchecked")
		List<CountryMaster> daoDtolist = query.getResultList();
		
		return daoDtolist;
	}

}