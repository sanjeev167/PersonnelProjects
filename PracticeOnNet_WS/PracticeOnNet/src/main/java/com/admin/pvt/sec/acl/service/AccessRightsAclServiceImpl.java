/**
 * 
 */
package com.admin.pvt.sec.acl.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.acl.dto.AccessRightsAclDTO;
import com.admin.pvt.sec.acl.dto.DomainMasterDTO;
import com.admin.pvt.sec.acl.entity.AclDomainAccessRight;
import com.admin.pvt.sec.acl.entity.AclDomainActionAccess;
import com.admin.pvt.sec.acl.entity.AclDomainActionMaster;
import com.admin.pvt.sec.acl.entity.AclDomainMaster;
import com.admin.pvt.sec.acl.repo.AccessRightsAclRepository;
import com.admin.pvt.sec.acl.repo.AclDomainActionAccessRepository;
import com.admin.pvt.sec.acl.repo.DomainActionRepository;
import com.admin.pvt.sec.acl.repo.DomainRepository;
import com.admin.pvt.sec.acl_monitor.entity.AclSid;
import com.admin.pvt.sec.acl_monitor.repo.AclSidRepository;
import com.admin.pvt.sec.env.repo.DepartmentRepository;
import com.admin.pvt.sec.env.repo.ModuleRepository;
import com.admin.pvt.sec.rbac.entity.AppRole;
import com.admin.pvt.sec.rbac.repo.AppAdminUserRepository;
import com.admin.pvt.sec.rbac.repo.AppRoleRepository;
import com.custom.exception.CustomRuntimeException;
import com.custom.exception.ExceptionApplicationUtility;
import com.grid_pagination.DataTableRequest;
import com.grid_pagination.DataTableResults;
import com.grid_pagination.PaginationCriteria;
import com.util.AppConstants;
import com.util.AppUtil;

/**
 * @author Sanjeev
 *
 */
@Service
public class AccessRightsAclServiceImpl implements AccessRightsAclService {

	static final Logger log = LoggerFactory.getLogger(AccessRightsAclServiceImpl.class);

