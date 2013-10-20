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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.helpers.ItemHelper;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class PacketHandler implements IPacketHandler {

	public class PacketIDs {
		public static final int dropItem = 0;
		public static final int spotClick = 1;
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
		}
		else if (id == PacketHandler.PacketIDs.spotClick) {
			ItemStack itemStack = new ItemStack((int)itemID, (int)value, (int)meta);
			((UpgradeTileEntity) tileAtLoc).setSlotViaClick(xAdd, yAdd, zAdd, (UpgradeTileEntity)tileAtLoc, (EntityPlayer) player, itemStack);
		}
	}

	@SideOnly(Side.CLIENT)
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
		packet.channel = "dynamicTanks";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToServer(packet);
	}
}

