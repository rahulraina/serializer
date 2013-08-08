package serializers;
import java.util.Map;


public interface Serializer<TYPE> {

	public String getRootName();
	public String getSideloadPoolName();
	
	public Map<String, Object> serialize(TYPE type, SerializingContext ctx);

}
