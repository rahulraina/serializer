package serializers.home;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import model.Home;
import model.Human;
import serializers.DeserializingContext;
import serializers.Serializer;
import serializers.SerializingContext;
import serializers.human.HumanSerializer;


public class HomeSerializer implements Serializer<Home> {

	private HumanSerializer humanSerializer;
	
	private HomeController homeController;
	
	public HomeSerializer() {
		humanSerializer = new HumanSerializer(this);
	}

	public HomeSerializer(HumanSerializer createdBy) {
		humanSerializer = createdBy;
	}

	@Override
	public Home createOrFindById(String id) {
		if (id == null) {
			Home human = new Home();
			return human;
		}

		return homeController.findById(id);
	}

	@Override
	public Map<String, Object> serialize(Home home, SerializingContext ctx) {
		Map<String, Object> serializedHome = ctx.getObjectByTypeAndIdObject("homes", home.getId());

		// Take care of NORMAL attributes
		serializedHome.put("name", home.getName());
		serializedHome.put("id", home.getId());

		// Take care of all human child-attributes
		try {
			ctx.pushObject(serializedHome);

//			Map<String, Object> serializedHome = homeSerializer.serialize(human.getHome(), ctx);
//			serializedHuman.put("home", serializedHome);
		} finally {
			// Done Taking care of all human child-attributes
			ctx.popCurrentObject();			
		}

		return serializedHome;

	}

	@Override
	public Home deserialize(Map<String, Object> hash, DeserializingContext ctx) {
		// No data? No object for you!
		if (hash == null) { return null; }

		// Create OR FIND a new object for the thing we're about to deserialize
		String id = (String) hash.get("id");

		// Have the deserialization context return the authoritative copy,
		// using the controller-store as a source if needed
		Home home = ctx.createOrFindInStore(this, "homes", id);

//		private String id;
//		private String name;
//		private List<Human> humans;
//		private int age;
		
		// Start deserializing over this object
		if (hash.containsKey("name")) {
			home.setName((String) hash.get("name"));
		};
		
		if (hash.containsKey("age")) {
			// If it's null/non-number, do something smarter..
			home.setAge( Integer.parseInt( (String) hash.get("age") ) );
		};

		// Dereference Home from the pools
		if (hash.containsKey("humans")) {
			Collection<String> humanIds = (Collection<String>) hash.get("humans");

			// Make sure to add/update available humans
			if (home.getHumans() == null) { home.setHumans(new ArrayList<Human>()); }

			if (humanIds != null) {
				// Remove any humans NOT in the new humans-hash
				Iterator<Human> iterHumans = home.getHumans().iterator();
				while (iterHumans.hasNext()) {
					Human eachHuman = iterHumans.next();
					
					// If your ID is not in the humanIds set we specified, REMOVE you
					// from the collection!
					if ( !humanIds.contains(eachHuman.getId()) ) {
						eachHuman.setHome(null);
						iterHumans.remove();
					}
				}

				// For each remaining human that has an ID in the set, make sure we
				// deserialize & merge as needed
				for (String humanId : humanIds) {
					// Human human = ctx.createOrFindInStore(humanController, "humans", humanId);
					// human.join(home);
				}
			}
		};

		return home;
	}
	
	
	@Override
	public String getRootName() {
		return "home";
	}
	
	@Override
	public String getSideloadPoolName() {
		return "homes";
	}

	
}
