package de.holisticon.kolloqium.tomee.forum.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;

import de.akquinet.jbosscc.needle.annotation.InjectInto;
import de.akquinet.jbosscc.needle.annotation.Mock;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import de.holisticon.kolloqium.tomee.forum.ejb.ForumEntryBean;
import de.holisticon.kolloqium.tomee.forum.ejb.IForumEntryBeanLocal;
import de.holisticon.kolloqium.tomee.forum.ejb.IForumEntryPersistenceBeanLocal;
import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;

/**
 * A Collaboration-test of the Controller behavior, external components
 * are called directly
 * 
 * @author Jan Galinski, Holisticon AG
 */
public class ForumControllerCollaborationTest {

    /**
     * One Rule to rule them all - injects mocks and Test-instances
     */
    @Rule
    public final NeedleRule needle = new NeedleRule();

    /**
     * The class we are testing right now
     */
    @ObjectUnderTest
    private ForumController forumController;

    /**
     * Mock injected to ForumController#forumBean
     */
    @ManagedProperty(value = "anyValue")
    private ForumBean forumBean;

    /**
     * Instance ForumController#formEntryBean (unused because it is only used for injection)
     */
    @ObjectUnderTest(implementation = ForumEntryBean.class)
    @InjectInto(targetComponentId = "forumController")
    @SuppressWarnings("unused")
    private IForumEntryBeanLocal forumEntryBean;

    /**
     * Mock used to verify internal behavior of {@link IForumEntryBeanLocal#addForumEntry(ForumEntry)}
     */
    @Inject
    private IForumEntryPersistenceBeanLocal forumEntryPersistenceBean;

    /**
     * mock Entry used to verify interactions
     */
    @Mock
    private ForumEntry forumEntry;

    /**
     * This test verifies that saveEntry invoces forumBean and
     * forumEntryBean
     */
    @Test
    public void shouldSaveEntry() {
        when(forumBean.getNewEntry()).thenReturn(forumEntry);

        // all mocks used in this test in order
        final InOrder inOrder = inOrder(forumBean, forumEntry, forumEntryPersistenceBean);

        this.forumEntryPersistenceBean.saveForumEntry(forumEntry);
        // call saveEntry on objectUnderTest and assert its return value
        final String saveEntry = forumController.saveEntry();
        assertThat(saveEntry, is("forum"));

        // verify the internal behavior
        inOrder.verify(forumBean).getNewEntry();
        inOrder.verify(forumEntry).setCreationDate(any(Date.class));
        inOrder.verify(forumEntryPersistenceBean).saveForumEntry(forumEntry);
        inOrder.verify(forumBean).resetNewEntry();

        // thats it, ensure no more interactions
        inOrder.verifyNoMoreInteractions();
    }
}
