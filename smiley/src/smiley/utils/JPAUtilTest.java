package smiley.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtilTest {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("smileytest");

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
