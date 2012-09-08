package function;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.google.common.base.Function;
import com.google.common.base.Throwables;

public enum ParseDate implements Function<String, Date> {
	DDMMYYYY;

	@Override
	public Date apply(final String ddmmyyyy) {
		try {
			return DateFormat.getDateInstance(DateFormat.MEDIUM).parse(ddmmyyyy);
		} catch (final ParseException e) {
			throw Throwables.propagate(e);
		}
	}

}
