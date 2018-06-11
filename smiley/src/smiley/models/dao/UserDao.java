package smiley.models.dao;

import java.util.List;

import smiley.models.Profile;
import smiley.models.User;

public interface UserDao {

	void create(User user);
	User find(String username);
	List<User> findAllUsers();
	void update(User user);
	List<User> find(String name, String username, Profile profile, Boolean active);
	User find(long id);

}
