package service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import model.Customer;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Supplier;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class AdultCustomersSupplierTest {

	@Inject
	@Named("adults")
	private Supplier<List<Customer>> adultCustomersSupplier1;

	@Inject
	@Named("alle")
	private Supplier<List<Customer>> customersSupplier;

	@Inject
	@Named("foo")
	String foo;

	@Inject
	@Named("a")
	String a;

	@Inject
	@Named("b")
	String b;

	@Inject
	@Named("c")
	Double c;

	@Before
	public void setUp() {
		final Injector injector = Guice.createInjector(new TestModule());

		injector.injectMembers(this);

		assertFalse(adultCustomersSupplier1 == customersSupplier);

		// install(new FactoryModuleBuilder()
		// .implement(Payment.class, RealPayment.class)
		// .build(PaymentFactory.class));
	}

	@Test
	public void test() {
		assertThat(adultCustomersSupplier1.get().size(), is(2));
	}

	@Test
	public void shouldInjectProperties() throws Exception {

		assertEquals("bar", foo);
		assertEquals("hallo", a);
		assertEquals("welt", b);

		assertEquals(Double.valueOf("5"), c);

	}
}
