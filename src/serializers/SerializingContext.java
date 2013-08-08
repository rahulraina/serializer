package serializers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;


public class SerializingContext {

	private Map<String, SideloadPool> objectPoolsByType;

	private Stack<Map<String, Object>> currentObject;
	
	public SerializingContext() {
		objectPoolsByType = new HashMap<String, SideloadPool>();
		currentObject = new Stack<Map<String, Object>>();
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

	public Map<String, Object> getObjectByTypeAndIdObject(String jsonType, String id) {
		SideloadPool typePool = objectPoolsByType.get(jsonType);
		if (typePool == null) { 
			typePool = new SideloadPool(jsonType);
			objectPoolsByType.put(jsonType, typePool);
		}
		Map<String, Object> foundByTypeAndId = typePool.findOrCreateById(id);
		return foundByTypeAndId;
	}
	
	public void pushObject(Map<String, Object> currentObject) {
		this.currentObject.push(currentObject);
	}

	public Map<String, Object> getCurrentObject() {
		return this.currentObject.peek();
	}
	
	public Map<String, Object> popCurrentObject() {
		return this.currentObject.pop();
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
