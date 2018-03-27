package com.bcldb.utils;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import producteligibilityservice.ErrorMessage;

import com.bcldb.exceptions.ValidationException;
import com.bcldb.services.eligibilityservice.GetEligibilityByEffectiveDateRequest;

public final class ParamValidator {
	
	
	public void validateRequest(Object obj, ErrorHandler handler) throws ErrorMessage {
        if (obj == null){
            throw new ValidationException("The request is not valid.");            
        }
    }
	
	public void validateRequest(GetEligibilityByEffectiveDateRequest obj, ErrorHandler handler) throws ErrorMessage {
		if(obj == null){
			throw new ValidationException("The request is not valid.");
		}
		if(obj.getEffectiveDate() == null){
			throw new ValidationException("The effective date must be defined.");
		}
	}
	
	
	public void validateSku(List<String> skus, ErrorHandler handler) throws ErrorMessage {
        if (skus == null || skus.isEmpty()) {
            throw new ValidationException("No valid product SKU found in the request.");
        }
        for(String sku : skus){
            validateSku(sku, handler);
        }
    }
	
	public Long validateSku(String sku, ErrorHandler handler) throws ErrorMessage {
        if (hasValue(sku)){
            if (hasPosNumValue(sku)) {
                return new Long(sku);
            }else{
                throw new ValidationException("Product SKU \""+sku+"\" is invalid.");                
            }
        }
        return null;
    }
		
	public void validateCustType(List<String> custType, ErrorHandler handler) throws ErrorMessage {        
        if (custType != null && !custType.isEmpty()) {
            Iterator<String> it = custType.listIterator();
            while(it.hasNext()){
                validateCustType(it.next(),handler);
            }            
        }else{
            throw new ValidationException("No customer type found in the request.");
        }
    }
	
	public void validateCustType(String custType, ErrorHandler handler) throws ErrorMessage {        
        if (!hasValue(custType)) {             
            throw new ValidationException("Customer type cannot be defined empty in the request.");
        }
    }
	
	public boolean hasValue(String flag){
        return flag != null && flag.trim().length() > 0;
    }
	
	public boolean hasPosNumValue(String flag){
        boolean res = false;
        try{
            res = Long.valueOf(flag.trim()).intValue() > 0;
        }catch(Throwable e){
        }
        return res;
    }
	
	public boolean isDateDefined(Date date){
        return date != null;
    }
	
}
