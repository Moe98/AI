package data;

public class Soldier {
	private Location location;
	private int initalDamage;

	public Soldier(Location location, int initalDamage) {
		super();
		this.location = location;
		this.initalDamage = initalDamage;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getInitalDamage() {
		return initalDamage;
	}

	public void setInitalDamage(int initalDamage) {
		this.initalDamage = initalDamage;
	}

	@Override
	public String toString() {
		return "Soldier [location=" + location + ", initalDamage=" + initalDamage + "]";
	}

}