	@Autowired
	AclSidRepository aclSidRepository;

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppConstants.JPA_UNIT_PRACTICEONNET)
	private EntityManager entityManager;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	DomainRepository domainRepository;
	@Autowired
	DomainActionRepository domainActionRepository;

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	AccessRightsAclRepository accessRightsAclRepository;

	@Autowired
	AclDomainActionAccessRepository aclDomainActionAccessRepository;
	@Autowired
	AppAdminUserRepository appAdminUserRepository;

	@Override
	public DataTableResults<DomainMasterDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> loadGrid ==> Started");
		DataTableResults<DomainMasterDTO> dataTableResult = null;
		try {
			DataTableRequest<AclDomainMaster> dataTableInRQ = new DataTableRequest<AclDomainMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select adm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " adm.name as domainName," + " (SELECT COUNT(1) FROM acl_domain_master ) AS totalrecords"
						+ " from acl_domain_master adm, module_master mm , department_master dm"
						+ " where adm.module_id=mm.id and mm.department_id=dm.id ";
			else
				baseQuery = "select adm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " adm.name as domainName," + " (SELECT COUNT(1) FROM acl_domain_master  where "
						+ whereClauseForBaseQuery + " ) AS totalrecords"
						+ " from acl_domain_master adm, module_master mm , department_master dm"
						+ " where adm.module_id=mm.id and mm.department_id=dm.id and " + whereClauseForBaseQuery;

			// System.out.println("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "DomainMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<DomainMasterDTO> aclDomainMasterList = query.getResultList();

			dataTableResult = new DataTableResults<DomainMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(aclDomainMasterList);
			if (!AppUtil.isObjectEmpty(aclDomainMasterList)) {
				dataTableResult.setRecordsTotal(aclDomainMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(aclDomainMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(aclDomainMasterList.size()));
				}
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public AccessRightsAclDTO getReordById(Integer id) throws CustomRuntimeException {

		log.info("     AccessRightsAclServiceImpl :==> getReordById ==> Started");
		AccessRightsAclDTO accessRightsAclDTO = new AccessRightsAclDTO();
		try {
			AclDomainMaster aclDomainMaster = domainRepository.getOne(id);
			accessRightsAclDTO
					.setDepartmentNameId(aclDomainMaster.getModuleMaster().getDepartmentMaster().getId() + "");
			accessRightsAclDTO.setDepartmentName(aclDomainMaster.getModuleMaster().getDepartmentMaster().getName());

			accessRightsAclDTO.setModuleNameId(aclDomainMaster.getModuleMaster().getId() + "");
			accessRightsAclDTO.setModuleName(aclDomainMaster.getModuleMaster().getName());

			accessRightsAclDTO.setDomainNameId(aclDomainMaster.getId() + "");
			accessRightsAclDTO.setDomainName(aclDomainMaster.getName());
			// Set all the roles available in the department with name and id

			accessRightsAclDTO.setRoleListInDepartment(
					getRoleListInDepartment(aclDomainMaster.getModuleMaster().getDepartmentMaster().getAppRoles(),
							aclDomainMaster.getAclDomainAccessRights()));
			// Set all the actions defined on this particular page with its name and id
			accessRightsAclDTO
					.setAllActionsDefinedOnThisDomain(getAllActionDefinedOnThisDomain(aclDomainMaster.getId()));
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> getReordById ==> Ended");
		return accessRightsAclDTO;
	}

	@Override
	public List<NameValue> getAllActionDefinedOnThisDomain(Integer domainId) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> getAllActionDefinedOnThisDomain ==> Started");
		List<NameValue> actionListWithNameAndId = new ArrayList<NameValue>();
		NameValue nameValue=null;
		try {			
			for (AclDomainActionMaster aclDomainActionMaster : domainRepository.getOne(domainId)
					.getAclDomainActionMasters()) {
				nameValue = new NameValue(aclDomainActionMaster.getId(), aclDomainActionMaster.getName());
				actionListWithNameAndId.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> getAllActionDefinedOnThisDomain ==> Ended");
		return actionListWithNameAndId;
	}

	@Override
	public List<NameValue> getAllActionRightsOnThisDomain(Integer accessRightsAclId) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> getAllActionRightsOnThisDomain ==> Started");
		List<NameValue> aclListWithNameAndId = new ArrayList<NameValue>();
		NameValue nameValue = null;
		try {			
			for (AclDomainActionAccess aclDomainActionAccess : accessRightsAclRepository.getOne(accessRightsAclId)
					.getAclDomainActionAccesses()) {
				nameValue = new NameValue(aclDomainActionAccess.getAclDomainActionMaster().getId(),
						aclDomainActionAccess.getAclDomainActionMaster().getName());
				aclListWithNameAndId.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> getAllActionRightsOnThisDomain ==> Ended");
		return aclListWithNameAndId;
	}

	@Override
	public List<NameValue> getRoleListHavingRightsOnThisDomain(List<AclDomainAccessRight> accessRightsAclList) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> getRoleListHavingRightsOnThisDomain ==> Started");
		List<NameValue> roleListWithNameAndId = new ArrayList<NameValue>();
		NameValue nameValue = null;
		try {			
			for (AclDomainAccessRight aclDomainAccessRight : accessRightsAclList) {
				nameValue = new NameValue(aclDomainAccessRight.getAppRole().getId(),
						aclDomainAccessRight.getAppRole().getRoleName());
				roleListWithNameAndId.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> getRoleListHavingRightsOnThisDomain ==> Ended");
		return roleListWithNameAndId;
	}

	@Override
	public List<NameValue> getRoleListInDepartment(List<AppRole> roleListInDepartment,
			List<AclDomainAccessRight> accessRightsAclList) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> getRoleListInDepartment ==> Started");
		List<NameValue> roleListWithNameAndId = new ArrayList<NameValue>();
		NameValue nameValue = null;
		Integer accessCount = 0;
		try {
			for (AppRole appRole : roleListInDepartment) {
				for (int j = 0; j < accessRightsAclList.size(); j++) {
					// Execute only when the role id is present in accessRightsAcl
					if (accessRightsAclList.get(j).getAppRole() != null)
						if (appRole.getId() == accessRightsAclList.get(j).getAppRole().getId())
							accessCount = accessRightsAclList.get(j).getAclDomainActionAccesses().size();
				}
				nameValue = new NameValue(appRole.getId(), appRole.getRoleName(), accessCount);
				accessCount = 0;
				roleListWithNameAndId.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> getRoleListInDepartment ==> Ended");
		return roleListWithNameAndId;
	}

	@Override
	public boolean saveAndUpdate(String accessRightsAclId, String domainId, String roleId, String userId,
			String recordIdArray[], String permissionBase) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> saveAndUpdate ==> Started");

		AclDomainAccessRight aclDomainAccessRight = null;
		AclSid aclSid = null;
		boolean isRecordSaved=true;
		try {
			if (accessRightsAclId.equals("")) {
				// New Access rights
				// For ACL table
				aclSid = prepareAclSidForSaveAndUpdate(permissionBase, roleId, userId);
				AclSid returnedAclSid = aclSidRepository.saveAndFlush(aclSid);
				// For PracticeOnNet table
				aclDomainAccessRight = new AclDomainAccessRight();
				aclDomainAccessRight.setAclDomainMaster(domainRepository.getOne(Integer.parseInt(domainId)));
				if (permissionBase.equals("R"))
					aclDomainAccessRight.setAppRole(appRoleRepository.getOne(Integer.parseInt(roleId)));
				else {
					aclDomainAccessRight.setUserReg(appAdminUserRepository.getOne(Integer.parseInt(userId)));
				}
				aclDomainAccessRight
						.setAclDomainActionAccesses(prepareActionsAccessList(aclDomainAccessRight, recordIdArray));
				aclDomainAccessRight.setPermisssionTo(permissionBase);
				// Set Acl Sid reference
				aclDomainAccessRight.setSidRef(returnedAclSid.getId());
				accessRightsAclRepository.saveAndFlush(aclDomainAccessRight);
			} else {
				// Old Access rights
				// For PracticeOnNet table
				aclDomainAccessRight = accessRightsAclRepository.getOne(Integer.parseInt(accessRightsAclId));
				// aclDomainAccessRight.setPermisssionTo(permissionBase);
				if (recordIdArray.length != 0) {
					updateAllExistingPermission(aclDomainAccessRight, recordIdArray);
					// Not required any update in acl sid as permissions are getting updated only.
				} else {
					accessRightsAclRepository.delete(aclDomainAccessRight);
					// Do delete in acl sid if required
					aclSidRepository.deleteById(aclDomainAccessRight.getSidRef());
				}
			}

		} catch (Exception ex) {
			isRecordSaved=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}

		log.info("     AccessRightsAclServiceImpl :==> saveAndUpdate ==> Ended");
		return isRecordSaved;
	}

	public AclSid prepareAclSidForSaveAndUpdate(String permissionBase, String roleId, String userId) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> prepareAclSidForSaveAndUpdate ==> Started");
		AclSid aclSid = new AclSid();
		try {			
			if (permissionBase.equals("R")) {
				aclSid.setPrincipal(false);
				aclSid.setSid(appRoleRepository.getOne(Integer.parseInt(roleId)).getRoleName());
			} else {
				aclSid.setPrincipal(true);
				aclSid.setSid(appAdminUserRepository.getOne(Integer.parseInt(userId)).getEmail());
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> prepareAclSidForSaveAndUpdate ==> Ended");
		return aclSid;
	}

	public void updateAllExistingPermission(AclDomainAccessRight existingAccessRightsAcl, String recordIdArray[]) throws CustomRuntimeException {

		log.info("     AccessRightsAclServiceImpl :==> updateAllExistingPermission ==> Started");
		try {
			// Fetch all existing permission first
			List<AclDomainActionAccess> aclDomainActionAccessList = existingAccessRightsAcl
					.getAclDomainActionAccesses();
			List<AclDomainActionAccess> aclDomainActionAccessListForInsert = new ArrayList<>();
			List<AclDomainActionAccess> aclDomainActionAccessForDelete = new ArrayList<>();
			// Pick ids for inserting new one and leaving old one as it if the old id is
			// coming
			int idComing;
			for (String id : recordIdArray) {
				idComing = Integer.parseInt(id);
				if (matchComingIdWithExistingList(idComing, aclDomainActionAccessList)) {
					// ComingId is old one. Leave as it is
					;
				} else {
					// ComingId is new one
					AclDomainActionAccess aclDomainActionAccess = new AclDomainActionAccess();
					aclDomainActionAccess.setAclDomainAccessRight(existingAccessRightsAcl);
					aclDomainActionAccess.setAclDomainActionMaster(domainActionRepository.getOne(idComing));
					aclDomainActionAccessListForInsert.add(aclDomainActionAccess);
				}
			}
			// Pick all those ids which are in existing but not coming. These ids will be
			// deleted
			int idExisting;
			for (AclDomainActionAccess aclDomainActionAccess : aclDomainActionAccessList) {
				idExisting = aclDomainActionAccess.getAclDomainActionMaster().getId();
				if (matchExistingIdWithComingId(idExisting, recordIdArray)) {
					// Leave as it is
					;
				} else {
					// Delete this ids
					aclDomainActionAccessForDelete.add(aclDomainActionAccess);
				}
			}

			// Now do bulk delete and bulk insert
			aclDomainActionAccessRepository.deleteInBatch(aclDomainActionAccessForDelete);
			aclDomainActionAccessRepository.saveAll(aclDomainActionAccessListForInsert);
			// if no op id is coming, delete all the access entry

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> updateAllExistingPermission ==> Ended");

	}// End of updateAllExistingPermission

	public boolean matchComingIdWithExistingList(Integer idComing,
			List<AclDomainActionAccess> aclDomainActionAccessList) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> matchComingIdWithExistingList ==> Started");
		boolean comparedStatus = false;
		try {
			for (AclDomainActionAccess aclDomainActionAccess : aclDomainActionAccessList) {
				if (aclDomainActionAccess.getAclDomainActionMaster().getId() == idComing)
					comparedStatus = true;
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> matchComingIdWithExistingList ==> Ended");
		return comparedStatus;

	}

	public boolean matchExistingIdWithComingId(Integer idExisting, String recordIdArray[]) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> matchExistingIdWithComingId ==> Started");
		boolean comparedStatus = false;
		try {
			for (String id : recordIdArray) {
				if (Integer.parseInt(id) == idExisting)
					comparedStatus = true;
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> matchExistingIdWithComingId ==> Ended");
		return comparedStatus;

	}

	private List<AclDomainActionAccess> prepareActionsAccessList(AclDomainAccessRight aclDomainAccessRight,
			String recordIdArray[]) throws CustomRuntimeException {

		log.info("     AccessRightsAclServiceImpl :==> prepareActionsAccessList ==> Started");
		List<AclDomainActionAccess> actionAccessList = new ArrayList<>();
		try {
			for (int i = 0; i < recordIdArray.length; i++) {
				AclDomainActionAccess aclDomainActionAccess = new AclDomainActionAccess();
				aclDomainActionAccess.setAclDomainAccessRight(aclDomainAccessRight);
				aclDomainActionAccess
						.setAclDomainActionMaster(domainActionRepository.getOne(Integer.parseInt(recordIdArray[i])));
				actionAccessList.add(aclDomainActionAccess);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> prepareActionsAccessList ==> Ended");
		return actionAccessList;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> deleteOneRecord ==> Started");
		boolean isDeleted = true;
		try {
			accessRightsAclRepository.deleteById(id);
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> deleteOneRecord ==> Ended");
		return isDeleted;
	}

	@Transactional
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {

		log.info("     AccessRightsAclServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isDeleted = true;
		try {
			accessRightsAclRepository.deleteOperationWithIds(recordIdArray);

			log.info("     AccessRightsAclServiceImpl :==> deleteMultipleRecords ==> Ended");
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		return isDeleted;
	}

	@Override
	public AccessRightsAclDTO loadActionsOnRoleSelection(Integer roleId, Integer domainId) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> loadActionsOnRoleSelection ==> Started");
		List<AclDomainActionAccess> actionAccessList = null;
		AccessRightsAclDTO accessRightsAclDTO = null;
		try {
			List<AclDomainAccessRight> accessRightsAclList = accessRightsAclRepository
					.loadActionsOnRoleSelection(roleId, domainId);			

			if (accessRightsAclList.size() != 0) {
				AclDomainAccessRight aclDomainAccessRight = accessRightsAclList.get(0);
				accessRightsAclDTO = new AccessRightsAclDTO();
				accessRightsAclDTO.setAccessRightsAclId(aclDomainAccessRight.getId() + "");
				accessRightsAclDTO.setRoleName(aclDomainAccessRight.getAppRole().getRoleName());
				accessRightsAclDTO.setRoleNameId(roleId + "");
				accessRightsAclDTO.setDomainNameId(domainId + "");
				accessRightsAclDTO.setDomainName(aclDomainAccessRight.getAclDomainMaster().getName());
				accessRightsAclDTO.setAllActionsDefinedOnThisDomain(getAllActionDefinedOnThisDomain(
						aclDomainAccessRight.getAclDomainMaster().getAclDomainActionMasters(),
						aclDomainAccessRight.getAclDomainActionAccesses()));
			} else {

				accessRightsAclDTO = new AccessRightsAclDTO();
				accessRightsAclDTO.setAccessRightsAclId("");
				accessRightsAclDTO.setRoleName(appRoleRepository.getOne(roleId).getRoleName());
				accessRightsAclDTO.setRoleNameId(roleId + "");
				accessRightsAclDTO.setDomainNameId(domainId + "");
				accessRightsAclDTO.setDomainName(domainRepository.getOne(domainId).getName());
				accessRightsAclDTO.setAllActionsDefinedOnThisDomain(getAllActionDefinedOnThisDomain(
						domainRepository.getOne(domainId).getAclDomainActionMasters(), actionAccessList));
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> loadActionsOnRoleSelection ==> Ended");
		return accessRightsAclDTO;
	}

	@Override
	public AccessRightsAclDTO loadActionsOnUserSelection(Integer roleId, Integer userId, Integer domainId) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> loadActionsOnUserSelection ==> Started");
		List<AclDomainActionAccess> actionAccessList = null;
		AccessRightsAclDTO accessRightsAclDTO = null;
		try {
			List<AclDomainAccessRight> accessRightsAclList = accessRightsAclRepository
					.loadActionsOnUserSelection(userId, domainId);

			
			if (accessRightsAclList.size() != 0) {
				// This will prepare the list of actions defined on the selected domain as well
				// as select the actions which have already been assigned.
				AclDomainAccessRight aclDomainAccessRight = accessRightsAclList.get(0);
				accessRightsAclDTO = new AccessRightsAclDTO();
				accessRightsAclDTO.setAccessRightsAclId(aclDomainAccessRight.getId() + "");

				accessRightsAclDTO.setRoleName(appRoleRepository.getOne(roleId).getRoleName());
				accessRightsAclDTO.setRoleNameId(appRoleRepository.getOne(roleId).getId() + "");

				accessRightsAclDTO.setUserName(aclDomainAccessRight.getUserReg().getEmail());
				accessRightsAclDTO.setUserNameId(aclDomainAccessRight.getUserReg().getId() + "");

				accessRightsAclDTO.setDomainNameId(domainId + "");
				accessRightsAclDTO.setDomainName(aclDomainAccessRight.getAclDomainMaster().getName());
				accessRightsAclDTO.setAllActionsDefinedOnThisDomain(getAllActionDefinedOnThisDomain(
						aclDomainAccessRight.getAclDomainMaster().getAclDomainActionMasters(),
						aclDomainAccessRight.getAclDomainActionAccesses()));
			} else {

				// This case is executed when no action is assigned to this user.It will prepare
				// action lists defined on the domain
				accessRightsAclDTO = new AccessRightsAclDTO();
				accessRightsAclDTO.setAccessRightsAclId("");
				accessRightsAclDTO.setRoleName(appRoleRepository.getOne(roleId).getRoleName());
				accessRightsAclDTO.setRoleNameId(roleId + "");

				accessRightsAclDTO.setUserName(appAdminUserRepository.getOne(userId).getEmail());
				accessRightsAclDTO.setUserNameId(appAdminUserRepository.getOne(userId).getId() + "");

				accessRightsAclDTO.setDomainNameId(domainId + "");
				accessRightsAclDTO.setDomainName(domainRepository.getOne(domainId).getName());
				accessRightsAclDTO.setAllActionsDefinedOnThisDomain(getAllActionDefinedOnThisDomain(
						domainRepository.getOne(domainId).getAclDomainActionMasters(), actionAccessList));
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> loadActionsOnUserSelection ==> Ended");
		return accessRightsAclDTO;
	}

	private List<NameValue> getAllActionDefinedOnThisDomain(List<AclDomainActionMaster> aclDomainActionMasterList, List<AclDomainActionAccess> aclDomainActionAccessList) throws CustomRuntimeException {
		log.info("     AccessRightsAclServiceImpl :==> getAllActionDefinedOnThisDomain ==> Started");

		List<NameValue> actionOnDomainListNew = new ArrayList<NameValue>();
		NameValue nameValue = null;
		try {
			for (AclDomainActionMaster aclDomainActionMaster : aclDomainActionMasterList) {
				if (isThisAccessAllReadyAssigned(aclDomainActionMaster.getId(), aclDomainActionAccessList)) {
					nameValue = new NameValue(aclDomainActionMaster.getId(), aclDomainActionMaster.getName(),
							"checked");
				} else {
					nameValue = new NameValue(aclDomainActionMaster.getId(), aclDomainActionMaster.getName(), "");
				}
				actionOnDomainListNew.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> getAllActionDefinedOnThisDomain ==> Ended");
		return actionOnDomainListNew;
	}

	private boolean isThisAccessAllReadyAssigned(Integer actionId, List<AclDomainActionAccess> aclDomainActionAccessList) throws CustomRuntimeException {

		log.info("     AccessRightsAclServiceImpl :==> isThisAccessAllReadyAssigned ==> Started");
		boolean isAssigned = false;
		try {
			if (aclDomainActionAccessList != null)
				for (AclDomainActionAccess aclDomainActionAccess : aclDomainActionAccessList) {
					if (aclDomainActionAccess.getAclDomainActionMaster().getId() == actionId)
						isAssigned = true;
				}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AccessRightsAclServiceImpl :==> isThisAccessAllReadyAssigned ==> Ended");
		return isAssigned;
	}

}
