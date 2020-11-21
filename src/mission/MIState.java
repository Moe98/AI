package mission;

import core.State;
import data.Location;
import data.SoldiersMap;

public class MIState extends State {
	private Location location;
	private int truckLoad;
	private SoldiersMap soldiersMap;
	private int steps;
	private int identifier;
	private static final int[] VALUE_LIMITS = { 225, 11, 1 << 10, 52 };

	public MIState(Location location, int truckLoad, SoldiersMap soldiersMap, int steps) {
		super();
		this.location = location;
		this.truckLoad = truckLoad;
		this.soldiersMap = soldiersMap;
		this.steps = steps;
		int[] encodedValues = { location.getX() * 15 + location.getY(), truckLoad, soldiersMap.getBitmap(), steps };
		this.identifier = calculateUniqueIdentifier(encodedValues);
	}

	private int calculateUniqueIdentifier(int[] values) {
		int cumulativeSize = VALUE_LIMITS[0];
		int id = values[0];
		for(int i = 1; i < values.length; i++) {
			id += cumulativeSize * values[i];
			cumulativeSize *= VALUE_LIMITS[i];
		}
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public int getTruckLoad() {
		return truckLoad;
	}

	public SoldiersMap getSoldiers() {
		return soldiersMap;
	}

	public int getSteps() {
		return steps;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(identifier);
	}

	@Override
	public boolean equals(Object obj) {
		return identifier == ((MIState)obj).identifier;
	}

	@Override
	public String toString() {
		return "MIState [location=" + location + ", truckLoad=" + truckLoad + ", soldiersMap=" + soldiersMap.toString()
				+ ", steps=" + steps + "]";
	}

}
