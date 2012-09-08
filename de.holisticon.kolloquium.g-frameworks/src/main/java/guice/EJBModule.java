package guice;

import static com.google.common.collect.Collections2.filter;
import static java.lang.String.format;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.base.Predicate;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public enum EJBModule implements Module, TypeListener {
	INSTANCE;

	private final Logger logger = Logger.getLogger(EJBModule.class.getCanonicalName());

	@Inject
	private Provider<Injector> injector;

	private final Predicate<Field> annotatedWithEJB = new Predicate<Field>() {

		@Override
		public boolean apply(final Field field) {
			return field.isAnnotationPresent(EJB.class);
		}
	};

	private final Matcher<TypeLiteral<?>> isBean = new AbstractMatcher<TypeLiteral<?>>() {

		@Override
		public boolean matches(final TypeLiteral<?> t) {
			final Class<?> rawType = t.getRawType();
			return rawType.isAnnotationPresent(Stateless.class) || rawType.isAnnotationPresent(Stateful.class);
		}
	};

	@Override
	public void configure(final Binder binder) {
		binder.install(PostConstructModule.INSTANCE);
		binder.bindListener(isBean, this);
		binder.requestInjection(this);
	}

	@Override
	public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
		if (!isBean.matches(type)) {
			return; // sanity
		}

		// get all ejb3-fields
		for (final Field ejbField : filter(Arrays.asList(type.getRawType().getDeclaredFields()), annotatedWithEJB)) {
			ejbField.setAccessible(true);
			logger.fine(format("Register field %s for Injection", ejbField));

			encounter.register(new MembersInjector<I>() {

				@Override
				public void injectMembers(final I injectee) {
					try {
						final Object ejb = injector.get().getInstance(ejbField.getType());
						ejbField.set(injectee, ejb);
						logger.fine(format("Inject %s to %s", ejb, ejbField));
					} catch (final IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}
			});
		}
	}
}
