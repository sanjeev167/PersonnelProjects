/**
 * 
 */
package com.admin.pvt.masters.repo;
import java.util.List;

import com.admin.pvt.masters.entity.CountryMaster;
/**
 * @author Sanjeev
 *
 */
public interface GenericRepo {
	
	List<CountryMaster> getCountryMasterList();
	
	
}//end of GenericRepo