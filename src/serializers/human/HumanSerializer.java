package serializers.human;

import java.util.Map;

import model.Home;
import model.Human;
import serializers.DeserializingContext;
import serializers.Serializer;
import serializers.SerializingContext;
import serializers.home.HomeController;
import serializers.home.HomeSerializer;


public class HumanSerializer implements Serializer<Human> {
	
	HomeSerializer homeSerializer;
	
	HumanController humanController;

	HomeController homeController;

	public HumanSerializer() {
		this.homeSerializer = new HomeSerializer(this);
		this.humanController = new HumanController();
	}

	public HumanSerializer(HomeSerializer createdBy) {
		this.homeSerializer = createdBy;
		this.humanController = new HumanController();
	}

	@Override
	public Map<String, Object> serialize(Human human, SerializingContext ctx) {
		Map<String, Object> serializedHuman = ctx.getObjectByTypeAndIdObject("humans", human.getId());

		// Take care of NORMAL attributes
		serializedHuman.put("name", human.getName());

		// Take care of all human child-attributes
		try {
			ctx.pushObject(serializedHuman);
			
			Map<String, Object> serializedHome = homeSerializer.serialize(human.getHome(), ctx);
			serializedHuman.put("home", serializedHome.get("id"));
		} finally {
			// Done Taking care of all human child-attributes
			ctx.popCurrentObject();			
		}

		return serializedHuman;
	}

	@Override
	public Human deserialize(Map<String, Object> hash, DeserializingContext ctx) {
		// No data? No object for you!
		if (hash == null) { return null; }

		// Create OR FIND a new object for the thing we're about to deserialize
		String id = (String) hash.get("id");

		// Have the deserialization context return the authoritative copy,
		// using the controller-store as a source if needed
		Human human = ctx.createOrFindInStore(this, "humans", id);

		// Start deserializing over this object
		if (hash.containsKey("name")) {
			human.setName((String) hash.get("name"));
		};
		
		if (hash.containsKey("age")) {
			// If it's null/non-number, do something smarter..
			human.setAge( Integer.parseInt( (String) hash.get("age") ) );
		};

		// Dereference Home from the pools
		if (hash.containsKey("home")) {
			String homeId = (String) hash.get("home");
			Home home = ctx.createOrFindInStore(homeSerializer, "homes", homeId);
			human.setHome(home);
		};

		return human;
	}
	
	@Override
	public Human createOrFindById(String id) {
		if (id == null) {
			Human human = new Human();
			return human;
		}

		return humanController.findById(id);
	}
	
	@Override
	public String getRootName() {
		return "human";
	}
	
	@Override
	public String getSideloadPoolName() {
		return "humans";
	}

}
