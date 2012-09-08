package model;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class Customer implements Serializable {

	private Long id;
	private Date birthday;
	private String name;
	private String surname;
	private Gender gender;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(final Date birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name, surname, birthday, gender);
	}

	@Override
	public boolean equals(final Object obj) {
		return Objects.equal(this, obj);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("name", name + " " + surname).add("gender", gender).add("birthday", birthday).toString();
	}

}
