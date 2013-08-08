package model;

public interface DeserializingRepository<TYPE> {

	/**
	 * Find this object in the repository
	 */
	public TYPE findById(String id);
	
}
