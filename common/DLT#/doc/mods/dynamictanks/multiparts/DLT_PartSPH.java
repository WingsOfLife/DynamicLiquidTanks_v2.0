package doc.mods.dynamictanks.multiparts;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.packet.PacketCustom.IServerPacketHandler;
import codechicken.multipart.minecraft.EventHandler;
import codechicken.multipart.minecraft.MinecraftMultipartMod;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;

public class DLT_PartSPH implements IServerPacketHandler {

	public static Object channel = DynamicLiquidTanksCore.instance;

	@Override
	public void handlePacket(PacketCustom packet, NetServerHandler nethandler, EntityPlayerMP sender)
	{
		switch(packet.getType())
		{
		case 1:
			EventHandler.place(sender, sender.worldObj);
			break;
		}
	}

}
