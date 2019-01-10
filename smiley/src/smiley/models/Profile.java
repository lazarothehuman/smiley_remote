package smiley.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "profilename", nullable = false, unique = true)
	private String profilename;

	@OneToMany(mappedBy = "profile")
	private List<User> users = new ArrayList<>();

	@ManyToMany(mappedBy = "profiles", cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	private List<Transaccao> transaccoes = new ArrayList<>();

	@Column(name = "active", nullable = false, columnDefinition = "bit")
	private Boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProfilename() {
		return profilename;
	}

	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

	public List<User> getUser() {
		return users;
	}

	public void setUser(List<User> user) {
		this.users = user;
	}

	public void removeUser(User user) {
		if (user != null)
			this.users.remove(user);
	}

	public void addUser(User user) {
		if (user != null)
			this.users.add(user);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Transaccao> getTransaccoes() {
		return Collections.unmodifiableList(transaccoes);
	}

	public void addTransaction(Transaccao transaccao) {
		if(transaccao!=null) {
			transaccoes.add(transaccao);
		}

	}

	public void removeTransaction(Transaccao transaccao) {
		if (transaccao != null) {
			if (transaccoes.contains(transaccao)) 
				transaccoes.remove(transaccao);
		}

	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Profile) {
			Profile profile = (Profile) obj;
			return profile.getId().equals(this.id);
		}
		return false;
	}

	public String toString() {
		return this.profilename;
	}

}
