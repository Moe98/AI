package mission;

import core.State;
import data.Location;
import data.SoldiersMap;

public class MIState extends State {
	private Location location;
	private int truckLoad;
	private SoldiersMap soldiersMap;
	private int steps;

	public MIState(Location location, int truckLoad, SoldiersMap soldiersMap, int steps) {
		super();
		this.location = location;
		this.truckLoad = truckLoad;
		this.soldiersMap = soldiersMap;
		this.steps = steps;
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

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	@Override
	public int hashCode() {
		StringBuilder code = new StringBuilder();
		code.append(location.getX()).append(",").append(location.getY());
		code.append("#").append(truckLoad);
		code.append("#").append(soldiersMap.getBitmap());
		code.append("#").append(steps);
		return code.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return location.equals(((MIState) obj).getLocation()) && truckLoad == ((MIState) obj).getTruckLoad()
				&& soldiersMap.equals(((MIState) obj).getSoldiers()) && steps == ((MIState) obj).steps;
	}

	@Override
	public String toString() {
		return "MIState [location=" + location + ", truckLoad=" + truckLoad + ", soldiersMap=" + soldiersMap.toString()
				+ ", steps=" + steps + "]";
	}

}
