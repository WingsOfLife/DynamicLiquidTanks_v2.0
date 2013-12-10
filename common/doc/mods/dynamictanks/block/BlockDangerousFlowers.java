package doc.mods.dynamictanks.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.common.CommonProxy;

public class BlockDangerousFlowers extends Block implements IPlantable
{
    public enum BlockType
    {
        FLAMING_LILLY(0), THORNY_ROSE(1), CREEPYGRASS(2);

        private final int metadata;

        BlockType(int metadata)
        {
            this.metadata = metadata;
        }

        public int metadata()
        {
            return metadata;
        }
    }

    private Icon flaminglilly;
    private Icon thornyrose;
    private Icon creepygrass;

    public BlockDangerousFlowers(int id, int index, Material material)
    {
        super(id, material);
        setTickRandomly(true);
        setLightValue(5);
        final float var4 = 0.2F;
        setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        flaminglilly = iconRegister.registerIcon("dynamictanks:flamingLilly");
        thornyrose = iconRegister.registerIcon("dynamictanks:thornyRose");
        creepygrass = iconRegister.registerIcon("dynamictanks:CreepyGrass");
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && canThisPlantGrowOnThisBlockID(world.getBlockId(x, y - 1, z));
    }

    private boolean canThisPlantGrowOnThisBlockID(int id)
    {
        return id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.tilledField.blockID || id == Block.sand.blockID || id == BlockManager.BlockHD.blockID;
    }

    private void checkFlowerChange(World world, int x, int y, int z)
    {
        if (!canBlockStay(world, x, y, z))
        {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    @Override
    public Icon getIcon(int side, int metadata)
    {
        //if (metadata > 7) metadata = 7;
        //return super.getBlockTextureFromSideAndMetadata(side, metadata) + metadata;
        switch (metadata)
        {
            case 0:
                return flaminglilly;

            case 1:
                return thornyrose;

            case 2:
                return creepygrass;

            default:
                return flaminglilly;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return EnumPlantType.Plains;
    }

    @Override
    public int getRenderType()
    {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1, y + maxY, z + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs tab, List itemList)
    {
        for (final BlockType type : BlockType.values())
        {
            itemList.add(new ItemStack(this, 1, type.metadata()));
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id)
    {
        checkFlowerChange(world, x, y, z);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        checkFlowerChange(world, x, y, z);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 0)
        {
            entity.setFire(2);
        }
        else if (meta == 1)
        {
            entity.attackEntityFrom(DamageSource.magic, 1);
        }
    }
}
