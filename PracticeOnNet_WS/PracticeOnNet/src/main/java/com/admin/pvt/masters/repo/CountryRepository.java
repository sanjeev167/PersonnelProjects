/**
 * 
 */
package com.admin.pvt.masters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.masters.entity.CountryMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface CountryRepository extends JpaRepository<CountryMaster, Integer> {
    
	

	@Query(value = "SELECT * FROM CountryMaster", nativeQuery = true)
	List<CountryMaster> findAllByName(List<String> listOfCountryNames);
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM CountryMaster cm where cm.name in ?1")
    public Boolean existsByCountryName(String countryName);
	

	@Modifying
	@Transactional
	@Query("delete from CountryMaster cm where cm.id in ?1")
	void deleteCountryWithIds(Integer[] recordIdArray);

	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM CountryMaster cm where cm.name in ?1 and cm.id <> ?2")
	boolean existsByCountryNameExceptThisId(String string, int id);
}
