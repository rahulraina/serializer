package serializers.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Home;
import model.Human;
import serializers.DeserializingContext;
import serializers.Serializer;
import serializers.SerializingContext;
import serializers.human.HumanNoHomeSerializer;


public class HomeWithOneHumanSerializer implements Serializer<Home> {

	HumanNoHomeSerializer humanNoHomeSerializer;
	
	public HomeWithOneHumanSerializer() {
		humanNoHomeSerializer = new HumanNoHomeSerializer();
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
			
			List<String> humanIds = new ArrayList<String>();
			for (Human human : home.getHumans()) {
				Map<String, Object> serializedHumanNoHome = humanNoHomeSerializer.serialize(human, ctx);
				humanIds.add((String) serializedHumanNoHome.get("id"));
			}
			serializedHome.put("humans", humanIds);

		} finally {
			// Done Taking care of all human child-attributes
			ctx.popCurrentObject();			
		}

		return serializedHome;
	}

	@Override
	public Home createOrFindById(String id) {
		throw new IllegalArgumentException("createOrFindById not implemented for HomeWithOneHumanSerializer.");
	}
	@Override
	public Home deserialize(Map<String, Object> hash, DeserializingContext ctx) {
		throw new IllegalArgumentException("Deserialize not implemented for HomeWithOneHumanSerializer.");
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
