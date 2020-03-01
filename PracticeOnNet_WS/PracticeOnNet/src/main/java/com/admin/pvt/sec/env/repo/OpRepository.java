/**
 * 
 */
package com.admin.pvt.sec.env.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.masters.entity.CityMaster;
import com.admin.pvt.masters.entity.CountryMaster;
import com.admin.pvt.masters.entity.StateMaster;
import com.admin.pvt.sec.env.entity.OperationMaster;
import com.admin.pvt.sec.env.entity.PageMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface OpRepository extends JpaRepository<OperationMaster, Integer> {

	@Query(value = "SELECT * FROM OperationMaster", nativeQuery = true)
	List<OperationMaster> findAllByName(List<String> listOfStateNames);

	@Modifying
	@Transactional
	@Query("delete from OperationMaster om where om.id in ?1")
	void deleteOperationWithIds(Integer[] recordIdArray);
	
	//Uniqueness query for first child with parent
	@Query("SELECT CASE WHEN COUNT(om.name) > 0 THEN 'true' ELSE 'false' END FROM OperationMaster om "
			+ " where om.pageMaster.id in ?1 and om.name in ?2")
    public Boolean existsByPageIdAndOpName(Integer pageId,String oPName);
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM OperationMaster om  where om.pageMaster.id in ?1 and om.name in ?2 and om.id <> ?3")	
	boolean existsByPageIdAndOpNameExceptThisId(Integer pageId, String oPName, Integer id);
	
	//Uniqueness query for second child with parent
	
	
	//Uniqueness query for third child with parent
	@Query("SELECT CASE WHEN COUNT(om.name) > 0 THEN 'true' ELSE 'false' END FROM OperationMaster om "
			+ " where om.pageMaster.id in ?1 and om.opurl in ?2")
    public Boolean existsByPageIdAndOpUrlName(Integer pageId,String opUrl);
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM OperationMaster om  where om.pageMaster.id in ?1 and om.opurl in ?2 and om.id <> ?3")	
	boolean existsByPageIdAndOpUrlNameExceptThisId(Integer pageId, String oPName, Integer id);
	
	

}
