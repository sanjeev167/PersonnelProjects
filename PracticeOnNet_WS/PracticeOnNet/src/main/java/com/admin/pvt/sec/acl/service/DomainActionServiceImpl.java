/**
 * 
 */
package com.admin.pvt.sec.acl.service;

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

import com.admin.pvt.sec.acl.dto.DomainActionMasterDTO;
import com.admin.pvt.sec.acl.entity.AclDomainActionMaster;
import com.admin.pvt.sec.acl.repo.DomainActionRepository;
import com.admin.pvt.sec.acl.repo.DomainRepository;
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
public class DomainActionServiceImpl implements DomainActionService {

	static final Logger log = LoggerFactory.getLogger(DomainActionServiceImpl.class);
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
	public DataTableResults<DomainActionMasterDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> loadGrid :==> Started");

		DataTableResults<DomainActionMasterDTO> dataTableResult = null;
		try {
			DataTableRequest<AclDomainActionMaster> dataTableInRQ = new DataTableRequest<AclDomainActionMaster>(
					request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select adam.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " adm.name as domainName," + " adm.package_name as packageName," + " adam.name as actionName,"
						+ " adam.sort_name as sortName," + " adam.action_number as actionNumber, "
						+ " (SELECT COUNT(1) FROM acl_domain_master adm, module_master mm , department_master dm, acl_domain_action_master adam "
						+ " where adam.acl_domain_master_id=adm.id  and adm.module_id=mm.id and mm.department_id=dm.id  "
						+ " ) AS totalrecords"
						+ " from acl_domain_master adm, module_master mm , department_master dm, acl_domain_action_master adam "
						+ " where adam.acl_domain_master_id=adm.id  and adm.module_id=mm.id and mm.department_id=dm.id ";

			else
				baseQuery = "select adam.id as ID," + " dm.name as departmentName," + " mm.name as moduleName,"
						+ " adm.name as domainName," + " adm.package_name as packageName," + " adam.name as actionName,"
						+ " adam.sort_name as sortName," + " adam.action_number as actionNumber, "
						+ " (SELECT COUNT(1) FROM acl_domain_master adm, module_master mm , department_master dm, acl_domain_action_master adam "
						+ " where adam.acl_domain_master_id=adm.id  and adm.module_id=mm.id and mm.department_id=dm.id and "
						+ whereClauseForBaseQuery + " ) AS totalrecords"
						+ " from acl_domain_master adm, module_master mm , department_master dm, acl_domain_action_master adam "
						+ " where adam.acl_domain_master_id=adm.id  and adm.module_id=mm.id and mm.department_id=dm.id  and "
						+ whereClauseForBaseQuery;

			// System.out.println("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			// System.out.println("paginatedQuery ="+paginatedQuery);
			Query query = entityManager.createNativeQuery(paginatedQuery, "DomainActionMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<DomainActionMasterDTO> opMasterList = query.getResultList();

			dataTableResult = new DataTableResults<DomainActionMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(opMasterList);
			if (!AppUtil.isObjectEmpty(opMasterList)) {
				dataTableResult.setRecordsTotal(opMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(opMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(opMasterList.size()));
				}
			}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> loadGrid :==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public DomainActionMasterDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> getReordById :==> Started");
		DomainActionMasterDTO domainActionMasterDTO = null;
		try {
			AclDomainActionMaster aclDomainActionMaster = domainActionRepository.getOne(id);
			domainActionMasterDTO = new DomainActionMasterDTO();
			domainActionMasterDTO.setId(aclDomainActionMaster.getId());
			domainActionMasterDTO.setDepartmentNameId(
					aclDomainActionMaster.getAclDomainMaster().getModuleMaster().getDepartmentMaster().getId() + "");

			domainActionMasterDTO
					.setModuleNameId(aclDomainActionMaster.getAclDomainMaster().getModuleMaster().getId() + "");

			domainActionMasterDTO.setDomainNameId(aclDomainActionMaster.getAclDomainMaster().getId() + "");
			domainActionMasterDTO.setPackageName(aclDomainActionMaster.getAclDomainMaster().getPackageName());
			domainActionMasterDTO.setActionName(aclDomainActionMaster.getName());
			domainActionMasterDTO.setSortName(aclDomainActionMaster.getSortName());
			domainActionMasterDTO.setActionNumber(aclDomainActionMaster.getActionNumber());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> getReordById :==> Ended");
		return domainActionMasterDTO;
	}

	@Override
	public DomainActionMasterDTO saveAndUpdate(DomainActionMasterDTO domainActionMasterDTO) throws CustomRuntimeException {

		log.info("     DomainActionServiceImpl :==> saveAndUpdate :==> Started");
		AclDomainActionMaster aclDomainActionMaster = null;
		DomainActionMasterDTO domainActionMasterDTONew = null;
		try {
			if (domainActionMasterDTO.getId() != null)
				aclDomainActionMaster = domainActionRepository.getOne(domainActionMasterDTO.getId());
			else
				aclDomainActionMaster = new AclDomainActionMaster();

			aclDomainActionMaster.setId(domainActionMasterDTO.getId());
			aclDomainActionMaster.setName(domainActionMasterDTO.getActionName());
			aclDomainActionMaster.setSortName(domainActionMasterDTO.getSortName());
			aclDomainActionMaster.setActionNumber(domainActionMasterDTO.getActionNumber());

			aclDomainActionMaster.setAclDomainMaster(
					domainRepository.getOne(Integer.parseInt(domainActionMasterDTO.getDomainNameId())));

			AclDomainActionMaster returnedAclDomainActionMaster = domainActionRepository
					.saveAndFlush(aclDomainActionMaster);

			domainActionMasterDTONew = new DomainActionMasterDTO(

					returnedAclDomainActionMaster.getId(),

					returnedAclDomainActionMaster.getAclDomainMaster().getModuleMaster().getDepartmentMaster().getId()
							+ "",

					returnedAclDomainActionMaster.getAclDomainMaster().getModuleMaster().getId() + "",

					returnedAclDomainActionMaster.getAclDomainMaster().getId() + "",
					returnedAclDomainActionMaster.getAclDomainMaster().getPackageName(),

					returnedAclDomainActionMaster.getName(), returnedAclDomainActionMaster.getSortName(),
					returnedAclDomainActionMaster.getActionNumber());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> saveAndUpdate :==> Ended");
		return domainActionMasterDTONew;
	}

	@Override
	public boolean deleteRecordById(Integer id) throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> deleteRecordById :==> Started");
		boolean isDeleted = true;
		try {
			domainActionRepository.deleteById(id);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> deleteRecordById :==> Ended");
		return isDeleted;
	}

	@Transactional
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> deleteMultipleRecords :==> Started");
		boolean isDeleted = true;
		try {
			domainActionRepository.deleteOperationWithIds(recordIdArray);
		} catch (Exception ex) {
			isDeleted = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> deleteMultipleRecords :==> Ended");
		return isDeleted;
	}

	

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> FirstChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("domainNameId")&&!fieldName.equals("actionName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {				
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.domainActionRepository.existsByDomainIdAndActionName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString()); 
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.domainActionRepository.existsByDomainIdAndActionNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> FirstChildValueWithParentIdExists ==> Ended");
		return recordFound;
	}

	@Override
	public boolean SecondChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> SecondChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("domainNameId")&&!fieldName.equals("sortName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {				
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.domainActionRepository.existsByDomainIdAndSortName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString()); 
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.domainActionRepository.existsByDomainIdAndSortNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> SecondChildValueWithParentIdExists ==> Ended");
		return recordFound;
	}

	@Override
	public boolean ThirdChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		log.info("     DomainActionServiceImpl :==> ThirdChildValueWithParentIdExists ==> Started");
		boolean recordFound = false;
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("domainNameId")&&!fieldName.equals("actionNumber") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {				
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.domainActionRepository.existsByDomainIdAndActionNumber(Integer.parseInt(parentIdValue+""),
						Integer.parseInt(fieldValue.toString())); 
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.domainActionRepository.existsByDomainIdAndActionNumberExceptThisId(Integer.parseInt(parentIdValue+""),
						Integer.parseInt(fieldValue.toString()),Integer.parseInt(idValue.toString()));  
			}
			
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     DomainActionServiceImpl :==> ThirdChildValueWithParentIdExists ==> Ended");
		return recordFound;
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
