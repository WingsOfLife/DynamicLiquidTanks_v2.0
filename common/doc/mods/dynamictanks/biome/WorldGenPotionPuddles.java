package doc.mods.dynamictanks.biome;

import java.util.Random;

import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPotionPuddles extends WorldGenerator
{
	/**
	 * The Block ID that the generator is allowed to replace while generating the terrain.
	 */
	private int replaceID;

	public WorldGenPotionPuddles(int par1)
	{
		replaceID = par1;
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		if (par1World.isAirBlock(par3, par4, par5) && par1World.getBlockId(par3, par4 - 1, par5) == replaceID)
		{
			int var6 = par2Random.nextInt(1) + par2Random.nextInt(1) + 1; //depth
			int var7 = par2Random.nextInt(1) + 2; // height
			int var8;
			int var9;
			int var10;
			int var11;

			for (var8 = par3 - var7; var8 <= par3 + var7; ++var8)
			{
				for (var9 = par5 - var7; var9 <= par5 + var7; ++var9)
				{
					var10 = var8 - par3;
					var11 = var9 - par5;

					if (var10 * var10 + var11 * var11 <= var7 * var7 + 1 && par1World.getBlockId(var8, par4 - 1, var9) != replaceID)
						return false;
				}
			}

			int singleType = FluidManager.blockType.get(par2Random.nextInt(FluidManager.blockType.size() - 1)).blockID;
			
			for (var8 = par4; var8 > par4 - var6 && var8 > 20; --var8)
			{
				for (var9 = par3 - var7; var9 <= par3 + var7; ++var9)
				{
					for (var10 = par5 - var7; var10 <= par5 + var7; ++var10)
					{
						var11 = var9 - par3;
						int var12 = var10 - par5;
						
						if (var11 * var11 + var12 * var12 <= var7 * var7 + 1)
						{
							par1World.setBlock(var9, var8 - 1, var10, singleType);
						}
					}
				}
			}

			return true;
		} else
			return false;
	}
}
