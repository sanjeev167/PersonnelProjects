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

import com.admin.pvt.sec.acl_monitor.dto.AclSidDTO;
import com.admin.pvt.sec.acl_monitor.entity.AclClass;
import com.admin.pvt.sec.acl_monitor.repo.AclSidRepository;
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
public class AclSidServiceImpl implements AclSidService{
	
	static final Logger log = LoggerFactory.getLogger(AclSidServiceImpl.class);
	/** The entity manager. */
	@Autowired
   @PersistenceContext(unitName= AppConstants.JPA_UNIT_ACL)
	private EntityManager entityManager;

	@Autowired
	AclSidRepository aclSidRepository;

	// Using constructor mapping
	@Override
	public DataTableResults<AclSidDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException {
		log.info("     AclSidServiceImpl :==> loadGrid ==> Started");
		
		DataTableResults<AclSidDTO> dataTableResult=null;
		try {
		DataTableRequest<AclClass> dataTableInRQ = new DataTableRequest<AclClass>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		
		String baseQuery = "SELECT id,principal,sid,"
				+ " (SELECT COUNT(1) FROM acl_sid "+whereClauseForBaseQuery+") AS totalrecords  FROM acl_sid "+whereClauseForBaseQuery;
		
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, "AclSidDTOMapping");
		@SuppressWarnings("unchecked")
		List<AclSidDTO> aclSidList = query.getResultList();
		dataTableResult = new DataTableResults<AclSidDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(aclSidList);
		if (!AppUtil.isObjectEmpty(aclSidList)) {
			dataTableResult.setRecordsTotal(aclSidList.get(0).getTotalrecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(aclSidList.get(0).getTotalrecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(aclSidList.size()));
			}
		}
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     AclSidServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	

}