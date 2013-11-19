package doc.mods.dynamictanks.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import doc.mods.dynamictanks.Fluids.PotionTileEntity;

public class BucketHandler {

	public static BucketHandler INSTANCE = new BucketHandler();
	public Map<Block, ItemStack> buckets = new HashMap<Block, ItemStack>();

	private BucketHandler() {}

	@ForgeSubscribe
	public void onBucketFill(FillBucketEvent event) {

		ItemStack result = fillCustomBucket(event.world, event.target);

		if (result == null)
			return;

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

		int blockID = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);
		TileEntity potionTile = world.getBlockTileEntity(pos.blockX, pos.blockY, pos.blockZ);
		ItemStack bucket = buckets.get(Block.blocksList[blockID]);

		if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlock(pos.blockX, pos.blockY, pos.blockZ, 0);
			if (potionTile instanceof PotionTileEntity) {
				if (bucket.stackTagCompound == null) {
					bucket.setTagCompound(new NBTTagCompound());
				}
				float test = ((PotionTileEntity) potionTile).getExistance();
				bucket.stackTagCompound.setFloat("lengthExisted", ((PotionTileEntity) potionTile).getExistance());
			}
			return bucket;
		} else
			return null;

	}
}
