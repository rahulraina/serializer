package serializers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SideloadPool {

	// Name of the pool.. e.g. "humans"
	private String name;
	
	// All objects in the Pool
	private List<Map<String, Object>> pool;
	private Map<String, Map<String, Object>> poolElemsById;

	public SideloadPool(String name) {
		this.name = name;
		pool = new ArrayList<>();
		poolElemsById = new HashMap<String, Map<String, Object>>();
	}

	public boolean isEmpty() {
		return pool == null || pool.size() == 0;
	}
	
	public Map<String, Object> findOrCreateById(String id) {
		Map<String, Object> found = findById(id);
		if (found == null) {
			found = new HashMap<String, Object>();
			found.put("id", id);
			this.addObjectToPool(id, found);
		}
		return found;
	}

	public Map<String, Object> findById(String id) {
		Map<String, Object> found = poolElemsById.get(id);
		return found;
	}

	public void removeById(String id) {
		Map<String, Object> found = findById(id);
		if (found != null) {
			poolElemsById.remove(id);
			pool.remove(found);
		}

	}
	
	public void addObjectToPool(String id, Map<String, Object> object) {
		pool.add(object);
		poolElemsById.put(id, object);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getPool() {
		return pool;
	}

	public void setPool(List<Map<String, Object>> pool) {
		// Whenever pool changes, update by re-inserting records...
		this.pool.clear();
		this.poolElemsById.clear();
		for (Map<String, Object> poolItem : pool) {
			addObjectToPool((String) poolItem.get("id"), poolItem);
		}
	}

	public boolean hasRecordForId(String id) {
		return poolElemsById.containsKey(id);
	}

	
	
}
