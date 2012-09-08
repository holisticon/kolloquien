package service.impl;

import static com.google.inject.Guice.createInjector;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import guice.EJBModule;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import service.OtherService;

import com.google.inject.AbstractModule;

public class CustomerServiceBeanTest extends AbstractModule {

	@Mock
	private OtherService otherServiceMock;

	@Inject
	private CustomerServiceBean customerServiceBean;

	@Before
	public void setUp() {
		initMocks(this);
		createInjector(this).injectMembers(this);
	}

	@Override
	protected void configure() {
		install(EJBModule.INSTANCE);
		bind(OtherService.class).toInstance(otherServiceMock);
	}

	@Test
	public void test() {
		assertThat(customerServiceBean.maxValue, is(5));
		customerServiceBean.findCustomer(1L);
		verify(otherServiceMock).doIt();
	}

}
