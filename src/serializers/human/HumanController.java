package serializers.human;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Human;


public class HumanController {

	List<Human> humans;
	Map<String, Human> humansById;
	
	public HumanController() {
		humans = new ArrayList<>();
		humansById = new HashMap<String, Human>();
	}
	
	public Human findById(String id) {
		// Remember something existed
		if (humansById.containsKey(id)) {
			return humansById.get(id);
		}
		
		// Pretend it existed
		return createPretendHumanById(id);
	}

	private Human createPretendHumanById(String id) {
		// Don't pretend nothing existed.
		if (id == null) { return null; }
		
		Human human = new Human();
		human.setId(id);
		human.setAge(new Random().nextInt(100));
		addHuman(human);
		return human;
	}

	private void addHuman(Human human) {
		humans.add(human);
		humansById.put(human.getId(), human);
	}
	
}
