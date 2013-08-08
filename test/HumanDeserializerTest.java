import java.util.Map;

import model.Home;
import model.Human;
import serializers.JsonApiRootSerialization;
import serializers.human.HumanSerializer;


public class HumanDeserializerTest {

	public static void main(String[] args) {
		
		
		Home home = new Home("100", "No place like");
		Human janeDoe = new Human("1", "Jane"); janeDoe.join(home);
		Human johnDoe = new Human("2", "John"); johnDoe.join(home);

		HumanSerializer humanSerializer = new HumanSerializer();
		Map<String, Object> serialized = new JsonApiRootSerialization().serializeRoot(humanSerializer, janeDoe);
		System.out.println("SLZD: \n" + serialized);

	}
	
}
