package tu.sofia.cv.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Abstract JPA DAO class
 *
 * @param <Key>
 *            type of primary key of entity
 * @param <Entity>
 *            type of entity
 */
@Singleton
public abstract class AbstractJpaDao<Key, Entity> {

	private static final String DEBUG_CREATE_MESSAGE = "Create: [{}]";
	private static final String DEBUG_UPDATE_MESSAGE = "Update: [{}]";
	private static final String DEBUG_DELETE_MESSAGE = "Delete: [{}]";
	private static final String DEBUG_FIND_BY_ID_MESSAGE = "Find by id: [{}]";
	private static final String DEBUG_ENTITY_FOUND_MESSAGE = "Entity found: [{}]";
	private static final String DEBUG_FIND_ALL_MESSAGE = "Find all";
	private static final String DEBUG_LIST_OF_ENTITIES_FOUND_MESSAGE = "List of entities found: [{}]";

	private final Class<Entity> entityClass;

	@Inject
	private Provider<EntityManager> emProvider;

	protected AbstractJpaDao(final Class<Entity> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract Logger getLogger();

	protected EntityManager getEntityManager() {
		return emProvider.get();
	}

	protected Class<Entity> getEntityClass() {
		return entityClass;
	}

	/**
	 * Persists entity into the database
	 *
	 * @param entity
	 */
	public void create(final Entity entity) {
		getLogger().debug(DEBUG_CREATE_MESSAGE, entity);

		getEntityManager().getTransaction().begin();
		getEntityManager().persist(entity);
		getEntityManager().getTransaction().commit();
	}

	/**
	 * Updates entity into the database
	 *
	 * @param entity
	 * @return entity
	 */
	public Entity update(final Entity entity) {
		getLogger().debug(DEBUG_UPDATE_MESSAGE, entity);

		getEntityManager().getTransaction().begin();
		Entity merge = getEntityManager().merge(entity);
		getEntityManager().getTransaction().commit();
		return merge;
	}

	/**
	 * Deletes entity from the database
	 *
	 * @param entity
	 */
	public void delete(final Entity entity) {
		getLogger().debug(DEBUG_DELETE_MESSAGE, entity);

		getEntityManager().getTransaction().begin();
		getEntityManager().remove(entity);
		getEntityManager().getTransaction().commit();
	}

	/**
	 * Deletes entities from the database
	 *
	 * @param entities
	 */
	public void delete(final List<Entity> entities) {
		getLogger().debug(DEBUG_DELETE_MESSAGE, entities);

		getEntityManager().getTransaction().begin();
		for (Entity e : entities) {
			getEntityManager().remove(e);
		}
		getEntityManager().getTransaction().commit();
	}

	/**
	 * Finds entity by id
	 *
	 * @param id
	 * @return entity
	 */
	public Entity findById(final Key id) {
		getLogger().debug(DEBUG_FIND_BY_ID_MESSAGE, id);

		Entity found = getEntityManager().find(getEntityClass(), id);

		getLogger().debug(DEBUG_ENTITY_FOUND_MESSAGE, found);

		return found;
	}

	/**
	 * Finds all entities
	 *
	 * @return all entities
	 */
	public List<Entity> findAll() {
		getLogger().debug(DEBUG_FIND_ALL_MESSAGE);

		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Entity> cq = cb.createQuery(getEntityClass());
		cq.select(cq.from(getEntityClass()));
		final TypedQuery<Entity> query = getEntityManager().createQuery(cq);
		List<Entity> resultList = query.getResultList();

		getLogger().debug(DEBUG_LIST_OF_ENTITIES_FOUND_MESSAGE, resultList);

		return resultList;
	}

	/**
	 * Returns the count of all entities
	 *
	 * @return the count of all entities
	 */
	public long countAll() {
		CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(getEntityClass())));

		return getEntityManager().createQuery(cq).getSingleResult();
	}
}
