package smiley.models.dao.jpa;

import javax.persistence.EntityManager;

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

}
