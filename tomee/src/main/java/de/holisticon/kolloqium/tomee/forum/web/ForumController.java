package de.holisticon.kolloqium.tomee.forum.web;

import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import de.holisticon.kolloqium.tomee.forum.ejb.IForumEntryBeanLocal;
import de.holisticon.kolloqium.tomee.forum.entities.ForumEntry;


@ManagedBean
@RequestScoped
public class ForumController {

	@ManagedProperty(value="#{forumBean}")
	private ForumBean forumBean;

	@Inject
	private IForumEntryBeanLocal forumEntryBean;

	public String saveEntry() {

		final ForumEntry forumEntry = this.forumBean.getNewEntry();
		forumEntry.setCreationDate(Calendar.getInstance().getTime());
		this.forumEntryBean.addForumEntry(forumEntry);

		// reset new entry bean
		this.forumBean.resetNewEntry();

		// trigger reload of Entries

		return "forum";
	}

	public void setForumBean(final ForumBean forumBean) {
		this.forumBean = forumBean;
	}

}
