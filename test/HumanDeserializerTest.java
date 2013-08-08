import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import model.Home;
import model.Human;

import org.junit.Before;
import org.junit.Test;

import serializers.DeserializingContext;
import serializers.human.HumanSerializer;


public class HumanDeserializerTest extends TestCase {

	HumanSerializer humanSerializer;
	Home home; 
	Human janeDoe; 
	Human johnDoe;
	
	@Before
	public void setup() {
		humanSerializer = new HumanSerializer();

		home = new Home("100", "No place like");
		janeDoe = new Human("1", "Jane"); janeDoe.join(home);
		johnDoe = new Human("2", "John"); johnDoe.join(home);
	}

	@Test
	public void testHumanDeserializer() {
		setup(); // befores aren't firing.
		
		// String inJson = "{homes=[{id=100, name=No place like}], human={id=1, home=100, name=Jane}}";
		Map<String, Object> inJson = new HashMap<String, Object>();

		Map<String, Object> homeJson = new HashMap<String, Object>();
		homeJson.put("id", "100");
		homeJson.put("name", "No place like");
		List<String> humanIds = new ArrayList<>();
		humanIds.add("1");
		homeJson.put("humans", humanIds);

		Map<String, Object> humanJson = new HashMap<>();
		humanJson.put("id", "1");
		humanJson.put("name", "Jane");
		humanJson.put("home", homeJson.get("id"));

		List<Map<String, Object>> homesJson = new ArrayList<Map<String,Object>>();
		homesJson.add(homeJson);
		
		inJson.put("homes", homesJson);
		inJson.put("human", humanJson);

		System.out.println("INJSON: " + inJson);
		
		DeserializingContext ctx = new DeserializingContext(inJson);

		Human human = humanSerializer.deserialize((Map<String, Object>) inJson.get("human"), ctx);
		System.out.println("GOT HUMAN: " + human);

	}
	
}
