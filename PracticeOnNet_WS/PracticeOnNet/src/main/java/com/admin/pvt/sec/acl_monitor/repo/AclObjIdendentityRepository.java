/**
 * 
 */
package com.admin.pvt.sec.acl_monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.admin.pvt.sec.acl_monitor.entity.AclObjectIdentity;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AclObjIdendentityRepository extends JpaRepository<AclObjectIdentity, Long>{
	

}
