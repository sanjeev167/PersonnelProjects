/**
 * 
 */
package com.admin.pvt.menu_mgr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.pvt.menu_mgr.entity.TreeMenuTypeMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface TreeMenuTypeMasterRepository extends JpaRepository<TreeMenuTypeMaster, Integer>{

}
