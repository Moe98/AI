package data;

import java.util.Objects;

public class Location {
	private int x, y;

	public Location(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public static Location getNewLocation(Location oldLocation, String move) {
		int x = oldLocation.getX();
		int y = oldLocation.getY();
		switch (move) {
		case "UP":
			return new Location(x - 1, y);
		case "DOWN":
			return new Location(x + 1, y);
		case "LEFT":
			return new Location(x, y - 1);
		case "RIGHT":
			return new Location(x, y + 1);
		default:
			return new Location(x, y);
		}
	}

	public static boolean locationInBounds(Location location, int height, int width) {
		return location.x >= 0 && location.x < height && location.y >= 0 && location.y < width;
	}

	public static int getManhattanDistance(Location a, Location b) {
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}

	@Override
	public boolean equals(Object obj) {
		Location location = (Location) obj;
		return this.x == location.x && this.y == location.y;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + "]";
	}

}
