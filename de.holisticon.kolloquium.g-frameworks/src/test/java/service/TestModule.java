package service;

import static com.google.inject.name.Names.bindProperties;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import model.Customer;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class TestModule extends AbstractModule {

	@Inject
	private Injector injector;

	@Override
	protected void configure() {

		final URL resource = Resources.getResource("test.properties");
		final Properties properties = new Properties();
		try {
			properties.load(Resources.newReaderSupplier(resource, Charsets.ISO_8859_1).getInput());
		} catch (final IOException e) {
			throw Throwables.propagate(e);
		}

		bindConstant().annotatedWith(Names.named("foo")).to("bar");

		bindProperties(binder(), properties);

		requestInjection(this);
	}

	@Provides
	@Named("alle")
	public Supplier<List<Customer>> alleCustomer() {
		return injector.getInstance(CustomerSupplier.class);
	}

	@Provides
	@Named("adults")
	public Supplier<List<Customer>> adultCustomer() {
		return injector.getInstance(AdultCustomersSupplier.class);
	}

}
