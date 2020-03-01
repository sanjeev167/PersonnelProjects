/**
 * 
 */
package com.admin.pvt.sec.rbac.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.pvt.sec.rbac.entity.OperationAccess;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface OperationAccessRepository extends JpaRepository<OperationAccess, Integer> {
	
	
			
		
}
