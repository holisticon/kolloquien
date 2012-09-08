package function;

import java.text.DateFormat;
import java.util.Date;

import com.google.common.base.Function;

public enum FormatDate implements Function<Date, String> {
	DDMMYYYY;

	@Override
	public String apply(final Date date) {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
	}

}
