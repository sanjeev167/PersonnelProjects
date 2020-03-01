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

import com.admin.pvt.sec.acl.entity.AclDomainActionMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface DomainActionRepository extends JpaRepository<AclDomainActionMaster, Integer> {

	@Query(value = "SELECT * FROM AclDomainActionMaster", nativeQuery = true)
	List<AclDomainActionMaster> findAllByName(List<String> listOfStateNames);

	@Modifying
	@Transactional
	@Query("delete from AclDomainActionMaster adam where adam.id in ?1")
	void deleteOperationWithIds(Integer[] recordIdArray);

	// Will be used for checking uniqueness of action name with domain name
	@Query("SELECT CASE WHEN COUNT(adam.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainActionMaster adam where adam.aclDomainMaster.id in ?1 and adam.name in ?2")
	public Boolean existsByDomainIdAndActionName(Integer domainId, String actionName);

	@Query("SELECT CASE WHEN COUNT(adam.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainActionMaster adam where adam.aclDomainMaster.id in ?1 and adam.name in ?2 and adam.id <> ?3")
	boolean existsByDomainIdAndActionNameExceptThisId(Integer domainId, String actionName, int id);

	// Will be used for checking uniqueness of action sort name with domain name
	@Query("SELECT CASE WHEN COUNT(adam.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainActionMaster adam where adam.aclDomainMaster.id in ?1 and adam.sortName in ?2")
	public Boolean existsByDomainIdAndSortName(Integer domainId, String sortName);

	@Query("SELECT CASE WHEN COUNT(adam.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainActionMaster adam where adam.aclDomainMaster.id in ?1 and adam.sortName in ?2 and adam.id <> ?3")
	boolean existsByDomainIdAndSortNameExceptThisId(Integer domainId, String sortName, int id);
	
	
	// Will be used for checking uniqueness of action number with domain name
	@Query("SELECT CASE WHEN COUNT(adam.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainActionMaster adam where adam.aclDomainMaster.id in ?1 and adam.actionNumber in ?2")
	public Boolean existsByDomainIdAndActionNumber(Integer domainId, Integer actionNumber);

	@Query("SELECT CASE WHEN COUNT(adam.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainActionMaster adam where adam.aclDomainMaster.id in ?1 and adam.actionNumber in ?2 and adam.id <> ?3")
	boolean existsByDomainIdAndActionNumberExceptThisId(Integer domainId, Integer actionNumber, int id);

}
