package serializers;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class DeserializingContext {

	private Map<String, SideloadPool> objectPoolsByType;
	private Map<String, Object> objectDeserializeInProgressByType;

	public DeserializingContext(Map<String, Object> sideloadPools) {
		objectPoolsByType = new HashMap<String, SideloadPool>();
		objectDeserializeInProgressByType = new HashMap<String, Object>();
		
		// For each sideloadPool..
		for (Entry<String, Object> entry : sideloadPools.entrySet()) {
			if (entry.getValue() instanceof Collection<?>) {
				// Add this as a new side-load pool
				System.out.println("Adding new sideload pool to Deserialization context: " + entry.getKey() + " with values: " + entry.getValue());
				SideloadPool newPool = new SideloadPool(entry.getKey());
				newPool.setPool((List<Map<String, Object>>) entry.getValue());
				objectPoolsByType.put(entry.getKey(), newPool);
			}
		}
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

		// If it's already baking, return what we got..
		if (createdOrFound != null) {
			return createdOrFound;
		}
		
		// If it's not already baking, find the authoritative source to start with..
		createdOrFound = serializer.createOrFindById(id);

		// Add to our list of things in progress
		this.objectDeserializeInProgressByType.put(typeIdKey, createdOrFound);

		// If it's in the side-loads too, we need to deserialize it so it gets the latest updates, too..
		// to resolve any changes to it
		SideloadPool findInSideloads = objectPoolsByType.get(jsonType);
		if ((findInSideloads != null) && findInSideloads.hasRecordForId(id)) {
			System.out.println("Found sideload record for: " + jsonType + ", id: " + id);
			Map<String, Object> foundInSideload = findInSideloads.findById(id);
			createdOrFound = serializer.deserialize(foundInSideload, this);
		}

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
