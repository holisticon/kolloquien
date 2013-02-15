package de.holisticon.kolloqium.tomee.forum.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.LoggerFactory;

import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;

@Stateless
public class ForumEntryPersistenceBean implements IForumEntryPersistenceBeanLocal {

    @PersistenceContext(name = "pu")
    private EntityManager em;

    org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ForumEntryPersistenceBean.class);

    @Override
    public List<ForumEntry> getForumEntries() {

        final Query query = this.em.createNamedQuery("getForumEntries");

        @SuppressWarnings("unchecked")
        final List<ForumEntry> list = query.getResultList();

        // detach
        this.em.clear();

        return list;

    }

    @Override
    public ForumEntry getForumEntry(final Long id, final String token) {
        final Query query = this.em.createNamedQuery("getForumEntry");

        final ForumEntry forumEntry = (ForumEntry)query.getSingleResult();

        // detach
        this.em.clear();

        return forumEntry;
    }

    @Override
    public ForumEntry saveForumEntry(final ForumEntry forumEntry) {
        final ForumEntry newForumEntry = this.em.merge(forumEntry);
        return newForumEntry;
    }

}
