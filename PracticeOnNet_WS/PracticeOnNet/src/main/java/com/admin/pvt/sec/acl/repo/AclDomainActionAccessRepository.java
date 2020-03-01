/**
 * 
 */
package com.admin.pvt.sec.acl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.pvt.sec.acl.entity.AclDomainActionAccess;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AclDomainActionAccessRepository extends JpaRepository<AclDomainActionAccess, Integer> {
	
	
			
		
}
