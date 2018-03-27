package com.bcldb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import producteligibilityservice.ErrorMessage;
import static org.mockito.Mockito.*;

import com.bcldb.service.ejb.ProductEligibilityServiceBean;
import com.bcldb.service.model.ProductCustomerEligibilityEnt;
import com.bcldb.services.eligibilityservice.GetEligibilityByCustomerRequest;
import com.bcldb.services.eligibilityservice.GetEligibilityByCustomerResponse;
import com.bcldb.services.eligibilityservice.GetEligibilityByEffectiveDateRequest;
import com.bcldb.services.eligibilityservice.GetEligibilityByEffectiveDateResponse;
import com.bcldb.services.eligibilityservice.GetEligibilityBySKURequest;
import com.bcldb.services.eligibilityservice.GetEligibilityBySKUResponse;
import com.bcldb.utils.CommonHelper;

public class ProductEligibilityServiceImplTest {

	ProductEligibilityServiceImpl soap = new ProductEligibilityServiceImpl();
	
	@Before
	public void setup(){
		soap.service = mock(ProductEligibilityServiceBean.class);
	}
	
	@Test
	public void getEligibilityByCustomer() throws ErrorMessage {
		
		// given
		GetEligibilityByCustomerRequest part = new GetEligibilityByCustomerRequest();
		part.getCustomerType().add("CON");
		
		List<ProductCustomerEligibilityEnt> entities = new ArrayList<ProductCustomerEligibilityEnt>();
		entities.add(new ProductCustomerEligibilityEnt("CON", "YES", "NO", 18, new Date()));
		entities.add(new ProductCustomerEligibilityEnt("CON", "YES", "YES", 67, new Date()));
		
		// when
		when(soap.service.getEligibilityByCustomer("CON")).thenReturn(entities);		
		
		// then
		GetEligibilityByCustomerResponse response = soap.getEligibilityByCustomer(part);		
		assertEquals(2, response.getCustomerEligibilities().get(0).getEligibility().size());
		
	}

	@Test
	public void getEligibilityByEffectiveDate() throws Exception {
		
		// given		
		GetEligibilityByEffectiveDateRequest part = new GetEligibilityByEffectiveDateRequest();
		
		XMLGregorianCalendar xmlDate = CommonHelper.date2Gregorian(new Date());
		part.setEffectiveDate(xmlDate);
		Date effDate = CommonHelper.gregorian2Date(part.getEffectiveDate());
		
		List<ProductCustomerEligibilityEnt> entities = new ArrayList<ProductCustomerEligibilityEnt>();
		entities.add(new ProductCustomerEligibilityEnt("CON", "YES", "NO", 18, effDate));
		entities.add(new ProductCustomerEligibilityEnt("RAS", "YES", "YES", 67, effDate));
		
		// when
		when(soap.service.getEligibilityByEffectiveDate(effDate)).thenReturn(entities);
		
		// then
		GetEligibilityByEffectiveDateResponse response = soap.getEligibilityByEffectiveDate(part);
		
		assertEquals(2, response.getCustomerEligibilities().size());
	}

	@Test
	public void getEligibilityBySKU() throws ErrorMessage {
		// given
		GetEligibilityBySKURequest part = new GetEligibilityBySKURequest();
		part.getSKU().addAll(Arrays.asList("18","67"));
		
		List<ProductCustomerEligibilityEnt> entity_18 = new ArrayList<ProductCustomerEligibilityEnt>();
		entity_18.add(new ProductCustomerEligibilityEnt("CON", "YES", "NO", 18, new Date()));
		
		List<ProductCustomerEligibilityEnt> entity_67 = new ArrayList<ProductCustomerEligibilityEnt>();
		entity_67.add(new ProductCustomerEligibilityEnt("RAS", "YES", "YES", 67, new Date()));
		
		// when
		when(soap.service.getEligibilityBySKU(18)).thenReturn(entity_18);
		when(soap.service.getEligibilityBySKU(67)).thenReturn(entity_67);
		
		// then
		GetEligibilityBySKUResponse response = soap.getEligibilityBySKU(part);		
		assertEquals(2, response.getSKUEligibilities().size());
	}

}
