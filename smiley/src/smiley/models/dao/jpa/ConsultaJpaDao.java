package smiley.models.dao.jpa;

import javax.persistence.EntityManager;

import smiley.models.Consulta;
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

}
