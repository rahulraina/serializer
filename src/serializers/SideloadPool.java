package serializers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SideloadPool {

	// Name of the pool.. e.g. "humans"
	private String name;
	
	// All objects in the Pool
	private Collection<Map<String, Object>> pool;
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
		Map<String, Object> found = poolElemsById.get(id);
		if (found == null) {
			found = new HashMap<String, Object>();
			found.put("id", id);
			this.addObjectToPool(id, found);
		}
		return found;
	}

	public void removeById(String id) {
		Map<String, Object> foundByTypeAndId = poolElemsById.get(id);
		Map<String, Object> found = poolElemsById.get(id);
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

	public Collection<Map<String, Object>> getPool() {
		return pool;
	}

	public void setPool(Collection<Map<String, Object>> pool) {
		this.pool = pool;
	}

	
	
}
