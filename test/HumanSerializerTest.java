import java.util.Map;

import model.Home;
import model.Human;
import serializers.JsonApiRootSerialization;
import serializers.home.HomeWithOneHumanSerializer;
import serializers.human.HumanSerializer;


public class HumanSerializerTest {

	public static void main(String[] args) {
		
		
		Home home = new Home("100", "No place like");
		Human janeDoe = new Human("1", "Jane"); janeDoe.join(home);
		Human johnDoe = new Human("2", "John"); johnDoe.join(home);

		{
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("HUMAN SERIALIZER TEST");
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			HumanSerializer humanSerializer = new HumanSerializer();
			Map<String, Object> serialized = new JsonApiRootSerialization().serializeRoot(humanSerializer, janeDoe);
			System.out.println("SLZD: \n" + serialized);
		}
		
		{
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("HOME WITH ONE HUMAN SERIALIZER TEST");
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			HomeWithOneHumanSerializer homeSerializer = new HomeWithOneHumanSerializer();
			Map<String, Object> serialized = new JsonApiRootSerialization().serializeRoot(homeSerializer, home);
			System.out.println("SLZD: \n" + serialized);
			
		}

	}
	
}
