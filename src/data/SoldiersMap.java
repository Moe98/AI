package data;

public class SoldiersMap {

	private int numOfSoldiers;
	private int bitmap;

	public SoldiersMap(int numOfSoldiers) {
		this.numOfSoldiers = numOfSoldiers;
		this.bitmap = 0;
	}

	public boolean isSoldierRescued(int soldierIdx) {
		return (this.bitmap & (1 << soldierIdx)) != 0;
	}

	public void pickupSoldier(int soldierIdx) {
		this.bitmap |= (1 << soldierIdx);
	}

	public boolean isAllRescued() {
		return this.bitmap == (1 << numOfSoldiers) - 1;
	}

	public int getNumOfSoldiers() {
		return numOfSoldiers;
	}

	public void setNumOfSoldiers(int numOfSoldiers) {
		this.numOfSoldiers = numOfSoldiers;
	}

	public int getBitmap() {
		return bitmap;
	}

	public void setBitmap(int bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "SoldiersMap [numOfSoldiers=" + numOfSoldiers + ", bitmap=" + Integer.toBinaryString(bitmap) + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return numOfSoldiers == ((SoldiersMap) obj).getNumOfSoldiers() && bitmap == ((SoldiersMap) obj).getBitmap();
	}

}
