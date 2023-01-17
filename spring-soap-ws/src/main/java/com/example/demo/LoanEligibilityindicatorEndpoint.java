package com.example.demo;

import javax.xml.namespace.QName;

import org.example.spriing_soap.loaneligibility.Acknowledgement;
import org.example.spriing_soap.loaneligibility.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import jakarta.xml.bind.JAXBElement;



@Endpoint
public class LoanEligibilityindicatorEndpoint {

	private static final String NAMESPACE = "http://www.example.org/spriing-soap/loaneligibility";
	@Autowired
	private LoanEligibilityService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "CustomerRequest")
	@ResponsePayload
	public JAXBElement<Acknowledgement> getLoanStatus(@RequestPayload JAXBElement<CustomerRequest> request) {
		return createJaxbElement(service.checkLoanEligibility(request.getValue()), Acknowledgement.class) ;
	}
	
	 private <T> JAXBElement<T> createJaxbElement(T object, Class<T> clazz) {
		    return new JAXBElement<>(new QName(clazz.getSimpleName()), clazz, object);
		  }

}