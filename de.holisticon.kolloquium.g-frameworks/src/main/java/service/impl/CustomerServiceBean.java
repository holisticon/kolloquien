package service.impl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Customer;
import service.CustomerService;
import service.OtherService;

@Stateless
public class CustomerServiceBean implements CustomerService {

	@EJB
	private OtherService otherService;

	int maxValue;

	@PostConstruct
	public void init() {
		maxValue = 5;
	}

	@Override
	public Customer findCustomer(final Long id) {
		otherService.doIt();
		return null;
	}

}
