package doc.mods.dynamictanks.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class RenderTank implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        RendererHelper.renderStandardInvBlock(renderer, block, metadata);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        TileEntity tankTile = world.getBlockTileEntity(x, y, z);
        renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        renderer.renderStandardBlock(block, x, y, z);

        if (tankTile instanceof TankTileEntity && !((TankTileEntity) tankTile).hasCamo())
        {
            if (block.shouldSideBeRendered(world, x, y - 1, z, 0))
            {
                renderNegYFace(world, x, y, z, block, modelId, renderer);
            }

            if (block.shouldSideBeRendered(world, x, y + 1, z, 1))
            {
                renderPosYFace(world, x, y, z, block, modelId, renderer);
            }

            if (block.shouldSideBeRendered(world, x, y, z - 1, 2))
            {
                renderNegZFace(world, x, y, z, block, modelId, renderer);
            }

            if (block.shouldSideBeRendered(world, x, y, z + 1, 3))
            {
                renderPosZFace(world, x, y, z, block, modelId, renderer);
            }

            if (block.shouldSideBeRendered(world, x - 1, y, z, 4))
            {
                renderNegXFace(world, x, y, z, block, modelId, renderer);
            }

            if (block.shouldSideBeRendered(world, x + 1, y, z, 5))
            {
                renderPosXFace(world, x, y, z, block, modelId, renderer);
            }
        }

        renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TankTileEntity)
        {
            TankTileEntity tank = ((TankTileEntity) tile);

            if (tank.hasController() && FluidHelper.hasLiquid(tank.getControllerTE()))
            {
                FluidStack liquid = tank.getControllerTE().getAllLiquids().get(tank.getControllerTE().getLiquidIndex()).getFluid();

                if (liquid != null && ClientProxy.renderPass == 1 && liquid.amount > 0)
                {
                    float amnt = tank.amntToRender();

                    if (amnt > 0)
                    {
                        renderer.setRenderBounds(
                            tank.worldObj.getBlockId(x - 1, y, z) == 0 ? 0.001
                            : 0.00,
                            tank.worldObj.getBlockId(x, y - 1, z) == 0 ? 0.001
                            : 0.00,
                            tank.worldObj.getBlockId(x, y, z - 1) == 0 ? 0.001
                            : 0.00,
                            tank.worldObj.getBlockId(x + 1, y, z) == 0 ? 0.999
                            : 1.0,
                            amnt <= 0 ? 0.0f : amnt,
                            tank.worldObj.getBlockId(x, y, z + 1) == 0 ? 0.999
                            : 1.0);
                        Fluid fluid = liquid.getFluid();

                        if (fluid.canBePlacedInWorld())
                        {
                            BlockSkinRenderHelper.renderMetadataBlock(Block.blocksList[fluid.getBlockID()], 0, x, y, z, renderer, world);
                        }
                        else
                        {
                            BlockSkinRenderHelper.renderLiquidBlock(fluid.getStillIcon(), fluid.getFlowingIcon(), x, y, z, renderer, world);
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ClientProxy.tankRender;
    }

    public void renderNegZFace(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world.getBlockId(x - 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y - 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 0, 0.065, 0.065, 0.065);
                renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x - 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y + 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 1 - 0.065, 0, 0.065, 1, 1);
                renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y - 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1 - 0.065, 0, 0, 1, 0.065, 1);
                renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y + 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1 - 0.065, 1 - 0.065, 0, 1, 1, 1);
                renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }
    }

    public void renderPosZFace(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world.getBlockId(x - 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y - 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 0, 0.065, 0.065, 1);
                renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x - 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y + 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 1 - 0.065, 0, 0.065, 1, 1);
                renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y - 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1 - 0.065, 0, 0, 1, 0.065, 1);
                renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y + 1, z) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1 - 0.065, 1 - 0.065, 0, 1, 1, 1);
                renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }
    }

    public void renderNegXFace(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y - 1, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 0, 0.065, 0.065, 0.065);
                renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y + 1, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 1 - 0.065, 0, 0.065, 1, 0.065);
                renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y - 1, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 1 - 0.065, 0.065, 0.065, 1);
                renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y + 1, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 1 - 0.065, 1 - 0.065, 0.065, 1, 1);
                renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }
    }

    public void renderPosXFace(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y - 1, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 0, 1, 0.065, 0.065);
                renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y + 1, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 1 - 0.065, 0, 1, 1, 0.065);
                renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y - 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y - 1, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 1 - 0.065, 1, 0.065, 1);
                renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x, y + 1, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x, y + 1, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 1 - 0.065, 1 - 0.065, 1, 1, 1);
                renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }
    }

    public void renderNegYFace(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x - 1, y, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 0, 0.065, 1, 0.065);
                renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1, 0, 0, 1 - 0.065, 1, 0.065);
                renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x - 1, y , z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 1 - 0.065, 0.065, 1, 1);
                renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1, 0, 1 - 0.065, 1 - 0.065, 1, 1);
                renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block));
            }
        }
    }

    public void renderPosYFace(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x - 1, y, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 0, 0.065, 1, 0.065);
                renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z - 1) == BlockManager.BlockTank.blockID && world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y, z - 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1, 0, 0, 1 - 0.065, 1, 0.065);
                renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x - 1, y , z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x - 1, y, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(0, 0, 1 - 0.065, 0.065, 1, 1);
                renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }

        if (world.getBlockId(x, y, z + 1) == BlockManager.BlockTank.blockID && world.getBlockId(x + 1, y, z) == BlockManager.BlockTank.blockID)
        {
            if (world.getBlockId(x + 1, y, z + 1) != BlockManager.BlockTank.blockID)
            {
                renderer.setRenderBounds(1, 0, 1 - 0.065, 1 - 0.065, 1, 1);
                renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block));
            }
        }
    }
}
