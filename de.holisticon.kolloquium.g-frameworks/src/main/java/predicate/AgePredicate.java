package predicate;

import java.util.Calendar;
import java.util.Date;

import model.Constants;

import com.google.common.base.Predicate;

public enum AgePredicate implements Predicate<Date> {

	ADULT {

		@Override
		public boolean apply(final Date birthday) {
			final Calendar cal = Calendar.getInstance();
			cal.setTime(birthday);
			cal.add(Calendar.YEAR, Constants.AGE_ADULT);

			return new Date().after(cal.getTime());
		}
	},
	CHILD {

		@Override
		public boolean apply(final Date birthday) {
			return !ADULT.apply(birthday);
		}
	};

}
