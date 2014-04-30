package doc.mods.dynamictanks.multiparts;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.minecraft.ButtonPart;
import codechicken.multipart.minecraft.LeverPart;
import codechicken.multipart.minecraft.RedstoneTorchPart;
import codechicken.multipart.minecraft.TorchPart;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import doc.mods.dynamictanks.block.BlockManager;

public class Content implements IPartFactory, IPartConverter {

	@Override
    public TMultiPart createPart(String name, boolean client)
    {
        if(name.equals("dlt_piping")) return new PipingPart();
        
        return null;
    }
    
    public void init()
    {
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerParts(this, new String[]{
                "dlt_piping"
            });
    }

    @Override
    public boolean canConvert(int blockID)
    {
        return blockID == BlockManager.BlockPiping.blockID;
    }

    @Override
    public TMultiPart convert(World world, BlockCoord pos)
    {
        int id = world.getBlockId(pos.x, pos.y, pos.z);
        int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
        
        if(id == BlockManager.BlockPiping.blockID)
            return new PipingPart();
        
        return null;
    }
}
