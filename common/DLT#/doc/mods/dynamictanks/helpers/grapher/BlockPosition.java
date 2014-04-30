package doc.mods.dynamictanks.helpers.grapher;

public class BlockPosition {

	private int x;
	private int y;
	private int z;
	
	public BlockPosition(int x, int y, int z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int x() {
		
		return x;
	}
	
	public int y() {
		
		return y;
	}
	
	public int z() {
		
		return z;
	}
	
	public int[] coordSys() {
		
		return new int[] { x, y, z };
	}
	
	@Override
	public String toString() {
		
		return x + ", " + y + ", " + z;
	}
}
