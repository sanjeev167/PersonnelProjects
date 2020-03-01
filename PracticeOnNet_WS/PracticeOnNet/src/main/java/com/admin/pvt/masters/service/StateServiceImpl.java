/**
 * 
 */
package com.admin.pvt.masters.service;

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
import com.admin.pvt.masters.dto.StateMasterDTO;
import com.admin.pvt.masters.entity.StateMaster;
import com.admin.pvt.masters.repo.CountryRepository;
import com.admin.pvt.masters.repo.StateRepository;
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
public class StateServiceImpl implements StateService{


	private static final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName= AppConstants.JPA_UNIT_PRACTICEONNET)
	private EntityManager entityManager;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CountryRepository countryRepository;
	// Using constructor mapping
	@Override
	public DataTableResults<StateMasterDTO> loadGrid(HttpServletRequest request,String whereClauseForBaseQuery) throws CustomRuntimeException{
		log.info("     StateServiceImpl :==> loadGrid :==> Started");
		DataTableResults<StateMasterDTO> dataTableResult;
		try {
			DataTableRequest<StateMaster> dataTableInRQ = new DataTableRequest<StateMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;
			if(whereClauseForBaseQuery.equals(""))
				baseQuery = "SELECT sm.id as id, cm.name as countryName, sm.name as stateName ,  (SELECT COUNT(1) FROM state_master sm, country_master cm where sm.country_id=cm.id "
						+") AS totalrecords  FROM state_master sm , country_master cm "+ 
						"where sm.country_id=cm.id ";
			else
				baseQuery = "SELECT sm.id as id, cm.name as countryName, sm.name as stateName ,  (SELECT COUNT(1) FROM state_master sm, country_master cm where sm.country_id=cm.id and  "
						+ whereClauseForBaseQuery+") AS totalrecords  FROM state_master sm , country_master cm "+ 
						"where sm.country_id=cm.id and "+whereClauseForBaseQuery;


			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);		
			Query query = entityManager.createNativeQuery(paginatedQuery, "StateMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<StateMasterDTO> StateMasterList = query.getResultList();		

			dataTableResult = new DataTableResults<StateMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(StateMasterList);
			if (!AppUtil.isObjectEmpty(StateMasterList)) {
				dataTableResult.setRecordsTotal(StateMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(StateMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(StateMasterList.size()));
				}
			}
		}catch(Exception ex) {throw ExceptionApplicationUtility.wrapRunTimeException(ex);}
		log.info("     StateServiceImpl :==> loadGrid :==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public StateMasterDTO getReordById(Integer id) throws CustomRuntimeException{
		log.info("     StateServiceImpl :==> getReordById :==> Started");
		StateMasterDTO stateMasterDTO;
		try {
			StateMaster stateMaster =stateRepository.getOne(id);
			stateMasterDTO=new StateMasterDTO();
			stateMasterDTO.setId(stateMaster.getId());
			stateMasterDTO.setStateName(stateMaster.getName());
			stateMasterDTO.setCountryId(stateMaster.getCountryMaster().getId()+"");
		}catch(Exception ex) {throw ExceptionApplicationUtility.wrapRunTimeException(ex);}
		log.info("     StateServiceImpl :==> getReordById :==> Ended");
		return stateMasterDTO;
	}

	@Override
	public StateMasterDTO saveAndUpdate(StateMasterDTO stateMasterDTO) throws CustomRuntimeException{
		StateMaster stateMaster;
		log.info("     StateServiceImpl :==> saveAndUpdate :==> Started");
		StateMasterDTO stateMasterDTONew;
		try {
			if(stateMasterDTO.getId()!=null)
				stateMaster =stateRepository.getOne(stateMasterDTO.getId());
			else
				stateMaster =new StateMaster();

			stateMaster.setId(stateMasterDTO.getId());
			stateMaster.setName(stateMasterDTO.getStateName()); 		
			stateMaster.setCountryMaster(countryRepository.getOne(Integer.parseInt(stateMasterDTO.getCountryId()))); 

			StateMaster returnedStateMaster = stateRepository.saveAndFlush(stateMaster);

			stateMasterDTONew=new StateMasterDTO(returnedStateMaster.getId(), 
					returnedStateMaster.getCountryMaster().getName(), 
					returnedStateMaster.getCountryMaster().getId(), 
					returnedStateMaster.getName());
		}catch(Exception ex) {throw ExceptionApplicationUtility.wrapRunTimeException(ex);}
		log.info("     StateServiceImpl :==> saveAndUpdate :==> Ended");
		return stateMasterDTONew;
	}

	@Override
	public boolean deleteOneRecord(Integer id)throws CustomRuntimeException {
		log.info("     StateServiceImpl :==> deleteOneRecord :==> Started");
		boolean isDeleted=true;
		try {
			stateRepository.deleteById(id);
		}catch(Exception ex) {
			isDeleted=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
		log.info("     StateServiceImpl :==> deleteOneRecord :==> Ended");
		return isDeleted;
	}

	@Transactional
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException{
		log.info("     StateServiceImpl :==> deleteMultipleRecords :==> Started");
		boolean isDeleted=true;
		try {
			stateRepository.deleteStateWithIds(recordIdArray);
		}catch(Exception ex) {
			isDeleted=false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
		log.info("     StateServiceImpl :==> deleteMultipleRecords :==> Ended");
		return isDeleted;
	}

	@Override
	public List<NameValue> getStateList(Integer id)throws CustomRuntimeException {
		List<NameValue> stateList=new ArrayList<NameValue>();		
		NameValue nameValue;		
		log.info("     StateServiceImpl :==> getStateList :==> Started");
		try {
			List<StateMaster> stateMasterList=countryRepository.getOne(id).getStateMasters();		
			for(StateMaster stateMaster:stateMasterList) {
				nameValue=new NameValue(stateMaster.getId(),stateMaster.getName());
				stateList.add(nameValue);
			}		
		}catch(Exception ex) {throw ExceptionApplicationUtility.wrapRunTimeException(ex);}
		log.info("     StateServiceImpl :==> getStateList :==> Ended");
		return stateList;
	}


	
	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId,Object fieldValue, String fieldName,Object idValue, String idFieldName)
			throws CustomRuntimeException {

		boolean recordFound=false;
		log.info("     StateServiceImpl :==> FieldValueWithParentIdAndChildExists :==> Started");
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("countryId")&&!fieldName.equals("stateName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one				
				recordFound=this.stateRepository.existsByCountryIdAndStateName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    				
			}			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.stateRepository.existsByCountryIdAndStateNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				
			}
			
		}catch(Exception ex) {throw ExceptionApplicationUtility.wrapRunTimeException(ex);}
		log.info("     StateServiceImpl :==> FieldValueWithParentIdAndChildExists :==> Ended");
		return recordFound;
	}

	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
			String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean SecondChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ThirdChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
			String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

}



