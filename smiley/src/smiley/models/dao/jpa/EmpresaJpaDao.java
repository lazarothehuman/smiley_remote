package smiley.models.dao.jpa;

import javax.persistence.EntityManager;

import smiley.models.Empresa;
import smiley.models.dao.EmpresaDao;
import smiley.utils.JPAUtil;

public class EmpresaJpaDao implements EmpresaDao {
	
	private EntityManager entityManager = (EntityManager) new JPAUtil().getEntityManager();


	@Override
	public void create(Empresa empresa) {
		entityManager.getTransaction().begin();
		entityManager.persist(empresa);
		entityManager.getTransaction().commit();
		
	}

}
