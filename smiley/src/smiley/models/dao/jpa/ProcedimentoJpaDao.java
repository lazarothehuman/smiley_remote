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

import smiley.models.Procedimento;
import smiley.models.dao.ProcedimentoDao;
import smiley.utils.JPAUtil;

public class ProcedimentoJpaDao implements ProcedimentoDao {
	
	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();

	@Override
	public void create(Procedimento procedimento) {
		entityManager.getTransaction().begin();
		entityManager.persist(procedimento);
		entityManager.getTransaction().commit();
	}
	
	@Override
	public void update(Procedimento procedimento) {
		entityManager.getTransaction().begin();
		entityManager.merge(procedimento);
		entityManager.getTransaction().commit();
	}

	@Override
	public List<Procedimento> find(Long id, String nome, String codigo, Boolean active) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Procedimento> query = criteriaBuilder.createQuery(Procedimento.class);

		Root<Procedimento> root = query.from(Procedimento.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<Long> idPath = root.<Long>get("id");
		Path<String> nomePath = root.<String>get("nome");
		Path<String> codigoPath = root.<String>get("codigo");
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

		TypedQuery<Procedimento> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		return typedQuery.getResultList();	}

}
