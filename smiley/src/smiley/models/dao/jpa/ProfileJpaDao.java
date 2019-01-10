package smiley.models.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import smiley.models.Profile;
import smiley.models.dao.ProfileDao;
import smiley.utils.JPAUtil;

public class ProfileJpaDao implements ProfileDao {
	
	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();

	@Override
	public void create(Profile profile) {
		entityManager.getTransaction().begin();
		entityManager.persist(profile);
		entityManager.getTransaction().commit();
		
	}

	@Override
	public Profile findbyID(Long id) {
		entityManager.getTransaction().begin();
		TypedQuery<Profile> query = entityManager.createQuery("select profile from Profile profile" + " where profile.id = :id",
				Profile.class);
		query.setParameter("id", id);
		List<Profile> profiles = query.getResultList();
		entityManager.getTransaction().commit();
		if (profiles.isEmpty())
			return null;
		Profile profile = profiles.get(0);
		return profile;
	}

	@Override
	public List<Profile> findProfiles() {
		entityManager.getTransaction().begin();
		TypedQuery<Profile> query = entityManager.createQuery("select profile from Profile profile",
				Profile.class);
		List<Profile> profiles = query.getResultList();
		entityManager.getTransaction().commit();
		if (profiles.isEmpty())
			return null;
		else
			return profiles;
	}

	//lixo
	@Override
	public Profile findByName(String name) {
		entityManager.getTransaction().begin();
		TypedQuery<Profile> query = entityManager.createQuery("select profile from Profile profile" + " where profile.profilename = :name",
				Profile.class);
		query.setParameter("name", name);
		List<Profile> profiles = query.getResultList();
		entityManager.getTransaction().commit();
		if (profiles.isEmpty())
			return null;
		Profile profile = profiles.get(0);
		return profile;
	}

	@Override
	public void update(Profile profile) {
		entityManager.getTransaction().begin();
		entityManager.merge(profile);
		entityManager.getTransaction().commit();
	}

}
