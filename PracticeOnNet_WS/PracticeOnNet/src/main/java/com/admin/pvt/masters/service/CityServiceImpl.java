/**
 * 
 */
package com.admin.pvt.masters.service;

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

import com.admin.pvt.masters.dto.CityMasterDTO;
import com.admin.pvt.masters.entity.CityMaster;
import com.admin.pvt.masters.repo.CityRepository;
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
public class CityServiceImpl implements CityService {

	static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

	/** The entity manager. */
	@Autowired
	@PersistenceContext(unitName = AppConstants.JPA_UNIT_PRACTICEONNET)
	private EntityManager entityManager;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CityRepository cityRepository;

	@Override
	public DataTableResults<CityMasterDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
			throws CustomRuntimeException {
		log.info("     CityServiceImpl :==> loadGrid ==> Started");
		DataTableResults<CityMasterDTO> dataTableResult;
		try {
			DataTableRequest<CityMaster> dataTableInRQ = new DataTableRequest<CityMaster>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			String baseQuery;

			if (whereClauseForBaseQuery.equals(""))
				baseQuery = "select ctm.id as ID," + " cm.name as countryName," + " sm.name as stateName,"
						+ " ctm.name as cityName," + " (SELECT COUNT(1) FROM city_master ) AS totalrecords"
						+ " from city_master ctm, state_master sm , country_master cm"
						+ " where ctm.state_id=sm.id and sm.country_id=cm.id ";
			else
				baseQuery = "select ctm.id as ID," + " cm.name as countryName," + " sm.name as stateName,"
						+ " ctm.name as cityName,"
						+ " (SELECT COUNT(1) FROM city_master ctm, state_master sm , country_master cm where ctm.state_id=sm.id and sm.country_id=cm.id and "
						+ whereClauseForBaseQuery + ") AS totalrecords"
						+ " from city_master ctm, state_master sm , country_master cm"
						+ " where ctm.state_id=sm.id and sm.country_id=cm.id and " + whereClauseForBaseQuery;

			// System.out.println("baseQuery ="+baseQuery);
			String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
			Query query = entityManager.createNativeQuery(paginatedQuery, "CityMasterDTOMapping");

			@SuppressWarnings("unchecked")
			List<CityMasterDTO> cityMasterList = query.getResultList();

			dataTableResult = new DataTableResults<CityMasterDTO>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(cityMasterList);
			if (!AppUtil.isObjectEmpty(cityMasterList)) {
				dataTableResult.setRecordsTotal(cityMasterList.get(0).getTotalrecords().toString());
				if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
					dataTableResult.setRecordsFiltered(cityMasterList.get(0).getTotalrecords().toString());
				} else {
					dataTableResult.setRecordsFiltered(Integer.toString(cityMasterList.size()));
				}
			}

		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     CityServiceImpl :==> loadGrid ==> Ended");
		return dataTableResult;
	}

	// This will directly put your result into your mapped dto
	@Override
	public CityMasterDTO getReordById(Integer id) throws CustomRuntimeException {
		log.info("     CityServiceImpl :==> getReordById ==> Started");
		CityMasterDTO cityMasterDTO;
		try {
			CityMaster cityMaster = cityRepository.getOne(id);
			cityMasterDTO = new CityMasterDTO();
			cityMasterDTO.setId(cityMaster.getId());
			cityMasterDTO.setCountryNameId(cityMaster.getStateMaster().getCountryMaster().getId() + "");
			cityMasterDTO.setStateNameId(cityMaster.getStateMaster().getId() + "");
			cityMasterDTO.setCityName(cityMaster.getName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     CityServiceImpl :==> getReordById ==> Ended");
		return cityMasterDTO;
	}

	@Override
	public CityMasterDTO saveAndUpdate(CityMasterDTO cityMasterDTO) throws CustomRuntimeException {
		log.info("     CityServiceImpl :==> saveAndUpdate ==> Started");
		CityMaster cityMaster;
		CityMasterDTO cityMasterDTONew;
		try {			
			if (cityMasterDTO.getId() != null) {//Edit case				
				cityMaster = cityRepository.getOne(cityMasterDTO.getId());				
			}
			else {	//Add case				
				cityMaster = new CityMaster();				
			}
			cityMaster.setName(cityMasterDTO.getCityName());			
			cityMaster.setStateMaster(stateRepository.getOne(Integer.parseInt(cityMasterDTO.getStateNameId())));
			
			CityMaster returnedCityMaster = cityRepository.saveAndFlush(cityMaster);

			cityMasterDTONew = new CityMasterDTO(returnedCityMaster.getId(),
					returnedCityMaster.getStateMaster().getCountryMaster().getId() + "",
					returnedCityMaster.getStateMaster().getId() + "", returnedCityMaster.getName());
		} catch (Exception ex) {
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     CityServiceImpl :==> saveAndUpdate ==> Ended");
		return cityMasterDTONew;
	}

	@Override
	public boolean deleteOneRecord(Integer id) throws CustomRuntimeException {
		log.info("     CityServiceImpl :==> deleteOneRecord ==> Started");
		boolean isRecordDelete = true;
		try {
			cityRepository.deleteById(id);
		} catch (Exception ex) {
			isRecordDelete = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     CityServiceImpl :==> deleteOneRecord ==> Ended");
		return isRecordDelete;
	}

	@Transactional
	@Override
	public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
		log.info("     CityServiceImpl :==> deleteMultipleRecords ==> Started");
		boolean isRecordDelete = true;
		try {
			cityRepository.deleteCityWithIds(recordIdArray);
		} catch (Exception ex) {
			isRecordDelete = false;
			throw ExceptionApplicationUtility.wrapRunTimeException(ex);
		}
		log.info("     CityServiceImpl :==> deleteMultipleRecords ==> Started");
		return isRecordDelete;
	}

	
	@Override
	public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId,Object fieldValue, String fieldName,Object idValue, String idFieldName)
			throws CustomRuntimeException {

		boolean recordFound=false;
		log.info("     CityServiceImpl :==> FieldValueWithParentIdAndChildExists :==> Started");
		try {
			Assert.notNull(parentId);Assert.notNull(fieldName);

			if (!parentId.equals("stateId")&&!fieldName.equals("cityName") && !idFieldName.equals("id")) {
				throw ExceptionApplicationUtility.wrapRunTimeException(new UnsupportedOperationException("Field name not supported"));            
			}

			if (parentIdValue == null && fieldValue==null) {
				return false;
			}

			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue==null) { 
				//Case of adding new one
				System.out.println("Add CCCCC  "+parentIdValue);
				recordFound=this.cityRepository.existsByStateIdAndCityName(Integer.parseInt(parentIdValue+""),
						fieldValue.toString());    
				System.out.println("Add BBBBBBBB  "+recordFound);
			}
			
			if(!parentIdValue.equals("")&&!fieldValue.equals("")&& idValue!=null) { 
				//Case of editing existing one
				recordFound=this.cityRepository.existsByStateIdAndCityNameExceptThisId(Integer.parseInt(parentIdValue+""),
						fieldValue.toString(),Integer.parseInt(idValue.toString()));  
				System.out.println("Edit BBBBBBBB  "+recordFound);
			}
			
		}catch(Exception ex) {throw ExceptionApplicationUtility.wrapRunTimeException(ex);}
		log.info("     CityServiceImpl :==> FieldValueWithParentIdAndChildExists :==> Ended");
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
