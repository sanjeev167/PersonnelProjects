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

import com.admin.pvt.masters.entity.StateMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface StateRepository extends JpaRepository<StateMaster, Integer> {

	@Query(value = "SELECT * FROM StateMaster", nativeQuery = true)
	List<StateMaster> findAllByName(List<String> listOfStateNames);
	
	
	@Query("SELECT CASE WHEN COUNT(sm.name) > 0 THEN 'true' ELSE 'false' END FROM StateMaster sm "
			+ " where sm.countryMaster.id in ?1 and sm.name in ?2")
    public Boolean existsByCountryIdAndStateName(Integer parentId,String stateName);
	

	@Modifying
	@Transactional
	@Query("delete from StateMaster sm where sm.id in ?1")
	void deleteStateWithIds(Integer[] recordIdArray);

	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM StateMaster sm where sm.countryMaster.id in ?1 and sm.name in ?2 and sm.id <> ?3")	
	boolean existsByCountryIdAndStateNameExceptThisId(int parseInt, String stateName, int id);
}
