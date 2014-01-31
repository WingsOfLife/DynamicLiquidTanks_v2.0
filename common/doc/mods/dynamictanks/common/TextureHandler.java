package doc.mods.dynamictanks.common;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.Fluids.FluidManager;

public class TextureHandler
{
    @ForgeSubscribe
    @SideOnly(Side.CLIENT)
    public void postStitch(TextureStitchEvent.Post event)
    {
        if (event.map.textureType == 0 && ModConfig.miscBoolean.enableLiquids)
        {
            FluidManager.omniFluid.setIcons(FluidManager.omniBlock.getBlockTextureFromSide(0), FluidManager.omniBlock.getBlockTextureFromSide(1));
            FluidManager.clenseFluid.setIcons(FluidManager.clenseBlock.getBlockTextureFromSide(0), FluidManager.clenseBlock.getBlockTextureFromSide(1));
            FluidManager.potionFluid.setIcons(FluidManager.potionBlock.getBlockTextureFromSide(0), FluidManager.potionBlock.getBlockTextureFromSide(1));
            FluidManager.tntFluid.setIcons(FluidManager.tntBlock.getBlockTextureFromSide(0), FluidManager.tntBlock.getBlockTextureFromSide(1));
            FluidManager.regenFluid.setIcons(FluidManager.regenBlock.getBlockTextureFromSide(0), FluidManager.regenBlock.getBlockTextureFromSide(1));
            FluidManager.swiftFluid.setIcons(FluidManager.swiftBlock.getBlockTextureFromSide(0), FluidManager.swiftBlock.getBlockTextureFromSide(1));
            FluidManager.fireFluid.setIcons(FluidManager.fireBlock.getBlockTextureFromSide(0), FluidManager.fireBlock.getBlockTextureFromSide(1));
            FluidManager.poisonFluid.setIcons(FluidManager.poisonBlock.getBlockTextureFromSide(0), FluidManager.poisonBlock.getBlockTextureFromSide(1));
            FluidManager.nightFluid.setIcons(FluidManager.nightBlock.getBlockTextureFromSide(0), FluidManager.nightBlock.getBlockTextureFromSide(1));
            FluidManager.weakFluid.setIcons(FluidManager.weakBlock.getBlockTextureFromSide(0), FluidManager.weakBlock.getBlockTextureFromSide(1));
            FluidManager.strengthFluid.setIcons(FluidManager.strengthBlock.getBlockTextureFromSide(0), FluidManager.strengthBlock.getBlockTextureFromSide(1));
            FluidManager.slowFluid.setIcons(FluidManager.slowBlock.getBlockTextureFromSide(0), FluidManager.slowBlock.getBlockTextureFromSide(1));
            FluidManager.harmingFluid.setIcons(FluidManager.harmingBlock.getBlockTextureFromSide(0), FluidManager.harmingBlock.getBlockTextureFromSide(1));
            FluidManager.waterFluid.setIcons(FluidManager.waterBlock.getBlockTextureFromSide(0), FluidManager.waterBlock.getBlockTextureFromSide(1));
            FluidManager.invisFluid.setIcons(FluidManager.invisBlock.getBlockTextureFromSide(0), FluidManager.invisBlock.getBlockTextureFromSide(1));
            FluidManager.healingFluid.setIcons(FluidManager.healingBlock.getBlockTextureFromSide(0), FluidManager.healingBlock.getBlockTextureFromSide(1));
        }
    }
}
