package de.holisticon.kolloqium.tomee.forum.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Forum_Entry")
@NamedQueries({
	@NamedQuery(name="getForumEntries", query="select fen from ForumEntry fen order by fen.creationDate desc"),
	@NamedQuery(name="getForumEntry", query="select fen from ForumEntry fen where fen.id=:id and fen.token=:token")
})
public class ForumEntry {

	@Column(name="fen_check_date")
	private Date checkDate;
	@Column(name="fen_content", nullable=false, length=5000)
	private String content;
	@Column(name="fen_contributor", length=256)
	private String contributor;
	@Column(name="fen_creation_date")
	private Date creationDate;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fen_id")
	private Long id;
	@Column(name="fen_state", columnDefinition="enum('NEW', 'ACCEPTED', 'REJECTED')")
	@Enumerated(EnumType.STRING)
	private State state;

	@Column(name="fen_title", length=256)
	private String title;

	@Column(name="fen_token", length=64)
	private String token;

	public Date getCheckDate() {
		return this.checkDate;
	}
	public String getContent() {
		return this.content;
	}
	public String getContributor() {
		return this.contributor;
	}
	public Date getCreationDate() {
		return this.creationDate;
	}
	public Long getId() {
		return this.id;
	}
	public State getState() {
		return this.state;
	}
	public String getTitle() {
		return this.title;
	}
	public String getToken() {
		return this.token;
	}
	public void setCheckDate(final Date checkDate) {
		this.checkDate = checkDate;
	}
	public void setContent(final String content) {
		this.content = content;
	}
	public void setContributor(final String contributor) {
		this.contributor = contributor;
	}
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	public void setState(final State state) {
		this.state = state;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	public void setToken(final String token) {
		this.token = token;
	}


}
