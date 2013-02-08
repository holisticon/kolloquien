package de.holisticon.kolloqium.tomee.forum.ejb;

import java.util.List;

import javax.ejb.Local;

import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;
import de.holisticon.kolloqium.tomee.forum.entities.State;


@Local
public interface IForumEntryBeanLocal {

	void addForumEntry(ForumEntry forumEntry);
	List<ForumEntry> getForumEntries();
	boolean setForumEntryState(Long id, String token, State state);
}
