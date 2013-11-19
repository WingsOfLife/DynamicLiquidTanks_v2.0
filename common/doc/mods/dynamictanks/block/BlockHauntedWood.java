package doc.mods.dynamictanks.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;

public class BlockHauntedWood extends Block {

	@SideOnly(Side.CLIENT)
	Icon[] sideIcons = new Icon[3];
	@SideOnly(Side.CLIENT)
	Icon topIcon;
	
	public BlockHauntedWood(int par1) {
		super(par1, Material.wood);
		setHardness(1.0f);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setUnlocalizedName("dynamictanks.blocks.blockHauntedWood");
		setStepSound(soundWoodFootstep);
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return par1 == 1 || par1 == 0 ? topIcon : sideIcons[0];
	}

	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if (par5 == 0 || par5 == 1)
			return topIcon;
		else if (meta != 0) {
			Random rnd = new Random();
			return sideIcons[rnd.nextInt(sideIcons.length)];
		} else
			return sideIcons[0];
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) { 
		sideIcons[0] = par1IconRegister.registerIcon("dynamictanks:hauntedWood");
		sideIcons[1] = par1IconRegister.registerIcon("dynamictanks:hauntedWood_Face");
		sideIcons[2] = par1IconRegister.registerIcon("dynamictanks:hauntedWood_Hollow");
		
		topIcon = par1IconRegister.registerIcon("dynamictanks:hauntedWood_Top");
	}

}
