package serializers;
import java.util.Map;


public interface Serializer<TYPE> {

	public String getRootName();
	public String getSideloadPoolName();
	
	public Map<String, Object> serialize(TYPE type, SerializingContext ctx);
	

	// DESERIALIZE METHODS
	
	/**
	 * Find an object of this type in the authoritative data-store. If one doesn't exist, return a NEW object
	 */
	public TYPE createOrFindById(String id);
	
	/**
	 * Deserialize the hash within the scope of the context into the resulting Object
	 */
	public TYPE deserialize(Map<String, Object> hash, DeserializingContext ctx);
	
}
