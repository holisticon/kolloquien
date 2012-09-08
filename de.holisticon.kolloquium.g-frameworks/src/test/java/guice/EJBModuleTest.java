package guice;

import static com.google.inject.Guice.createInjector;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;

@SuppressWarnings("unused")
public class EJBModuleTest extends AbstractModule {

    private static final String FOO = "foo";

    @Stateless
    public static class StatelessBean {

        @EJB
        private String name;

        private int age;

        @PostConstruct
        public void init() {
            age = 5;
        }
    }

    public static class NoBean {

        @EJB
        private String name;

        private int age;

        @PostConstruct
        public void init() {
            age = 5;
        }
    }

    @Inject
    private StatelessBean bean;
    @Inject
    private NoBean noBean;

    @Before
    public void setUp() throws Exception {
        // creates ABean
        createInjector(this).injectMembers(this);

    }

    @Override
    protected void configure() {
        install(EJBModule.INSTANCE);
        bind(String.class).toInstance(FOO);
    }

    @Test
    public void shouldInjectEJB() {
        assertThat(bean.name, is(FOO));
    }

    @Test
    public void shouldCallPostconstruct() {
        assertThat(bean.age, is(5));
    }
}
