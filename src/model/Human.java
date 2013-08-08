package model;

public class Human {

	private String name;
	private int age;
	private Home home;
	private String id;
	
	public Human() {
	}
	
	public Human(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Human [name=" + name + ", age=" + age + ", home=" + home
				+ ", id=" + id + "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	public void join(Home home) {
		this.home = home;
		home.getHumans().add(this);
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
}
