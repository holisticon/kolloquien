package guice;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static guice.MethodPredicate.VALID_SUBSCRIBE;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.base.Predicate;
import com.google.common.eventbus.EventBus;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public enum RegisterSubscriberModule implements Module, TypeListener, Predicate<Class<?>> {
	INSTANCE;

	private final Logger logger = Logger.getLogger(RegisterSubscriberModule.class.getCanonicalName());

	@Inject
	Provider<EventBus> eventBusProvider;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.inject.Module#configure(com.google.inject.Binder)
	 */
	@Override
	public final void configure(final Binder binder) {
		// Provider für EventBus injecten
		binder.requestInjection(this);

		// Listener für Subscriber
		binder.bindListener(new AbstractMatcher<TypeLiteral<?>>() {

			@Override
			public boolean matches(final TypeLiteral<?> typeLiteral) {
				return apply(typeLiteral.getRawType());
			}
		}, this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral, com.google.inject.spi.TypeEncounter)
	 */
	@Override
	public final <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
		encounter.register(new InjectionListener<I>() {

			@Override
			public void afterInjection(final Object injectee) {
				if (eventBusProvider == null || !apply(injectee.getClass())) {
					return;
				}
				register(injectee);
			}
		});
	}

	public final void register(final Object subscriber) {

		eventBusProvider.get().register(subscriber);
		logger.fine("register(): " + subscriber);
	}

	/**
	 * <p>
	 * liefert true, wenn die Klasse mind. eine Methode definiert, die mit @Subscribe annotiert ist. Die Methode muss <code>public void name(Event event){}</code> heißen.
	 * </p>
	 * {@inheritDoc}
	 * 
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public final boolean apply(final Class<?> check) {
		checkArgument(check != null, "check must not be null!");

		// prüfe, dass die Menge der gefilterten Methoden nicht leer ist (dann gibt es mind. eine Subcribe-Methode)
		return !filter(newArrayList(check.getDeclaredMethods()), VALID_SUBSCRIBE).isEmpty();
	}
}
