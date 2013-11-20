package doc.mods.dynamictanks.common;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import doc.mods.dynamictanks.Fluids.FluidManager;

public class TextureHandler {
	@ForgeSubscribe
	public void postStitch(TextureStitchEvent.Post event) {
		//FluidManager.potionFluid.setIcons(FluidManager.potionBlock.getBlockTextureFromSide(0), FluidManager.potionBlock.getBlockTextureFromSide(1));
		//FluidManager.regenFluid.setIcons(FluidManager.regenBlock.getBlockTextureFromSide(0), FluidManager.regenBlock.getBlockTextureFromSide(1));
	}	
}
