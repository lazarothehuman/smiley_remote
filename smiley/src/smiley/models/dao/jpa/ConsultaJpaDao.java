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

import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.User;
import smiley.models.dao.ConsultaDao;
import smiley.utils.JPAUtil;

public class ConsultaJpaDao implements ConsultaDao {
	
	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();


	@Override
	public void create(Consulta consulta) {
		entityManager.getTransaction().begin();
		entityManager.persist(consulta);
		entityManager.getTransaction().commit();
		
		
	}


	@Override
	public void update(Consulta consulta) {
		entityManager.getTransaction().begin();
		entityManager.merge(consulta);
		entityManager.getTransaction().commit();
	}



	@Override
	public List<Consulta> find(Cliente cliente, Medico medico, User user, Long id, Boolean active) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Consulta> query = criteriaBuilder.createQuery(Consulta.class);

		Root<Consulta> root = query.from(Consulta.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<Long> idPath = root.<Long>get("id");
		Path<Medico> medicoPath = root.<Medico>get("medico");
		Path<Cliente> clientePath = root.<Cliente>get("cliente");
		Path<User> userPath = root.<User>get("user");
		Path<Boolean> activePath = root.<Boolean>get("active");

		if (id != null) {
			Predicate predicate = criteriaBuilder.equal(idPath, id);
			predicates.add(predicate);
		}

		if (cliente != null) {
			Predicate predicate = criteriaBuilder.equal(clientePath, cliente);
			predicates.add(predicate);
		}
		
		if (medico != null) {
			Predicate predicate = criteriaBuilder.equal(medicoPath, medico);
			predicates.add(predicate);
		}
		
		if (user != null) {
			Predicate predicate = criteriaBuilder.equal(userPath, user);
			predicates.add(predicate);
		}

		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(activePath, active);
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Consulta> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		return typedQuery.getResultList();
	}

}
