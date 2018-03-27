package com.bcldb.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PRODUCT_CUSTOMER_ELIGIBILITY", schema="ELIGIBILITY")
@NamedQueries({
              @NamedQuery(name = ProductCustomerEligibilityEnt.FIND_ALL,query = "select o from ProductCustomerEligibilityEnt o"),
              @NamedQuery(name = ProductCustomerEligibilityEnt.FIND_BY_SKU ,query = "select o from ProductCustomerEligibilityEnt o Where o.productId = :sku"),
              @NamedQuery(name = ProductCustomerEligibilityEnt.FIND_BY_EFFECTIVE_DATE ,query = "select o from ProductCustomerEligibilityEnt o Where o.effectiveDate = :effDate"),
              @NamedQuery(name = ProductCustomerEligibilityEnt.FIND_BY_CTYPE ,query = "select o from ProductCustomerEligibilityEnt o Where o.customerType = :custType")})
@IdClass(ProductCustomerEligibilityEntPK.class)
public class ProductCustomerEligibilityEnt implements Serializable {
	
    private static final long serialVersionUID = -4128299142302577690L;
    
    public static final String FIND_ALL = "ProductCustomerEligibilityEnt.findAll";
    public static final String FIND_BY_SKU = "ProductCustomerEligibilityEnt.findBySKU";
    public static final String FIND_BY_CTYPE = "ProductCustomerEligibilityEnt.findByCustType";
    public static final String FIND_BY_EFFECTIVE_DATE = "ProductCustomerEligibilityEnt.findByEffectiveDate";
    
    
    @Id
    @Column(name = "CUSTOMER_TYPE")
    private String customerType;
    @Id
    @Column(name = "PRODUCT_ID")        
    private Integer productId;
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_DATE")
    private Date effectiveDate;
    @Column(name = "ORDER_CASE")
    private String orderCase;
    @Column(name = "ORDER_SU")
    private String orderSu;

    public ProductCustomerEligibilityEnt() {
    }

    public ProductCustomerEligibilityEnt(String customerType, String orderCase, String orderSu,
                                         Integer productId, Date effectiveDate) {
        this.customerType = customerType;
        this.orderCase = orderCase;
        this.orderSu = orderSu;
        this.productId = productId;
        this.effectiveDate = effectiveDate;
    }


    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getOrderCase() {
        return orderCase;
    }

    public void setOrderCase(String orderCase) {
        this.orderCase = orderCase;
    }

    public String getOrderSu() {
        return orderSu;
    }

    public void setOrderSu(String orderSu) {
        this.orderSu = orderSu;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
