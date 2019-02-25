package smiley.models.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import smiley.models.Cliente;
import smiley.models.Sexo;
import smiley.models.dao.ClienteDao;
import smiley.utils.JPAUtil;

public class ClienteJpaDao implements ClienteDao {

	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();

	@Override
	public void create(Cliente cliente) {
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Cliente> findBirthdayClients(int dia, int mes, Boolean active) {
		List<Cliente> clientesBirthday = new ArrayList<>();
		List<Cliente> clientes = findClients(null, null, null, null, null, null, null, active);
		if (clientes != null) {
			for (Cliente cliente : clientes) {
				if (cliente.getDataNascimento().getDate() == dia && cliente.getDataNascimento().getMonth() == mes)
					clientesBirthday.add(cliente);
			}
		}
		return clientesBirthday;
	}

	@Override
	public List<Cliente> findClients(Long id, String nome, String email, String telefone, Date startDate, Date endDate,
			Sexo sexo, Boolean active) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);

		Root<Cliente> root = query.from(Cliente.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<Long> idPath = root.<Long>get("id");
		Path<String> nomePath = root.<String>get("nome");
		Path<String> emailPath = root.<String>get("email");
		Path<String> telefonePath = root.<String>get("telefone");
		Path<Sexo> sexoPath = root.<Sexo>get("sexo");
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

		if (email != null) {
			if (!email.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(emailPath, "%" + email + "%");
				predicates.add(predicate);
			}
		}

		if (telefone != null) {
			if (!telefone.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(telefonePath, "%" + telefone + "%");
				predicates.add(predicate);
			}
		}

		if (startDate != null) {
			Predicate greaterThanOrEqualPredicate = criteriaBuilder
					.greaterThanOrEqualTo(root.<Date>get("dataNascimento"), startDate);
			predicates.add(greaterThanOrEqualPredicate);
		}
		if (endDate != null) {
			Predicate lessThanPredicate = criteriaBuilder.lessThan(root.<Date>get("dataNascimento"), endDate);
			predicates.add(lessThanPredicate);
		}

		if (sexo != null) {
			Predicate predicate = criteriaBuilder.equal(sexoPath, sexo);
			predicates.add(predicate);
		}

		if (active != null) {
			Predicate predicate = criteriaBuilder.equal(activePath, active);
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Cliente> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		return typedQuery.getResultList();
	}

	@Override
	public void update(Cliente cliente) {
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();

	}

	@Override
	public Cliente find(Long id, String name) {
		entityManager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);

		Root<Cliente> root = query.from(Cliente.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Path<Long> idPath = root.<Long>get("id");
		Path<String> nomePath = root.<String>get("nome");
		Path<Boolean> activePath = root.<Boolean>get("active");

		if (id != null) {
			Predicate predicate = criteriaBuilder.equal(idPath, id);
			predicates.add(predicate);
		}

		if (name != null) {
			if (!name.isEmpty()) {
				Predicate predicate = criteriaBuilder.like(nomePath, "%" + name + "%");
				predicates.add(predicate);
			}
		}
		Boolean active = true;
		Predicate predicate = criteriaBuilder.equal(activePath, active);
		predicates.add(predicate);

		query.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Cliente> typedQuery = entityManager.createQuery(query);
		entityManager.getTransaction().commit();
		return typedQuery.getResultList().get(0);
	}

}
