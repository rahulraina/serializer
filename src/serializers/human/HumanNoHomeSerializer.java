package serializers.human;

import java.util.Map;

import serializers.Serializer;
import serializers.SerializingContext;

import model.Human;


public class HumanNoHomeSerializer implements Serializer<Human> {
	
	public HumanNoHomeSerializer() {
	}

	@Override
	public Map<String, Object> serialize(Human human, SerializingContext ctx) {
		Map<String, Object> serializedHuman = ctx.getObjectByTypeAndIdObject("humans", human.getId());

		// Take care of NORMAL attributes
		serializedHuman.put("name", human.getName());

//		// Take care of all human child-attributes
//		try {
//			ctx.pushObject(serializedHuman);
//			
////			Map<String, Object> serializedHome = homeSerializer.serialize(human.getHome(), ctx);
////			serializedHuman.put("home_id", serializedHome.get("id"));
//		} finally {
//			// Done Taking care of all human child-attributes
//			ctx.popCurrentObject();			
//		}
//
		return serializedHuman;
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
