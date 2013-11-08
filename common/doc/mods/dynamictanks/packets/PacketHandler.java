package doc.mods.dynamictanks.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import doc.mods.dynamictanks.helpers.ItemHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class PacketHandler implements IPacketHandler {

	public class PacketIDs {
		public static final int dropItem = 0;
		public static final int spotClick = 1;
		public static final int camo = 2;
		public static final int camoMeta = 3;
		public static final int extractIndex = 4;
		public static final int dyeColorSet = 5;
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));	

		int id, x, y, z;
		float value, itemID, meta;
		double xAdd, yAdd, zAdd;

		try {
			id = data.readInt();
			value = data.readFloat();
			itemID = data.readFloat();
			meta = data.readFloat();
			xAdd = data.readDouble();
			yAdd = data.readDouble();
			zAdd = data.readDouble();
			x = data.readInt();
			y = data.readInt();
			z = data.readInt();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/*
		 * Handler Packets
		 */
		
		World world = ((EntityPlayerMP) player).worldObj;
		TileEntity tileAtLoc = world.getBlockTileEntity(x, y, z);
		
		if (id == PacketHandler.PacketIDs.dropItem) {
			ItemStack itemStack = new ItemStack((int)itemID, (int)value, (int)meta);
			if (!world.isRemote)
				ItemHelper.spawnItem(itemStack, world, x, y, z);
			((UpgradeTileEntity) tileAtLoc).setInventorySlotContents((int) xAdd, null);
		}
		else if (id == PacketHandler.PacketIDs.spotClick) {
			ItemStack itemStack = itemID != -1 ? new ItemStack((int)itemID, (int)value, (int)meta) : null;
			((UpgradeTileEntity) tileAtLoc).setSlotViaClick(xAdd, yAdd, zAdd, (UpgradeTileEntity)tileAtLoc, (EntityPlayer) player, itemStack);
		} 
		else if (id == PacketHandler.PacketIDs.camo)
			((ControllerTileEntity) tileAtLoc).setCamo((int) value);
		else if (id == PacketHandler.PacketIDs.camoMeta)
			((ControllerTileEntity) tileAtLoc).setCamo((int) value, (int) meta); 
		else if (id == PacketHandler.PacketIDs.extractIndex)
			((ControllerTileEntity) tileAtLoc).setLiquidIndex((int) value);
		else if (id == PacketHandler.PacketIDs.dyeColorSet)
			((ControllerTileEntity) tileAtLoc).setDyeColor((int) value);
		
		world.markBlockForUpdate(x, y, z);
		world.markBlockForRenderUpdate(x, y, z);
	}

	public static void sendPacketWithInt(int id, float value, float itemID, float meta, double xAdd, double yAdd, double zAdd, int x, int y, int z) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(id);
			outputStream.writeFloat(value);
			outputStream.writeFloat(itemID);
			outputStream.writeFloat(meta);
			outputStream.writeDouble(xAdd);
			outputStream.writeDouble(yAdd);
			outputStream.writeDouble(zAdd);
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "dynamictanks";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToServer(packet);
	}
}

