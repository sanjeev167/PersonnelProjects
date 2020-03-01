/**
 * 
 */
package com.util;

/**
 * @author Sanjeev
 *
 */
public class AppConstants {	
	//This flag will be used for updating access rights if changed
	public static boolean securityUpdate=false;
	
	//Persistence unit i.e. two enterprise mangers have been registered with these names while datasource configuration
    public static final String JPA_UNIT_PRACTICEONNET ="PracticeOnNet";
    public static final String JPA_UNIT_ACL ="ACL";
	
	//Define all the entities package names here.    Following will use PracticeOnNet
    public static final String[] PON_PKG_ENTITIES_ARRAY=new String[] {"com.admin.pvt.masters.entity",
    		                                          "com.admin.pvt.sec.env.entity",
    		                                          "com.admin.pvt.sec.rbac.entity",    		                                         
    		                                          "com.admin.pvt.sec.acl.entity",
    		                                          "com.admin.pvt.sec.acl.entity",
    		                                          
    		                                          "com.admin.pvt.menu_mgr.entity",
    		                                         // "com.user.modal.UserAddress",
    		                                          //"com.user.modal.UserJobDetail",
    		                                          //"com.user.modal.UserPersonelDetail"
    		                                          "com.admin.pub.local_en.entity"
                                                      };
    
    
  //Define all the repositories package names here.	Following will use PracticeOnNet
    public static final String PKG_REPO_MASTERS = "com.admin.pvt.masters.repo";
    public static final String PKG_REPO_ENV = "com.admin.pvt.sec.env.repo";
    public static final String PKG_REPO_MENU_MGR = "com.admin.pvt.menu_mgr.repo";
    public static final String PKG_REPO_RBAC = "com.admin.pvt.sec.rbac.repo";       
    public static final String PKG_REPO_ACL = "com.admin.pvt.sec.acl.repo"; 
    public static final String PKG_REPO_WEB_PUB = "com.user.pub.local_en.repo"; 
    
    
    
    
    
    
  //This will use ACL
    public static final String[] ACL_MONITOR_PKG_ENTITIES_ARRAY=new String[] { "com.admin.pvt.sec.acl_monitor.entity"};
     
    //This will use ACL
    public static final String PKG_REPO_ACL_MONITOR = "com.admin.pvt.sec.acl_monitor.repo";
   
     
}
