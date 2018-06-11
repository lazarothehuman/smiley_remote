package smiley.models.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import smiley.models.User;
import smiley.models.dao.SessionHelperDao;
import smiley.utils.JPAUtil;
import smiley.utils.SessionHelper;

public class SessionHelperJpaDao implements SessionHelperDao {
	
	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();

	@Override
	public void set(User user) {
		entityManager.getTransaction().begin();
		TypedQuery<SessionHelper> query = entityManager.createQuery(
				"select sessionHelper from SessionHelper sessionHelper "
						+ "where sessionHelper.id = :id", SessionHelper.class);
		query.setParameter("id", 1l);
		List<SessionHelper> sessionHelpers = query.getResultList();
		if (!sessionHelpers.isEmpty()) {
			sessionHelpers.get(0).setSelectedUser(user);
			entityManager.merge(sessionHelpers.get(0));
		}
		entityManager.getTransaction().commit();
		
	}

	@Override
	public User get() {
		User user = null;
		entityManager.getTransaction().begin();
		TypedQuery<SessionHelper> query = entityManager.createQuery(
				"select sessionHelper from SessionHelper sessionHelper "
						+ "where sessionHelper.id = :id", SessionHelper.class);
		query.setParameter("id", 1l);
		List<SessionHelper> sessionHelpers = query.getResultList();
		if (!sessionHelpers.isEmpty()) {
			System.out.println(sessionHelpers.get(0).toString());
			user = sessionHelpers.get(0).getSelectedUser();
		}
		entityManager.getTransaction().commit();
		if(user == null)
			System.out.println("Its null");
		else
			System.out.println("Its not null, "+user.getName());
		return user;
	}
	


}
