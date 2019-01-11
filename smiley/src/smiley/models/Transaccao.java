package smiley.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class Transaccao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique=true)
	private Long code;
	
	@Column(nullable = false, unique=true)
	private String url;
	
	@ManyToMany
	List<Profile> profiles = new ArrayList<>();
	
	@Column(nullable =false, columnDefinition = "bit")
	private Boolean active = true;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public void addProfile(Profile profile) {
		if(profile!=null)
			this.profiles.add(profile);
	}
	
	public List<Profile> getProfiles() {
		return this.profiles;
	}
	
	
	

}
