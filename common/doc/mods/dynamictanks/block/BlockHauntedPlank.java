package doc.mods.dynamictanks.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;

public class BlockHauntedPlank extends Block {

	public BlockHauntedPlank(int par1) {
		super(par1, Material.wood);
		setHardness(1.0F);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setUnlocalizedName("dynamictanks.blocks.blockHauntedPlank");
		setStepSound(soundWoodFootstep);
	}

	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) { 
		this.blockIcon = par1IconRegister.registerIcon("dynamictanks:hauntedWood_Planks");
	}
	
}
