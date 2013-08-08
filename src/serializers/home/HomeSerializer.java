package serializers.home;

import java.util.Map;

import model.Home;
import serializers.Serializer;
import serializers.SerializingContext;


public class HomeSerializer implements Serializer<Home> {

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
	public String getRootName() {
		return "home";
	}
	
	@Override
	public String getSideloadPoolName() {
		return "homes";
	}

}
