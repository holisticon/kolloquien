package model;

import java.util.Date;

import com.google.common.base.Function;

public enum GetBirthdayFromCustomer implements Function<Customer, Date> {
	INSTANCE;

	@Override
	public Date apply(final Customer customer) {
		return customer.getBirthday();
	}

}
