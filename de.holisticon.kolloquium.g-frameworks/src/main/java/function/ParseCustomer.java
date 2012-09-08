package function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Iterator;

import model.Customer;
import model.Gender;

import com.google.common.base.Function;
import com.google.common.base.Splitter;

public class ParseCustomer implements Function<String, Customer> {

	private final Splitter onSemicolon = Splitter.on(";").trimResults();

	@Override
	public Customer apply(final String csv) {
		checkArgument(!isNullOrEmpty(csv), "csv must not be null or empty");

		final Customer customer = new Customer();

		final Iterator<String> iterator = onSemicolon.split(csv).iterator();

		customer.setId(Long.valueOf(iterator.next()));
		customer.setName(iterator.next());
		customer.setSurname(iterator.next());
		customer.setBirthday(ParseDate.DDMMYYYY.apply(iterator.next()));
		customer.setGender(Gender.valueOf(iterator.next()));

		return customer;
	}
}
