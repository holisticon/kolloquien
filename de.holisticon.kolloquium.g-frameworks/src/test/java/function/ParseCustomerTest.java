package function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;

import model.Customer;

import org.junit.Test;

public class ParseCustomerTest {

	@Test
	public void shouldParseFromString() throws ParseException {
		final Customer customer = new ParseCustomer().apply("1;Foo;Bar;01.02.2002;MALE");

		assertThat(customer.getId(), is(1L));
		assertThat(customer.getName(), is("Foo"));
		assertThat(customer.getSurname(), is("Bar"));
		assertThat(customer.getBirthday(), is(DateFormat.getDateInstance(DateFormat.MEDIUM).parse("01.02.2002")));

	}
}
