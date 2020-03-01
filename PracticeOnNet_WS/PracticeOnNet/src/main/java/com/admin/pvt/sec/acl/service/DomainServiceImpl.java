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

import org.modelmapper.internal.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.masters.dto.NameValue;
import com.admin.pvt.sec.acl.dto.DomainMasterDTO;
import com.admin.pvt.sec.acl.entity.AclDomainActionMaster;
import com.admin.pvt.sec.acl.entity.AclDomainMaster;
import com.admin.pvt.sec.acl.repo.DomainActionRepository;
import com.admin.pvt.sec.acl.repo.DomainRepository;
import com.admin.pvt.sec.acl_monitor.entity.AclClass;
import com.admin.pvt.sec.acl_monitor.repo.AclClassRepository;
import com.admin.pvt.sec.env.repo.DepartmentRepository;
import com.admin.pvt.sec.env.repo.ModuleRepository;
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
public class DomainServiceImpl implements DomainService {

	static final Logger log = LoggerFactory.getLogger(DomainServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppConstants.JPA_UNIT_ACL)
	private EntityManager aclEntityManager;
	@Autowired
	AclClassRepository aclClassRepository;

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

	@Override
	public DataTableResults<DomainMasterDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {

		log.info("     DomainServiceImpl :==> loadGrid ==> Started");

		DataTableResults<DomainMasterDTO> dataTableResult = null;
		try {
			DataTableRequest<AclDomainMaster> dataTableInRQ = new DataTableRequest<AclDomainMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select adm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " adm.name as domainName," + " adm.package_name as packageName,"
						+ " (SELECT COUNT(1) FROM acl_domain_master ) AS totalrecords"
						+ " from acl_domain_master adm, module_master mm , department_master dm"
						+ " where adm.module_id=mm.id and mm.department_id=dm.id ";
			else
				baseQuery = "select adm.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " adm.name as domainName," + " adm.package_name as packageName,"
						+ " (SELECT COUNT(1) FROM acl_domain_master  where " + whereClauseForBaseQuery
						+ " ) AS totalrecords" + " from acl_domain_master adm, module_master mm , department_master dm"
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
		log.info("     DomainServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public DomainMasterDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> getReordById ==> Started");
		DomainMasterDTO domainMasterDTO = null;
		try {
			AclDomainMaster aclDomainMaster = domainRepository.getOne(id);
			domainMasterDTO = new DomainMasterDTO();
			domainMasterDTO.setId(aclDomainMaster.getId());
			domainMasterDTO.setDepartmentNameId(aclDomainMaster.getModuleMaster().getDepartmentMaster().getId() + "");
			domainMasterDTO.setModuleNameId(aclDomainMaster.getModuleMaster().getId() + "");
			domainMasterDTO.setDomainName(aclDomainMaster.getName());
			domainMasterDTO.setPackageName(aclDomainMaster.getPackageName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> getReordById ==> Ended");
		return domainMasterDTO;
	}

	@Override
	public DomainMasterDTO saveAndUpdate(DomainMasterDTO domainMasterDTO) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> saveAndUpdate ==> Started");
		DomainMasterDTO domainMasterDTONew = null;
		try {
			AclDomainMaster returnedDomainMaster;
			if (domainMasterDTO.getId() != null)
				returnedDomainMaster = updateDomain(domainMasterDTO);
			else
				returnedDomainMaster = saveDomain(domainMasterDTO);

			setAllBasicInitialActionsOnThisDomain(returnedDomainMaster);
			domainMasterDTONew = new DomainMasterDTO(returnedDomainMaster.getId(),
					returnedDomainMaster.getModuleMaster().getDepartmentMaster().getId() + "",
					returnedDomainMaster.getModuleMaster().getId() + "", returnedDomainMaster.getName(),
					returnedDomainMaster.getPackageName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> saveAndUpdate ==> Ended");
		return domainMasterDTONew;
	}

	private void setAllBasicInitialActionsOnThisDomain(AclDomainMaster returnedDomainMaster)
			throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> setAllBasicInitialActionsOnThisDomain ==> Started");
		AclDomainActionMaster aclDomainActionMaster = null;
		try {

			aclDomainActionMaster = new AclDomainActionMaster();
			aclDomainActionMaster.setName("CREATE");
			aclDomainActionMaster.setSortName("C");
			aclDomainActionMaster.setActionNumber(1);
			aclDomainActionMaster.setAclDomainMaster(returnedDomainMaster);
			domainActionRepository.saveAndFlush(aclDomainActionMaster);

			aclDomainActionMaster = new AclDomainActionMaster();
			aclDomainActionMaster.setName("READ");
			aclDomainActionMaster.setSortName("R");
			aclDomainActionMaster.setActionNumber(2);
			aclDomainActionMaster.setAclDomainMaster(returnedDomainMaster);
			domainActionRepository.saveAndFlush(aclDomainActionMaster);

			aclDomainActionMaster = new AclDomainActionMaster();
			aclDomainActionMaster.setName("UPDATE");
			aclDomainActionMaster.setSortName("U");
			aclDomainActionMaster.setActionNumber(3);
			aclDomainActionMaster.setAclDomainMaster(returnedDomainMaster);
			domainActionRepository.saveAndFlush(aclDomainActionMaster);

			aclDomainActionMaster = new AclDomainActionMaster();
			aclDomainActionMaster.setName("DELETE");
			aclDomainActionMaster.setSortName("D");
			aclDomainActionMaster.setActionNumber(4);
			aclDomainActionMaster.setAclDomainMaster(returnedDomainMaster);
			domainActionRepository.saveAndFlush(aclDomainActionMaster);

			aclDomainActionMaster = new AclDomainActionMaster();
			aclDomainActionMaster.setName("ADMINISTRATION");
			aclDomainActionMaster.setSortName("A");
			aclDomainActionMaster.setActionNumber(5);
			aclDomainActionMaster.setAclDomainMaster(returnedDomainMaster);
			domainActionRepository.saveAndFlush(aclDomainActionMaster);
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> setAllBasicInitialActionsOnThisDomain ==> Ended");
	}

	private AclDomainMaster updateDomain(DomainMasterDTO domainMasterDTO) throws CustomRuntimeException {

		log.info("     DomainServiceImpl :==> updateDomain ==> Started");
		AclDomainMaster aclDomainMaster = null;
		try {
			aclDomainMaster = domainRepository.getOne(domainMasterDTO.getId());
			AclClass aclClass = aclClassRepository.findById(aclDomainMaster.getAclDomainRefId()).get();

			// updating it in ACL table acl-sid
			aclClass.setClass_(domainMasterDTO.getPackageName());
			aclClassRepository.save(aclClass);// Will update if existed otherwise insert as a new one
			// updating it in PracticeOnNet
			aclDomainMaster.setId(domainMasterDTO.getId());
			aclDomainMaster.setName(domainMasterDTO.getDomainName());
			aclDomainMaster.setPackageName(domainMasterDTO.getPackageName());
			// set the reference id of acl-class entry in aclDomainMaster
			aclDomainMaster.setAclDomainRefId(aclClass.getId());

			aclDomainMaster
					.setModuleMaster(moduleRepository.getOne(Integer.parseInt(domainMasterDTO.getModuleNameId())));
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> updateDomain ==> Ended");
		return aclDomainMaster;
	}

	private AclDomainMaster saveDomain(DomainMasterDTO domainMasterDTO) throws CustomRuntimeException {

		log.info("     DomainServiceImpl :==> saveDomain ==> Started");
		AclDomainMaster returnedDomainMaster = null;
		AclDomainMaster aclDomainMaster = new AclDomainMaster();
		AclClass aclClass = new AclClass();
		try {
			// Prepare aclClass for updating it in ACL table acl-sid
			aclClass.setClass_(domainMasterDTO.getPackageName());
			AclClass returnedAclClass = aclClassRepository.saveAndFlush(aclClass);

			// Prepare aclDomainMaster for updating it in PracticeOnNet
			aclDomainMaster.setId(domainMasterDTO.getId());
			aclDomainMaster.setName(domainMasterDTO.getDomainName());
			aclDomainMaster.setPackageName(domainMasterDTO.getPackageName());
			// set the reference id of acl-class entry in aclDomainMaster
			aclDomainMaster.setAclDomainRefId(returnedAclClass.getId());

			aclDomainMaster
					.setModuleMaster(moduleRepository.getOne(Integer.parseInt(domainMasterDTO.getModuleNameId())));

			returnedDomainMaster = domainRepository.saveAndFlush(aclDomainMaster);
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> saveDomain ==> Ended");

		return returnedDomainMaster;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> deleteRecordById ==> Started");
		boolean isDeleted = true;
		try {
			aclClassRepository.deleteById(domainRepository.getOne(id).getAclDomainRefId());
			domainRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> deleteRecordById ==> Ended");
		return isDeleted;
	}

	@Transactional
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> deleteMultipleRecords ==> Started");

		List<AclDomainMaster> aclDomainMasterList = new ArrayList<AclDomainMaster>();
		List<AclClass> aclClassListList = new ArrayList<AclClass>();
		AclDomainMaster aclDomainMaster = null;
		boolean isDeleted = true;
		try {
			for (int i = 0; i < recordIdArray.length; i++) {
				aclDomainMaster = domainRepository.getOne(recordIdArray[i]);
				// Incase of batch delete of parent togather, prepare a list of parent entity
				// then do a batch delete
				aclDomainMasterList.add(aclDomainMaster);
				aclClassListList.add(aclClassRepository.findById(aclDomainMaster.getAclDomainRefId()).get());
			}
			// Now delete in batch mode
			aclClassRepository.deleteInBatch(aclClassListList);
			domainRepository.deleteInBatch(aclDomainMasterList);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> deleteMultipleRecords ==> Ended");

		return isDeleted;
	}

	@Override
	public List<NameValue> getDomainList(Integer id) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> getDomainList ==> Started");

		List<NameValue> moduleList = new ArrayList<NameValue>();
		NameValue nameValue = null;
		try {
			List<AclDomainMaster> aclDomainMasterList = moduleRepository.getOne(id).getAclDomainMasters();
			for (AclDomainMaster aclDomainMaster : aclDomainMasterList) {
				nameValue = new NameValue(aclDomainMaster.getId(), aclDomainMaster.getName());
				moduleList.add(nameValue);
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> getDomainList ==> Ended");
		return moduleList;
	}

	
	
	

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("moduleNameId")&&!fieldName.equals("domainName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {				
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.domainRepository.existsByModuleIdAndDomainName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString()); 
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.domainRepository.existsByModuleIdAndDomainNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> FirstChildValueWithParentIdExists ==> Ended");
		return recordFound;
	}

	@Override
	public boolean SecondChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     DomainServiceImpl :==> SecondChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("moduleNameId")&&!fieldName.equals("packageName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.domainRepository.existsByModuleIdAndDomainPkgName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.domainRepository.existsByModuleIdAndDomainPkgNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainServiceImpl :==> SecondChildValueWithParentIdExists ==> Started");
		return recordFound;
	}

	@Override
	public boolean ThirdChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}
}
