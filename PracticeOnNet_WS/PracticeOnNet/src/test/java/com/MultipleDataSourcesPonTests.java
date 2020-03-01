/**
 * 
 */
package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.admin.pvt.masters.entity.CountryMaster;
import com.admin.pvt.masters.repo.CountryRepository;

/**
 * @author Sanjeev
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleDataSourcesPonTests {
	@Autowired
    private CountryRepository countryRepository;
    
    @Test
   // @Transactional("productTransactionManager")
    public void create_check_product() {
        assertNotNull(countryRepository.findById(20));
        System.out.println(countryRepository.findById(20).get().getName() );
       assertEquals(countryRepository.findById(20).get().getName() ,"Belarus");
        
    }

}
