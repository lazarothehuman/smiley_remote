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
		TypedQuery<Profile> query = entityManager
				.createQuery("select profile from Profile profile" + " where profile.id = :id", Profile.class);
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
		TypedQuery<Profile> query = entityManager.createQuery("select profile from Profile profile", Profile.class);
		List<Profile> profiles = query.getResultList();
		entityManager.getTransaction().commit();
		if (profiles.isEmpty())
			return null;
		else
			return profiles;
	}

	// lixo
	@Override
	public Profile findByName(String name) {
		entityManager.getTransaction().begin();
		TypedQuery<Profile> query = entityManager.createQuery(
				"select profile from Profile profile" + " where profile.profilename = :name", Profile.class);
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

	@Override
	public List<Profile> findProfiles(String nome, boolean active) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Profile> query = criteriaBuilder.createQuery(Profile.class);

		Root<Profile> root = query.from(Profile.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<String> nomePath = root.<String>get("profilename");
		Path<Boolean> activePath = root.<Boolean>get("active");



		if (nome != null) {
			if (!nome.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(nomePath, "%" + nome + "%");
				predicates.add(predicate);
			}
		}
		Predicate predicate = criteriaBuilder.equal(activePath, active);
		predicates.add(predicate);

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Profile> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		List<Profile> resultList = typedQuery.getResultList();
		if(resultList.isEmpty())
			return null;
		return typedQuery.getResultList();
	}

}
