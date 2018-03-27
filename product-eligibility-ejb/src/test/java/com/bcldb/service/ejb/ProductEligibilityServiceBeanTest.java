package com.bcldb.service.ejb;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bcldb.service.model.ProductCustomerEligibilityEnt;

@RunWith(Arquillian.class)
public class ProductEligibilityServiceBeanTest {

	private static final Date effdate = new Date();


	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "eligibility-test.jar")
				.addClass(ProductEligibilityServiceBean.class)
				.addPackage(ProductCustomerEligibilityEnt.class.getPackage())
				.addAsManifestResource("test-persistence.xml", "persistence.xml")
				.addAsManifestResource("jbossas-ds.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		System.out.println(jar.toString());
		return jar;
	}
	
	@Inject
	ProductEligibilityServiceBean service;
	
	@PersistenceContext
	EntityManager em;
	
	@Inject
	UserTransaction utx;
	
	
	@Before
	public void setup() throws Exception{
		clearData();
		insertData();
		startTransaction();
	}
	
	@After
	public void cleanup() throws Exception{
		utx.commit();
	}
	
	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records ...");
		em.createQuery("delete from ProductCustomerEligibilityEnt w").executeUpdate();
		
		utx.commit();		
	}
	
	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Inserting customer record ...");
		ProductCustomerEligibilityEnt elig = new ProductCustomerEligibilityEnt("CON", "YES", "NO", 18, effdate);
		
		em.persist(elig);
		
		//commit and clear transaction
		utx.commit();
		em.clear();
	
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
		
	}	
	
	@Test
	public void getEligibilityByCustomer() {
		List<ProductCustomerEligibilityEnt> list = service.getEligibilityByCustomer("CON");
		assertEquals("CON", list.get(0).getCustomerType());		
	}

	@Test
	public void getEligibilityByEffectiveDate() {
		List<ProductCustomerEligibilityEnt> list = service.getEligibilityByEffectiveDate(effdate);
		assertEquals("CON", list.get(0).getCustomerType());		

	}

	@Test
	public void getEligibilityBySKU() {
		List<ProductCustomerEligibilityEnt> list = service.getEligibilityBySKU(18);
		assertEquals("CON", list.get(0).getCustomerType());	

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldFindAllSKUEligibilitiesUsingJpqlQuery(){
        // given
		String namedQuery = ProductCustomerEligibilityEnt.FIND_BY_SKU;

        // when
        System.out.println("Selecting (using JPQL)...");
        Query qry = em.createNamedQuery(namedQuery);
        qry.setParameter("sku", 18);
        
		List<ProductCustomerEligibilityEnt> eligs = qry.getResultList();

        // then
        assertNotNull(eligs);
        assertEquals(1, eligs.size());
        
        System.out.println("Found " + eligs.size() + " product customer eligibilities (using JPQL):");
        
    }
	
	
}
