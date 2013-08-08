package serializers;

import java.util.Map;

public class JsonApiRootSerialization {

	public <TYPE> Map<String, Object> serializeRoot(Serializer<TYPE> serializer, TYPE toSerialize) {
		SerializingContext ctx = new SerializingContext();
		Map<String, Object> serialized = serializer.serialize(toSerialize, ctx);
		
		// B/c it's a root... Pull out all the sideloads, but first REMOVE the one for this id b/c it's the ROOT!
		ctx.removeSideloadedObjectByTypeAndId(serializer.getSideloadPoolName(), (String) serialized.get("id"));

		// Now that the main element was removed from the sideloads pools, 
		// pull out the sideloads and put back IN the root element.
		Map<String, Object> jsonApiRoot = ctx.getSideloadPoolsMap();
		jsonApiRoot.put(serializer.getRootName(), serialized);

		return jsonApiRoot;
	}
}
