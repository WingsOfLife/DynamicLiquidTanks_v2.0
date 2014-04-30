package doc.mods.dynamictanks.multiparts;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import codechicken.lib.lighting.LazyLightMatrix;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.IHollowConnect;
import codechicken.multipart.minecraft.IPartMeta;
import codechicken.multipart.minecraft.McBlockPart;
import codechicken.multipart.minecraft.McSidedMetaPart;
import codechicken.multipart.minecraft.PartMetaAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.block.BlockPiping;

public class PipingPart extends McSidedMetaPart {
	
	public static BlockPiping piping = (BlockPiping) BlockManager.BlockPiping;
	
	public PipingPart(int meta) {
		
		super(meta);
	}
	
	public PipingPart() {
	}

	@Override
	public Block getBlock() {
		
		return piping;
	}

	@Override
	public Cuboid6 getBounds() {
		
		return new Cuboid6(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);
	}

	@Override
	public String getType() {
		
		return "dlt_piping";
	}
	
	public static McBlockPart placement(World world, BlockCoord pos, int side) {       
        
        return new PipingPart();
    }
	
	@Override
	public Iterable<Cuboid6> getCollisionBoxes() {
		
		ArrayList<Cuboid6> boxes = new ArrayList<Cuboid6>();
		boxes.add(getBounds());

		if(tile() != null)
		{
			int connections = 5;

			for(int i = 0; i < 6; ++i)
			{
				if((connections & (1 << i)) != 0)
				{
					switch(i)
					{
					case 0:
						boxes.add(new Cuboid6(0.25, 0.0, 0.25, 0.75, 0.25, 0.75));
						break;
					case 1:
						boxes.add(new Cuboid6(0.25f, 0.75f, 0.25f, 0.75f, 1.0f, 0.75f));
						break;
					case 2:
						boxes.add(new Cuboid6(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.25f));
						break;
					case 3:
						boxes.add(new Cuboid6(0.25f, 0.25f, 0.75f, 0.75f, 0.75f, 1.0f));
						break;
					case 4:
						boxes.add(new Cuboid6(0.0f, 0.25f, 0.25f, 0.25f, 0.75f, 0.75f));
						break;
					case 5:
						boxes.add(new Cuboid6(0.75f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f));
						break;
					}
				}
			}
		}

		return boxes;
	}
	
	@Override
    public boolean canStay() {
		
		return true;
	}
	
	@Override
    public Icon getBreakingIcon(Object subPart, int side) {
		
        return piping.getBlockTextureFromSide(0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBrokenIcon(int side) {
    	
    	return piping.getBlockTextureFromSide(0);
    }
    
    @Override
    public void renderStatic(Vector3 pos, LazyLightMatrix olm, int pass) {
    	
        if(pass == 0)
            new RenderBlocks(new PartMetaAccess(this)).renderBlockByRenderType(getBlock(), x(), y(), z());
    }

	@Override
	public World getWorld() {

		return world();
	}

	@Override
	public int getBlockId() {

		return getBlock().blockID;
	}

	@Override
	public BlockCoord getPos() {

		return new BlockCoord(tile());
	}

	@Override
	public int sideForMeta(int meta) {
		
		return -1;
	}
}
