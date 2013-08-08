package serializers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class DeserializingContext {

	private Map<String, SideloadPool> objectPoolsByType;
	private Map<String, Object> objectDeserializeInProgressByType;

	public DeserializingContext() {
		objectPoolsByType = new HashMap<String, SideloadPool>();
		objectDeserializeInProgressByType = new HashMap<String, Object>();
	}
	
	protected void removeSideloadedObjectByTypeAndId(String jsonType, String id) {
		SideloadPool typePool = objectPoolsByType.get(jsonType);
		if (typePool != null) {
			typePool.removeById(id);
			if (typePool.isEmpty()) {
				objectPoolsByType.remove(jsonType);
			}
		}
	}

	/**
	 * Using the json-type & id and the provided serializer, attempt to see if we're already
	 * in the process of Deserializing this object. If so, return the deserialized object.
	 * Otherwise, have the serializer create/find the authoritative copy by id first and THEN
	 * put that authoritative copy in the store for linking everywhere this jsonType/id is needed.
	 * 
	 * @param serializer
	 * @param jsonType
	 * @param id
	 * @return
	 */
	public <TYPE> TYPE createOrFindInStore(Serializer<TYPE> serializer, String jsonType, String id) {
		String typeIdKey = jsonType+"~"+id;
		TYPE createdOrFound = (TYPE) this.getDeserializationInProgress(typeIdKey);

		// If it's not already baking, find the authoritative source.
		if (createdOrFound == null) {
			createdOrFound = serializer.createOrFindById(id);
		}

		// Add to our list of things in progress
		this.objectDeserializeInProgressByType.put(typeIdKey, createdOrFound);

		return createdOrFound;
	}
	
	public Object getDeserializationInProgress(String id) {
		return objectDeserializeInProgressByType.get(id);
	}
	
	public Map<String, Object> getObjectByTypeAndIdObject(String jsonType, String id) {
		SideloadPool typePool = objectPoolsByType.get(jsonType);
		return typePool != null ? typePool.findById(id) : null;
	}
	
	public Map<String, Object> getSideloadPoolsMap() {
		Map<String, Object> sideloadMap = new HashMap<String, Object>();
		for (Entry<String, SideloadPool> sideload : objectPoolsByType.entrySet()) {
			String poolName = sideload.getKey();
			SideloadPool pool = sideload.getValue();
			List<Map<String, Object>> poolMaps = pool.getPool();
			sideloadMap.put(poolName, poolMaps);
		}
		return sideloadMap;
	}
	
}
