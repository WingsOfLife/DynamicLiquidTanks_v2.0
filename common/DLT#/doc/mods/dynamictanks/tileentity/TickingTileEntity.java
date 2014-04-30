package doc.mods.dynamictanks.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TickingTileEntity extends TileEntity
{
    public int tickCount = 0;
    protected int maxTickCount = 100; //number of seconds in ticks (secs * 20)

    public int tickCountSec = 0;
    protected int maxTickCountSec = 100; //number of seconds in ticks (secs * 20)

    public TickingTileEntity()
    {
    }

    public TickingTileEntity(int maxTickCount)
    {
        this.maxTickCount = maxTickCount;
    }

    public boolean countMet()
    {
        if (tickCount >= maxTickCount)
        {
            return true;
        }

        return false;
    }

    public boolean countMetSec()
    {
        if (tickCountSec >= maxTickCountSec)
        {
            return true;
        }

        return false;
    }

    public void doCount()
    {
        if (tickCount >= maxTickCount)
        {
            tickCount = 0;
        }

        tickCount++;
    }

    public void doCountSec()
    {
        if (tickCountSec >= maxTickCountSec)
        {
            tickCountSec = 0;
        }

        tickCountSec++;
    }
    
    public int getTickCount() {
    	
    	return maxTickCount;
    }
}
