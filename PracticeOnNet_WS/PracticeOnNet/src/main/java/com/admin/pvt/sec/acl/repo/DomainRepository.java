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

import com.admin.pvt.sec.acl.entity.AclDomainMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface DomainRepository extends JpaRepository<AclDomainMaster, Integer> {

	@Query(value = "SELECT * FROM AclDomainMaster", nativeQuery = true)
	List<AclDomainMaster> findAllByName(List<String> listOfStateNames);

	@Modifying
	@Transactional
	@Query("delete from AclDomainMaster pm where pm.id in ?1")
	void deleteDomainWithIds(Integer[] recordIdArray);

	// Will be used for checking uniqueness of domain name with module name

	@Query("SELECT CASE WHEN COUNT(adm.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainMaster adm where adm.moduleMaster.id in ?1 and adm.name in ?2")
	public Boolean existsByModuleIdAndDomainName(Integer moduleId, String domainName);

	@Query("SELECT CASE WHEN COUNT(adm.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainMaster adm where adm.moduleMaster.id in ?1 and adm.name in ?2 and adm.id <> ?3")
	boolean existsByModuleIdAndDomainNameExceptThisId(Integer moduleId, String domainName, int id);

	// Will be used for checking uniqueness of domain name with module name

	@Query("SELECT CASE WHEN COUNT(adm.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainMaster adm where adm.moduleMaster.id in ?1 and adm.packageName in ?2")
	public Boolean existsByModuleIdAndDomainPkgName(Integer moduleId, String domainName);

	@Query("SELECT CASE WHEN COUNT(adm.name) > 0 THEN 'true' ELSE 'false' END FROM AclDomainMaster adm where adm.moduleMaster.id in ?1 and adm.packageName in ?2 and adm.id <> ?3")
	boolean existsByModuleIdAndDomainPkgNameExceptThisId(Integer moduleId, String packageName, int id);

}
