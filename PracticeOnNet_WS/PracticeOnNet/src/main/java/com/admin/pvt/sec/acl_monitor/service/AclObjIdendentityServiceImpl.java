/**
 * 
 */
package com.admin.pvt.sec.acl_monitor.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.pvt.sec.acl_monitor.dto.AclObjectDTO;
import com.admin.pvt.sec.acl_monitor.entity.AclObjectIdentity;
import com.admin.pvt.sec.acl_monitor.repo.AclObjIdendentityRepository;
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
public class AclObjIdendentityServiceImpl implements AclObjIdendentityService{
	
	static final Logger log = LoggerFactory.getLogger(AclObjIdendentityServiceImpl.class);
	/** The entity manager. */
	@Autowired
   @PersistenceContext(unitName= AppConstants.JPA_UNIT_ACL)
	private EntityManager entityManager;

	@Autowired
	AclObjIdendentityRepository aclObjIdendentityRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<AclObjectDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException {
		log.info("     AclObjIdendentityServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<AclObjectDTO> dataTableResult=null;
		try {
		DataTableRequest<AclObjectIdentity> dataTableInRQ = new DataTableRequest<AclObjectIdentity>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		
		String baseQuery = "SELECT id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting, "
				+ " (SELECT COUNT(1) FROM acl_object_identity "+whereClauseForBaseQuery+") AS totalrecords  FROM acl_object_identity "+whereClauseForBaseQuery;
		
		
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		//System.out.println("paginatedQuery = "+paginatedQuery);
		Query query = entityManager.createNativeQuery(paginatedQuery, "AclObjectDTOMapping");
		@SuppressWarnings("unchecked")
		List<AclObjectDTO> aclObjectList = query.getResultList();
		dataTableResult = new DataTableResults<AclObjectDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(aclObjectList);
		if (!AppUtil.isObjectEmpty(aclObjectList)) {
			dataTableResult.setRecordsTotal(aclObjectList.get(0).getTotalrecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(aclObjectList.get(0).getTotalrecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(aclObjectList.size()));
			}
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AclObjIdendentityServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	

}