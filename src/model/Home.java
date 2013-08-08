package model;
import java.util.ArrayList;
import java.util.List;



public class Home {

	private String id;
	private String name;
	private List<Human> humans;

	public Home() {
	}

	public Home(String id, String name) {
		this.id = id;
		this.name = name;
		humans = new ArrayList<Human>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Human> getHumans() {
		return humans;
	}
	public void setHumans(List<Human> humans) {
		this.humans = humans;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
