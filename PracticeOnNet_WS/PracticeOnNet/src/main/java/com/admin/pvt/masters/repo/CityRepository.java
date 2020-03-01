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

import com.admin.pvt.masters.entity.CityMaster;
import com.admin.pvt.masters.entity.StateMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface CityRepository extends JpaRepository<CityMaster, Integer> {

	@Query(value = "SELECT * FROM CityMaster", nativeQuery = true)
	List<StateMaster> findAllByName(List<String> listOfStateNames);
	@Query("SELECT CASE WHEN COUNT(cm.name) > 0 THEN 'true' ELSE 'false' END FROM CityMaster cm "
			+ " where cm.stateMaster.id in ?1 and cm.name in ?2")
    public Boolean existsByStateIdAndCityName(Integer parentId,String stateName);
	


	@Modifying
	@Transactional
	@Query("delete from CityMaster cm where cm.id in ?1")
	void deleteCityWithIds(Integer[] recordIdArray);
	
	@Query("SELECT CASE WHEN COUNT(cm.name) > 0 THEN 'true' ELSE 'false' END FROM CityMaster cm where cm.stateMaster.id in ?1 and cm.name in ?2 and cm.id <> ?3")	
	boolean existsByStateIdAndCityNameExceptThisId(int parseInt, String cityName, int id);
}
