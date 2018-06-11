package smiley.models.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import smiley.utils.JPAUtil;
import smiley.models.Profile;
import smiley.models.User;
import smiley.models.dao.UserDao;

public class UserJpaDao implements UserDao {

	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();

	@Override
	public void create(User user) {
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
	}

	@Override
	public User find(String username) {
		entityManager.getTransaction().begin();
		TypedQuery<User> query = entityManager
				.createQuery("select user from User user " + " where user.username = :username", User.class);
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		entityManager.getTransaction().commit();
		if (users.isEmpty())
			return null;
		User user = users.get(0);
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		entityManager.getTransaction().begin();
		TypedQuery<User> query = entityManager.createQuery("select user from User user", User.class);
		List<User> users = query.getResultList();
		entityManager.getTransaction().commit();
		if (users.isEmpty())
			return null;
		else
			return users;
	}

	@Override
	public void update(User user) {
		entityManager.getTransaction().begin();
		entityManager.merge(user);
		entityManager.getTransaction().commit();

	}

	@Override
	public List<User> find(String name, String username, Profile profile, Boolean active) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

		Root<User> root = query.from(User.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<String> nomePath = root.<String>get("name");
		Path<String> usernamePath = root.<String>get("username");
		Path<Profile> profilePath = root.<Profile>get("profile");
		Path<Boolean> activePath = root.<Boolean>get("active");

		if (name != null) {
			if (!name.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(nomePath, "%" + name + "%");
				predicates.add(predicate);
			}
		}

		if (username != null) {
			if (!username.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(usernamePath, "%" + username + "%");
				predicates.add(predicate);
			}
		}

		if (profile != null) {
			Predicate predicate = criteriaBuilder.equal(profilePath, profile);
			predicates.add(predicate);
		}

		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(activePath, active);
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<User> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		return typedQuery.getResultList();
	}

	@Override
	public User find(long id) {
		entityManager.getTransaction().begin();
		TypedQuery<User> query = entityManager
				.createQuery("select user from User user" + " where user.id = :id", User.class);
		query.setParameter("id", id);
		List<User> users = query.getResultList();
		entityManager.getTransaction().commit();
		if (users.isEmpty())
			return null;
		User user = users.get(0);
		return user;
	}

}
