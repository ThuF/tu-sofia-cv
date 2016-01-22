package tu.sofia.cv.entity;

/**
 * Interface used for marking the JPA Entities
 * 
 * @param <Key>
 */
public interface IJPAEntity<Key> {

	/**
	 * Returns the value of the JPA key
	 * 
	 * @return the value of the JPA key
	 */
	public Key getKeyValue();

}
