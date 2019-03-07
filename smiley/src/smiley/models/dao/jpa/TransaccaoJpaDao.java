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

import smiley.models.Transaccao;
import smiley.models.dao.TransaccaoDao;
import smiley.utils.JPAUtil;



public class TransaccaoJpaDao implements TransaccaoDao {
	
	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();

	@Override
	public Transaccao find(Long code) {

			entityManager.getTransaction().begin();
			TypedQuery<Transaccao> query = entityManager
					.createQuery("select transaccao from Transaccao transaccao join fetch transaccao.profiles"+ " where transaccao.code = :code", Transaccao.class);
			query.setParameter("code", code);
			List<Transaccao> transaccaos = query.getResultList();
			entityManager.getTransaction().commit();
			if (transaccaos.isEmpty())
				return null;
			Transaccao transaccao = transaccaos.get(0);
			return transaccao;
		
	}

	@Override
	public void create(Transaccao transaction) {
		entityManager.getTransaction().begin();
		entityManager.persist(transaction);
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Transaccao transaccao) {
		entityManager.getTransaction().begin();
		entityManager.merge(transaccao);
		entityManager.getTransaction().commit();
	}

	@Override
	public List<Transaccao> find() {
		entityManager.getTransaction().begin();
		TypedQuery<Transaccao> query = entityManager
				.createQuery("select distinct transaccao from Transaccao transaccao join fetch transaccao.profiles", Transaccao.class);
		List<Transaccao> transaccaos = query.getResultList();
		entityManager.getTransaction().commit();
		if (transaccaos.isEmpty())
			return null;
		return transaccaos;
	}

	@Override
	public List<Transaccao> find(Long id, String nome, String codigo, Boolean active) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Transaccao> query = criteriaBuilder.createQuery(Transaccao.class);

		Root<Transaccao> root = query.from(Transaccao.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<Long> idPath = root.<Long>get("id");
		Path<String> nomePath = root.<String>get("url");
		Path<String> codigoPath = root.<String>get("code");
		Path<Boolean> activePath = root.<Boolean>get("active");

		if (id != null) {
			Predicate predicate = criteriaBuilder.equal(idPath, id);
			predicates.add(predicate);
		}

		if (nome != null) {
			if (!nome.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(nomePath, "%" + nome + "%");
				predicates.add(predicate);
			}
		}
		
		if (codigo != null) {
			if (!codigo.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(codigoPath, "%" + codigo + "%");
				predicates.add(predicate);
			}
		}

		
		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(activePath, active);
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Transaccao> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		return typedQuery.getResultList();	
	}

}
