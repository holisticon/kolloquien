package de.holisticon.kolloqium.tomee.forum.ejb;

import java.util.List;

import javax.ejb.Local;

import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;


@Local
public interface IForumEntryPersistenceBeanLocal {

	List<ForumEntry> getForumEntries();
	ForumEntry getForumEntry(Long id, String token);
	ForumEntry saveForumEntry(ForumEntry forumEntry);

}
