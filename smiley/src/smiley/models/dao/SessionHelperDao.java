package smiley.models.dao;

import smiley.models.User;
import smiley.utils.SessionHelper;

public interface SessionHelperDao {

	void set(User user);

	User get();

	SessionHelper findSession();

}
