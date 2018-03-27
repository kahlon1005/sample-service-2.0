package com.bcldb.utils;

import java.util.List;

import com.bcldb.service.model.ProductCustomerEligibilityEnt;
import com.bcldb.services.common.YesNoEnumType;
import com.bcldb.services.eligibilityservice.CustomerEligibilityDetailType;
import com.bcldb.services.eligibilityservice.CustomerEligibilityType;
import com.bcldb.services.eligibilityservice.SKUEligibilityDetailType;
import com.bcldb.services.eligibilityservice.SKUEligibilityType;

public final class DTOConverter {
	
	private static final String VALUE_YES = "Y";

	public SKUEligibilityType convertSKUEligibilityType(List<ProductCustomerEligibilityEnt> el) throws Exception {
		SKUEligibilityType eligibilityType = new SKUEligibilityType();		
		for(ProductCustomerEligibilityEnt eligibility : el){
			eligibilityType.setSKU(eligibility.getProductId().toString());			
			CustomerEligibilityDetailType custElgiDtlTyp = new CustomerEligibilityDetailType();
			custElgiDtlTyp.setCustomerType(eligibility.getCustomerType());
			
			custElgiDtlTyp.setEffectiveDate(CommonHelper.date2Gregorian(eligibility.getEffectiveDate()));			
			custElgiDtlTyp.setSellingUnitOrder(VALUE_YES.equals(eligibility.getOrderSu()) ? YesNoEnumType.YES : YesNoEnumType.NO);		
			custElgiDtlTyp.setCaseOrder(VALUE_YES.equals(eligibility.getOrderCase()) ? YesNoEnumType.YES : YesNoEnumType.NO);

			eligibilityType.getEligibility().add(custElgiDtlTyp);
		}		
		return eligibilityType;
		
	}

	public CustomerEligibilityType convertCustomerEligibityType(List<ProductCustomerEligibilityEnt> el)  throws Exception {
		CustomerEligibilityType eligibilityType = new CustomerEligibilityType();
		for(ProductCustomerEligibilityEnt eligibility : el){
			eligibilityType.setCustomerType(eligibility.getCustomerType());			
			SKUEligibilityDetailType eligibilityDetailType = new SKUEligibilityDetailType();
			eligibilityDetailType.setSKU(eligibility.getProductId().toString());
			
			eligibilityDetailType.setEffectiveDate(CommonHelper.date2Gregorian(eligibility.getEffectiveDate()));			
			eligibilityDetailType.setSellingUnitOrder(VALUE_YES.equals(eligibility.getOrderSu()) ? YesNoEnumType.YES : YesNoEnumType.NO);
			eligibilityDetailType.setCaseOrder(VALUE_YES.equals(eligibility.getOrderCase()) ? YesNoEnumType.YES : YesNoEnumType.NO);
			
			eligibilityType.getEligibility().add(eligibilityDetailType);
		}
		return eligibilityType;
	}

	public CustomerEligibilityType convertEligibilityByEffectiveDate(ProductCustomerEligibilityEnt eligibility)  throws Exception {
		CustomerEligibilityType eligibilityType = new CustomerEligibilityType();
		
		eligibilityType.setCustomerType(eligibility.getCustomerType());
		
		SKUEligibilityDetailType eligibilityDetailType = new SKUEligibilityDetailType();		
		eligibilityDetailType.setSKU(eligibility.getProductId().toString());
		
		eligibilityDetailType.setEffectiveDate(CommonHelper.date2Gregorian(eligibility.getEffectiveDate()));			
		eligibilityDetailType.setSellingUnitOrder(VALUE_YES.equals(eligibility.getOrderSu()) ? YesNoEnumType.YES : YesNoEnumType.NO);		
		eligibilityDetailType.setCaseOrder(VALUE_YES.equals(eligibility.getOrderCase()) ? YesNoEnumType.YES : YesNoEnumType.NO);
		
		eligibilityType.getEligibility().add(eligibilityDetailType);
		
		return eligibilityType;
	}

}
