package service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import model.Customer;
import model.GetBirthdayFromCustomer;
import predicate.AgePredicate;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Singleton
public class AdultCustomersSupplier implements Supplier<List<Customer>> {

	@Inject
	private CustomerSupplier customerSupplier;

	private final Predicate<Customer> filterAdult = Predicates.compose(AgePredicate.ADULT, GetBirthdayFromCustomer.INSTANCE);

	@Override
	public List<Customer> get() {
		return Lists.newArrayList(Collections2.filter(customerSupplier.get(), filterAdult));
	}

}
