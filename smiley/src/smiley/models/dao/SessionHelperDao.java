package smiley.models.dao;

import smiley.models.User;

public interface SessionHelperDao {

	void set(User user);

	User get();

}
