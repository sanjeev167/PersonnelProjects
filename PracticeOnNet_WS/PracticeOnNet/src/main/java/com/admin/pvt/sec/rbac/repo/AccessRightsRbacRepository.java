/**
 * 
 */
package com.admin.pvt.sec.rbac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.sec.rbac.entity.AccessRightsRbac;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AccessRightsRbacRepository extends JpaRepository<AccessRightsRbac, Integer> {
	
	@Query(value = "SELECT * FROM AccessRightsRbac", nativeQuery = true)
	List<AccessRightsRbac> findAllByName(List<String> listOfStateNames);
	
	@Modifying
	@Transactional
	@Query("delete from AccessRightsRbac om where om.id in ?1")
	void deleteOperationWithIds(Integer[] recordIdArray);

	//Using named parameter in Query method
	//@Query("Select arr from AccessRightsRbac arr where arr.appRole.id=:roleId and arr.pageMaster.id=:pageId")	
	//List<AccessRightsRbac> loadOperationOnPage(@Param("roleId") Integer roleId, @Param("pageId") Integer pageId);
			
	 //position based parameter binding:
	@Query("Select arr from AccessRightsRbac arr where arr.appRole.id=?1 and arr.pageMaster.id=?2")	
	List<AccessRightsRbac> loadOperationOnPage(Integer roleId, Integer pageId);
			
		
}
