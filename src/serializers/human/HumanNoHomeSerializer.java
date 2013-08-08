package serializers.human;

import java.util.Map;

import model.Human;
import serializers.DeserializingContext;
import serializers.Serializer;
import serializers.SerializingContext;


public class HumanNoHomeSerializer implements Serializer<Human> {
	
	public HumanNoHomeSerializer() {
	}

	@Override
	public Map<String, Object> serialize(Human human, SerializingContext ctx) {
		Map<String, Object> serializedHuman = ctx.getObjectByTypeAndIdObject("humans", human.getId());

		// Take care of NORMAL attributes
		serializedHuman.put("name", human.getName());

		return serializedHuman;
	}

	@Override
	public Human deserialize(Map<String, Object> hash, DeserializingContext ctx) {
		throw new IllegalArgumentException("Deserialize not implemented for HumanNoHomeSerializer.");
	}
	
	@Override
	public Human createOrFindById(String id) {
		throw new IllegalArgumentException("createOrFindById not implemented for HumanNoHomeSerializer.");
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
