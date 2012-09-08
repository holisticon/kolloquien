package guice;

import static com.google.common.collect.Collections2.filter;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Ermöglicht die Verwendung von @{@link PostConstruct} im Zusammenhand mit Guice. Statt aufwändiger Constructor injection:
 * 
 * <pre>
 * &#064;Inject
 * public MyClass(YourClass a) {
 *    a.set(....)
 *    a.register(....)
 * }
 * </pre>
 * 
 * die unter anderem auch die Kombination von Field-Injection und Constructor Injection verhindert. kann jetzt wie bei EJB folgendes verwendet werden:
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * &#064;Inject
 * YourClass a;
 * 
 * &#064;PostConstruct
 * public void init() {
 * 	// do stuff with a
 * }
 * 
 * </pre>
 * 
 * @author Jan Galinski, Holisticon AG
 */
public enum PostConstructModule implements Module, TypeListener, Function<Class<?>, List<Method>> {

	INSTANCE;

	private final Predicate<Method> isAnnotatedWithPostConstruct = new Predicate<Method>() {

		@Override
		public boolean apply(final Method method) {
			return method.isAnnotationPresent(PostConstruct.class);
		}
	};

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.inject.Module#configure(com.google.inject.Binder)
	 */
	@Override
	public void configure(final Binder binder) {
		// an alle Klassen diesen Listener binden
		binder.bindListener(Matchers.any(), this);
		binder.bind(PostConstructModule.class).toInstance(INSTANCE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.inject.spi.TypeListener#hear(com.google.inject.TypeLiteral, com.google.inject.spi.TypeEncounter)
	 */
	@Override
	public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
		encounter.register(new InjectionListener<I>() {

			@Override
			public void afterInjection(final I injectee) {
				// alle postconstruct Methoden (nie null) ausführen.
				for (final Method postConstructMethod : INSTANCE.apply(injectee.getClass())) {
					try {
						postConstructMethod.invoke(injectee);
					} catch (final Exception e) {
						throw new RuntimeException(format("@PostConstruct %s", postConstructMethod), e);
					}
				}
			}
		});
	}

	/**
	 * liefert alle PostConstruct Methoden einer Klasse
	 * <p/>
	 * {@inheritDoc}
	 * 
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	@NotNull
	public List<Method> apply(final Class<?> declaringClass) {
		// Schritt 1: alle mit @PostConstruct annotierten Methoden isolieren
		final Collection<Method> allAnnotatedMessages = filter(asList(declaringClass.getMethods()), isAnnotatedWithPostConstruct);

		final List<Method> result = Lists.newArrayList();

		// Schritt 2: filtern auf void und parameterlos
		for (final Method candidate : allAnnotatedMessages) {
			if (candidate.getParameterTypes().length != 0 || !Void.TYPE.equals(candidate.getReturnType())) {
				throw new IllegalStateException(format("@PostConstruct method must bei void and parameter-less: %s", candidate));
			}
			result.add(candidate);
		}
		return result;
	}

}
