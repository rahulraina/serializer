import java.util.Map;

import junit.framework.TestCase;
import model.Home;
import model.Human;

import org.junit.Before;
import org.junit.Test;

import serializers.JsonApiRootSerialization;
import serializers.home.HomeWithOneHumanSerializer;
import serializers.human.HumanSerializer;


public class HumanDeserializerTest extends TestCase {

	Home home; 
	Human janeDoe; 
	Human johnDoe;
	
	@Before
	public void setup() {
		home = new Home("100", "No place like");
		janeDoe = new Human("1", "Jane"); janeDoe.join(home);
		johnDoe = new Human("2", "John"); johnDoe.join(home);
	}

	@Test
	public void testHumanSerializer() {
		setup(); // befores aren't firing. don't want to deal with this right now...
		
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("HUMAN SERIALIZER TEST");
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		HumanSerializer humanSerializer = new HumanSerializer();
		Map<String, Object> serialized = new JsonApiRootSerialization().serializeRoot(humanSerializer, janeDoe);
		String asString = serialized.toString();
		System.out.println("SLZD: \n" + asString);
		assertEquals("{homes=[{id=100, name=No place like}], human={id=1, home=100, name=Jane}}", asString);
	}

	@Test
	public void testHumanSerializerSpecialCase() {
		setup(); // befores aren't firing. don't want to deal with this right now...

		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("HOME WITH ONE HUMAN SERIALIZER TEST");
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		HomeWithOneHumanSerializer homeSerializer = new HomeWithOneHumanSerializer();
		Map<String, Object> serialized = new JsonApiRootSerialization().serializeRoot(homeSerializer, home);
		String asString = serialized.toString();
		System.out.println("SLZD: \n" + asString);
		assertEquals("{home={id=100, name=No place like, humans=[1, 2]}, humans=[{id=1, name=Jane}, {id=2, name=John}]}", asString);
	}
	
}
