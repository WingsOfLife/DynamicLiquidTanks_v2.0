package doc.mods.dynamictanks.tileentity;

import net.minecraft.tileentity.TileEntity;

public class CountableTileEntity extends TileEntity {

	protected int tickCount = 0;
	protected int maxTickCount = 100; //number of seconds in ticks (secs * 20)
	
	protected int tickCountSec = 0;
	protected int maxTickCountSec = 100; //number of seconds in ticks (secs * 20)
	
	public CountableTileEntity() {
	}
	
	public CountableTileEntity(int maxTickCount) {
		this.maxTickCount = maxTickCount;
	}
	
	public boolean countMet() {
		if (tickCount >= maxTickCount)
			return true;
		return false;
	}
	
	public boolean countMetSec() {
		if (tickCountSec >= maxTickCountSec)
			return true;
		return false;
	}

	public void doCount() {
		if (tickCount >= maxTickCount)
			tickCount = 0;
		tickCount++;
	}	
	
	public void doCountSec() {
		if (tickCountSec >= maxTickCountSec)
			tickCountSec = 0;
		tickCountSec++;
	}
}
