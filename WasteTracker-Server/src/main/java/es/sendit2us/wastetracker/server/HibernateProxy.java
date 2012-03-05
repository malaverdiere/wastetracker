package es.sendit2us.wastetracker.server;

import javax.persistence.Query;

public interface HibernateProxy {
	public Object merge(Object entity);
	public void persist(Object entity);
	public Query createQuery(String q);
	public Object findBy(Class clazz, Object key);
	void remove(Object entity);
}
