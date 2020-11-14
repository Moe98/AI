package core;

import data.Location;
import data.SoldiersMap;

public class State {
	private Location location;
	private int truckLoad;
	private SoldiersMap soldiersMap;

	public State(Location location, int truckLoad, SoldiersMap soldiersMap) {
		super();
		this.location = location;
		this.truckLoad = truckLoad;
		this.soldiersMap = soldiersMap;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getTruckLoad() {
		return truckLoad;
	}

	public void setTruckLoad(int truckLoad) {
		this.truckLoad = truckLoad;
	}

	public SoldiersMap getSoldiers() {
		return soldiersMap;
	}

	public void setSoldiers(SoldiersMap soldiersMap) {
		this.soldiersMap = soldiersMap;
	}

	@Override
	public int hashCode() {
		StringBuilder code = new StringBuilder();
		code.append(location.getX()).append(",").append(location.getY());
		code.append("#").append(truckLoad);
		code.append("#").append(soldiersMap.getBitmap());
		return code.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return location.equals(((State) obj).getLocation()) && truckLoad == ((State) obj).getTruckLoad()
				&& soldiersMap.equals(((State) obj).getSoldiers());
	}

	@Override
	public String toString() {
		return "State [location=" + location + ", truckLoad=" + truckLoad + ", soldiersMap=" + soldiersMap.toString()
				+ "]";
	}

}
