package smiley.models.dao;

import java.util.List;

import smiley.models.Profile;

public interface ProfileDao {
	
	void create(Profile profile);
	Profile findbyID(Long id);
	List<Profile> findProfiles();
	Profile findByName(String name);
	void update(Profile profile);
	List<Profile> findProfiles(String nome, boolean active);
	

}
