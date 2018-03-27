package com.bcldb.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.jboss.logging.Logger;

import producteligibilityservice.ErrorMessage;
import producteligibilityservice.ProductEligibilityService;

import com.bcldb.common.util.Version;
import com.bcldb.service.ejb.ProductEligibilityServiceBean;
import com.bcldb.service.model.ProductCustomerEligibilityEnt;
import com.bcldb.services.common.EmptyRequestType;
import com.bcldb.services.common.GetVersionResponse;
import com.bcldb.services.eligibilityservice.CustomerEligibilityType;
import com.bcldb.services.eligibilityservice.GetEligibilityByCustomerRequest;
import com.bcldb.services.eligibilityservice.GetEligibilityByCustomerResponse;
import com.bcldb.services.eligibilityservice.GetEligibilityByEffectiveDateRequest;
import com.bcldb.services.eligibilityservice.GetEligibilityByEffectiveDateResponse;
import com.bcldb.services.eligibilityservice.GetEligibilityBySKURequest;
import com.bcldb.services.eligibilityservice.GetEligibilityBySKUResponse;
import com.bcldb.services.eligibilityservice.SKUEligibilityType;
import com.bcldb.utils.CommonHelper;
import com.bcldb.utils.DTOConverter;
import com.bcldb.utils.ErrorHandler;
import com.bcldb.utils.ParamValidator;


@WebService(name = "ProductEligibilityService", targetNamespace = "urn:ProductEligibilityService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.bcldb.services.common.ObjectFactory.class,
    com.bcldb.services.eligibilityservice.ObjectFactory.class,
    com.bcldb.services.common.type.ObjectFactory.class
})
public class ProductEligibilityServiceImpl implements ProductEligibilityService {
	
	private static final Logger logger = Logger.getLogger(ProductEligibilityServiceImpl.class);
	
	@Inject
	ProductEligibilityServiceBean service = new ProductEligibilityServiceBean();	
	
	ErrorHandler handler = new ErrorHandler();
	ParamValidator validator = new ParamValidator();
	DTOConverter dtoConverter = new DTOConverter();
	
	@Override
	public GetVersionResponse getVersionInfo(EmptyRequestType part) throws ErrorMessage {
		GetVersionResponse getVersionResponse = new GetVersionResponse();
		getVersionResponse.setVersion(Version.get().getVersionNumber());
		logger.info("getVersion: version number : " + Version.get().getVersionNumber());
		return getVersionResponse;
	}

	@Override
	public GetEligibilityBySKUResponse getEligibilityBySKU(GetEligibilityBySKURequest part) throws ErrorMessage {
		GetEligibilityBySKUResponse dto = new GetEligibilityBySKUResponse();
		
		validator.validateRequest(part, handler);
		validator.validateSku(part.getSKU(), handler);
		
		try{		
			for(String sku : part.getSKU()){
				
				List<ProductCustomerEligibilityEnt> eligs = service.getEligibilityBySKU(Integer.valueOf(sku));
				if(eligs != null && !eligs.isEmpty()){
					SKUEligibilityType entry = dtoConverter.convertSKUEligibilityType(eligs);				
					dto.getSKUEligibilities().add(entry);
				}
			}
		
        }catch(Exception e){
            logger.error("Web service operation \"getEligibilityBySKU\" error for SKU :" + part.getSKU(), e);
            handler.reportError(e, "Service operation error: "+e.getMessage());
        }		
		return dto;
		
	}

	@Override
	public GetEligibilityByCustomerResponse getEligibilityByCustomer(GetEligibilityByCustomerRequest part) throws ErrorMessage {
		GetEligibilityByCustomerResponse dto = new GetEligibilityByCustomerResponse();
		validator.validateRequest(part, handler);
		
		validator.validateCustType(part.getCustomerType(), handler);
		
		try{
			for(String custType : part.getCustomerType()){
				List<ProductCustomerEligibilityEnt> eligs = service.getEligibilityByCustomer(custType);
				if(eligs != null && !eligs.isEmpty()){
					CustomerEligibilityType entry = dtoConverter.convertCustomerEligibityType(eligs);				
					dto.getCustomerEligibilities().add(entry);
				}
			}			
        }catch(Exception e){
            logger.error("Web service operation \"getEligibilityByCustomer\" error for custType :" + part.getCustomerType() , e);
            handler.reportError(e, "Service operation error: "+e.getMessage());
        }
		return dto;		
	}

	@Override
	public GetEligibilityByEffectiveDateResponse getEligibilityByEffectiveDate(GetEligibilityByEffectiveDateRequest part) throws ErrorMessage {
		GetEligibilityByEffectiveDateResponse dto = new GetEligibilityByEffectiveDateResponse();
		
		validator.validateRequest(part, handler);
		
		Date effDate = CommonHelper.gregorian2Date(part.getEffectiveDate());
		
		try{			
			List<ProductCustomerEligibilityEnt> eligs = service.getEligibilityByEffectiveDate(effDate);
			if(eligs != null && !eligs.isEmpty()){
				for(ProductCustomerEligibilityEnt eligibility : eligs){
					CustomerEligibilityType entry = new CustomerEligibilityType();
					entry = dtoConverter.convertEligibilityByEffectiveDate(eligibility);				
					dto.getCustomerEligibilities().add(entry);
				}			
			}
        }catch(Exception e){
            logger.error("Web service operation \"getEligibilityByEffectiveDate\" error for effectiveDate :" + part.getEffectiveDate(), e);
            handler.reportError(e, "Service operation error: "+e.getMessage());
        }
		return dto;
	}

}
