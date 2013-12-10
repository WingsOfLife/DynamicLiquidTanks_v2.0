package doc.mods.dynamictanks.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;

public class CPotionHelper
{
    protected int[] existanceMult = { 1, 3, 5, 10, 15, 20, 25, 30, 35, 40 };

    public static final int[] potions =
    {
        8193, 8194, 8195, 8196, 8197, 8198,
        8200, 8201, 8202, 8204, 8205, 8206,
        8225, 8226, 8228, 8229, 8233, 8236,
        8257, 8258, 8259, 8260, 8262, 8264,
        8265, 8266, 8269, 8270, 8289, 8290,
        8292, 8297
    };

    public static Block[] fluidBlocks =
    {
        FluidManager.regenBlock, FluidManager.swiftBlock, FluidManager.fireBlock,
        FluidManager.poisonBlock, FluidManager.healingBlock, FluidManager.nightBlock,
        FluidManager.weakBlock, FluidManager.strengthBlock, FluidManager.slowBlock,
        FluidManager.harmingBlock, FluidManager.waterBlock, FluidManager.invisBlock
    };

    public static final float ticksPerSec = 20;
    public static final float maxExistance = 1200 * ticksPerSec; //in seconds

    public static String nameFromMeta(int meta)
    {
        return new ItemStack(Item.potion, 1, meta + 1).getDisplayName();
    }

    public static int getDuration(int percent)
    {
        if (percent <= 100 && percent >= 90)
        {
            return 1;
        }
        else if (percent <= 89 && percent >= 80)
        {
            return 2;
        }
        else if (percent <= 79 && percent >= 70)
        {
            return 3;
        }
        else if (percent <= 69 && percent >= 60)
        {
            return 5;
        }
        else if (percent <= 59 && percent >= 50)
        {
            return 9;
        }
        else if (percent <= 49 && percent >= 40)
        {
            return 15;
        }
        else if (percent <= 39 && percent >= 30)
        {
            return 20;
        }
        else if (percent <= 29 && percent >= 20)
        {
            return 25;
        }
        else if (percent <= 19 && percent >= 10)
        {
            return 30;
        }

        return 40;
    }

    public static int showHealth(int percent)
    {
        return -1;
    }
}
