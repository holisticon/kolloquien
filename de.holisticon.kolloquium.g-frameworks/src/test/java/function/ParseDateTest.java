package function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DateFormat;

import org.junit.Test;

public class ParseDateTest {

	@Test
	public void shouldReturnParsedDate() {
		assertThat(DateFormat.getDateInstance(DateFormat.MEDIUM).format(ParseDate.DDMMYYYY.apply("12.03.2012")), is("12.03.2012"));
	}

}
