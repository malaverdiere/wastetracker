package es.sendit2us.wastetracker.server;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(HibernateProxy.class)
public class HibernateProxyImpl implements HibernateProxy {
	@PersistenceContext(unitName="entityManager") 
	private EntityManager entityManager;
	
	public Object merge(Object entity) {
		return entityManager.merge(entity);
	}
	
	public void persist(Object entity) {
	
		entityManager.persist(entity);
	}
	
	@Override
	public void remove(Object entity) {
	
		entityManager.remove(entity);
	}
	
	public Query createQuery(String q) {
		return entityManager.createQuery(q);
	}
	
	public Object findBy(Class clazz, Object key) {
		return entityManager.find(clazz, key);
	}
}
