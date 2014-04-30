package doc.mods.dynamictanks.multiparts;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;

public class MultiPartRenderHelper {

	public static TileMultipart getMultipartTile(IBlockAccess access, BlockCoord pos) {
		
        TileEntity te = access.getBlockTileEntity(pos.x, pos.y, pos.z);
        return te instanceof TileMultipart ? (TileMultipart) te : null;
    }

    public static TMultiPart getMultiPart(IBlockAccess w, BlockCoord bc, int part) {
    	
        TileMultipart t = getMultipartTile(w, bc);
        if (t != null)
            return t.partMap(part);

        return null;
    }

}
