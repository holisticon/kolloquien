package predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import function.ParseDate;

public class AgePredicateTest {

	@Test
	public void shouldReturnTrueForAdult() throws ParseException {
		final Date birthday = ParseDate.DDMMYYYY.apply("12.03.1993");
		assertThat(AgePredicate.ADULT.apply(birthday), is(true));
	}

	@Test
	public void shouldReturnFalseForChild() throws ParseException {
		final Date birthday = ParseDate.DDMMYYYY.apply("12.03.1998");
		assertThat(AgePredicate.ADULT.apply(birthday), is(false));
	}
}
