package doc.mods.dynamictanks.UP;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGenerator extends BlockContainer {

	public BlockGenerator(int par1) {
		super(par1, Material.iron);
		setHardness(2.0F);
        setUnlocalizedName("dynamictanks.block.generator");
        setBlockBounds(0.2f, 0f, 0.2f, 0.8f, 1.6f, 0.8f);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new GeneratorTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if (!par1World.isRemote) {
			GeneratorTileEntity genTE = (GeneratorTileEntity) par1World.getBlockTileEntity(par2, par3, par4);
			par5EntityPlayer.addChatMessage("Power Stored: " + genTE.storage.getEnergyStored() + " RF");
			par5EntityPlayer.addChatMessage("Fluid Stored: " + genTE.tank.getFluidAmount() + " mB");
		}
        return true;
    }
	
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType()
    {
        return ClientProxy.geneRender;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;
        return true;
    }

}
