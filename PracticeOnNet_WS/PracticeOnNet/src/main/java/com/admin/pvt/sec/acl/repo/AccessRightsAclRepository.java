/**
 * 
 */
package com.admin.pvt.sec.acl.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.sec.acl.entity.AclDomainAccessRight;


/**
 * @author Sanjeev
 *
 */
@Repository
public interface AccessRightsAclRepository extends JpaRepository<AclDomainAccessRight, Integer> {
	
	@Query(value = "SELECT * FROM AclDomainAccessRight", nativeQuery = true)
	List<AclDomainAccessRight> findAllByName(List<String> listOfStateNames);
	
	@Modifying
	@Transactional
	@Query("delete from AclDomainAccessRight adar where adar.id in ?1")
	void deleteOperationWithIds(Integer[] recordIdArray);

	//Using named parameter in Query method
	//@Query("Select arr from AclDomainAccessRight arr where arr.appRole.id=:roleId and arr.pageMaster.id=:pageId")	
	//List<AclDomainAccessRight> loadOperationOnPage(@Param("roleId") Integer roleId, @Param("pageId") Integer pageId);
			
	 //position based parameter binding:
	@Query("Select adar from AclDomainAccessRight adar where adar.appRole.id=?1 and adar.aclDomainMaster.id=?2")	
	List<AclDomainAccessRight> loadActionsOnRoleSelection(Integer roleId, Integer domainNameId);
	
	
	//position based parameter binding:
		@Query("Select adar from AclDomainAccessRight adar where adar.userReg.id=?1 and adar.aclDomainMaster.id=?2")	
		List<AclDomainAccessRight> loadActionsOnUserSelection(Integer userId, Integer domainNameId);
			
		
}
