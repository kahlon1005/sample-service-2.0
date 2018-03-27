package com.bcldb.service.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.bcldb.service.model.ProductCustomerEligibilityEnt;

@Stateless
public class ProductEligibilityServiceBean {
	
	private static final Logger logger = Logger.getLogger(ProductEligibilityServiceBean.class);
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * find eligibility by sku
	 * 
	 * @param sku
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCustomerEligibilityEnt> getEligibilityBySKU(int sku) {
		logger.info("get eligibity by sku ...");
		Query q = em.createNamedQuery(ProductCustomerEligibilityEnt.FIND_BY_SKU);
		q.setParameter("sku", sku);
		
		List<ProductCustomerEligibilityEnt> el = q.getResultList();
		return el;
	}
	
	/**
	 * find eligibility by customer
	 * 
	 * @param custType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCustomerEligibilityEnt> getEligibilityByCustomer(String custType) {
		logger.info("get eligibity by customer ...");
		Query q = em.createNamedQuery(ProductCustomerEligibilityEnt.FIND_BY_CTYPE);
		q.setParameter("custType", custType);
		
		List<ProductCustomerEligibilityEnt> el = q.getResultList();
		return el;
	}
	
	/**
	 * find eligibility by effective date
	 *  
	 * @param effDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductCustomerEligibilityEnt> getEligibilityByEffectiveDate(Date effDate) {
		logger.info("get eligibity by effective date ...");
		Query q = em.createNamedQuery(ProductCustomerEligibilityEnt.FIND_BY_EFFECTIVE_DATE);
		q.setParameter("effDate", effDate);
		
		List<ProductCustomerEligibilityEnt> el = q.getResultList();
		return el;
	}
	
}
