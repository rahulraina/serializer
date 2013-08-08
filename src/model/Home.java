package model;
import java.util.ArrayList;
import java.util.List;



public class Home {

	private String id;
	private String name;
	private List<Human> humans;
	private int age;
	
	public Home() {
		humans = new ArrayList<Human>();
	}
	
	public Home(String id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Home [id=" + id + ", name=" + name + ", humans(size)=" + humans.size()
				+ ", age=" + age + "]";
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private void addHuman(Human human) {
		if (human != null) {
			human.join(this);
		}
	}
	
}
