package com.bcldb.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ProductCustomerEligibilityEntPK implements Serializable {
	
	
    private static final long serialVersionUID = -8024803474573239321L;
		
	public String customerType;
    public Integer productId;
    @Temporal(TemporalType.DATE)
    public Date effectiveDate;

    public ProductCustomerEligibilityEntPK() {
    }

    public ProductCustomerEligibilityEntPK(String customerType, Integer productId) {
        this.customerType = customerType;
        this.productId = productId;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj instanceof ProductCustomerEligibilityEntPK){
            final ProductCustomerEligibilityEntPK pk = (ProductCustomerEligibilityEntPK) obj;
            return pk.getProductId().equals(productId) && 
                   pk.getCustomerType().equals(customerType) && 
                   pk.getEffectiveDate().equals(effectiveDate) ;
        }else{
            return false;
        }
    }    

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public int hashCode() {
        return customerType.hashCode();
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
