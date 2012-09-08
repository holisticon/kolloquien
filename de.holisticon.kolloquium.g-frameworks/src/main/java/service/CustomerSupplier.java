package service;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import model.Customer;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import function.ParseCustomer;

public class CustomerSupplier implements Supplier<List<Customer>> {

	@Override
	public List<Customer> get() {
		try {
			final URL resource = Resources.getResource("customers.csv");

			final List<String> lines = Resources.readLines(resource, Charsets.UTF_8);

			final Collection<Customer> customers = Collections2.transform(lines, new ParseCustomer());

			return Lists.newArrayList(customers);

		} catch (final IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
