/**
 * 
 */
package com.admin.pub.local_en.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.admin.pvt.sec.rbac.entity.AccessRightsRbac;
import com.admin.pvt.sec.rbac.entity.AppGroupRole;
import com.admin.pvt.sec.rbac.entity.AppRole;
import com.admin.pvt.sec.rbac.entity.OperationAccess;
import com.admin.pvt.sec.rbac.entity.UserGroup;
import com.admin.pvt.sec.rbac.entity.UserReg;

/**
 * @author Sanjeev
 *
 */
public class CustomAdministrativeUserDetailsImpl extends UserReg implements UserDetails {

	private static final long serialVersionUID = 1L;
		
	//@Override
	public Collection<? extends GrantedAuthority> getAuthorities(UserReg userReg) {

		List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<SimpleGrantedAuthority>();
		
		// Call suitable method for knowing the group assigned with this particular user
		List<UserGroup> userGroups = userReg.getUserGroups();//Through webUser object method		
		List<AppGroupRole> appGroupRoles = null;
		for (UserGroup userGroup : userGroups) {			
			// Once the group is known, take out all the roles associated with this group
			appGroupRoles = userGroup.getAppGroup().getAppGroupRoles();			
			AppRole appRole = null;
			List<AccessRightsRbac> accessRightsRbacs = null;
			//Now start taking out access rights and roles for each group and collect them in a list
			for (AppGroupRole appGroupRole : appGroupRoles) {
				// Now start collecting all the roles defined for the groups
				appRole = appGroupRole.getAppRole();				
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
						"ROLE_" + appRole.getRoleName());			
				simpleGrantedAuthorityList.add(simpleGrantedAuthority);				
			}
		}		
		return simpleGrantedAuthorityList;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}	
}