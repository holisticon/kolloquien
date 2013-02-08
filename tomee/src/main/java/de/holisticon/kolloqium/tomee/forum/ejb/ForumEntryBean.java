package de.holisticon.kolloqium.tomee.forum.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;
import de.holisticon.kolloqium.tomee.forum.entities.State;


@Stateless
public class ForumEntryBean implements IForumEntryBeanLocal{

	@Inject
	private IForumEntryPersistenceBeanLocal forumEntryPersistenceBean;

	@Override
	public void addForumEntry(final ForumEntry forumEntry) {
		this.forumEntryPersistenceBean.saveForumEntry(forumEntry);
	}

	@Override
	public List<ForumEntry> getForumEntries() {

		return this.forumEntryPersistenceBean.getForumEntries();
	}

	@Override
	public boolean setForumEntryState(final Long id, final String token, final State state) {

		try {
			final ForumEntry forumEntry = this.forumEntryPersistenceBean.getForumEntry(id, token);
			if (forumEntry != null) {

				forumEntry.setState(state);
				this.forumEntryPersistenceBean.saveForumEntry(forumEntry);

				return true;

			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
