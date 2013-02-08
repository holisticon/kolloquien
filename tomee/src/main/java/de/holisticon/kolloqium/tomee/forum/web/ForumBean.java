package de.holisticon.kolloqium.tomee.forum.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import de.holisticon.kolloqium.tomee.forum.ejb.IForumEntryBeanLocal;
import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;
import de.holisticon.kolloqium.tomee.forum.entities.State;


@ManagedBean(name="forumBean")
@RequestScoped
public class ForumBean {

	@Inject
	private IForumEntryBeanLocal forumEntryBean;

	private ForumEntry newEntry;

	public List<ForumEntry> getEntries() {

		return this.forumEntryBean.getForumEntries();

	}

	public ForumEntry getNewEntry() {
		if (this.newEntry == null) {
			this.newEntry = new ForumEntry();
			this.newEntry.setState(State.NEW);
			this.newEntry.setToken("xyz");

		}

		return this.newEntry;
	}

	public void resetNewEntry () {
		this.newEntry = null;
	}

}
