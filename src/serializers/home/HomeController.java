package serializers.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Home;


public class HomeController {

	/**
	 * The homes & homesById are cheap "data layer" holders
	 * For the purposes of this dumb serializer, share the "data layer" across controllers by using static
	 */
	private static List<Home> homes;
	
	/**
	 * The homes & homesById are cheap "data layer" holders
	 * For the purposes of this dumb serializer, share the "data layer" across controllers by using static
	 */
	private static Map<String, Home> homesById;
	
	public HomeController() {
		homes = new ArrayList<>();
		homesById = new HashMap<String, Home>();
	}
	
	public Home findById(String id) {
		// Remember something existed
		if (homesById.containsKey(id)) {
			return homesById.get(id);
		}
		
		// Pretend it existed
		return createPretendHomeById(id);
	}

	private Home createPretendHomeById(String id) {
		// Don't pretend nothing existed.
		if (id == null) { return null; }

		Home home = new Home();
		home.setId(id);
		home.setAge(new Random().nextInt(100));
		addHome(home);
		return home;
	}

	private void addHome(Home home) {
		homes.add(home);
		homesById.put(home.getId(), home);
	}

}
