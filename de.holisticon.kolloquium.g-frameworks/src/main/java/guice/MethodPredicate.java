package guice;

import static com.google.common.base.Predicates.and;
import static java.lang.reflect.Modifier.isPublic;

import java.lang.reflect.Method;

import com.google.common.base.Predicate;
import com.google.common.eventbus.Subscribe;

public enum MethodPredicate implements Predicate<Method> {

    ANNOTATED_WITH_SUBSCRIBE {

        @Override
        public boolean apply(final Method method) {
            return method.isAnnotationPresent(Subscribe.class);
        }
    },
    VOID {

        @Override
        public boolean apply(final Method method) {
            return Void.TYPE.equals(method.getReturnType());
        }
    },
    PARAMETER_ONE {

        @Override
        public boolean apply(final Method method) {
            return method.getParameterTypes().length == 1;
        }
    },

    PUBLIC {

        @Override
        public boolean apply(final Method method) {
            return isPublic(method.getModifiers());
        }
    },
    VALID_SUBSCRIBE {

        @Override
        @SuppressWarnings("unchecked")
        public boolean apply(final Method method) {
            return and(ANNOTATED_WITH_SUBSCRIBE, VOID, PARAMETER_ONE, PUBLIC).apply(method);
        }
    };

}
