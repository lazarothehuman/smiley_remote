package smiley.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import smiley.models.User;

@Entity
public class SessionHelper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="selectedUser", unique=true)
	private User selectedUser;
	
	@Transient
	private Map<String, Object> objects = new HashMap<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public Map<String, Object> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, Object> objects) {
		this.objects = objects;
	}

	

}
