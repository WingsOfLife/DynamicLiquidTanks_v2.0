package doc.mods.dynamictanks.potionrecipe;

import java.util.LinkedList;

import net.minecraft.block.Block;
import doc.mods.dynamictanks.Fluids.FluidManager;

public class PotionInverse
{
    public static final Block[] regen =    { FluidManager.regenBlock, FluidManager.poisonBlock };
    public static final Block[] swift =    { FluidManager.swiftBlock, FluidManager.slowBlock };
    public static final Block[] fire =     { FluidManager.fireBlock, FluidManager.waterBlock };
    public static final Block[] healing =  { FluidManager.healingBlock, FluidManager.harmingBlock };
    public static final Block[] strength = { FluidManager.strengthBlock, FluidManager.weakBlock };

    public static final Block[] water =    { FluidManager.waterBlock, Block.waterStill };
    public static final Block[] invis =    { FluidManager.invisBlock, Block.waterStill };
    public static final Block[] night =    { FluidManager.nightBlock, Block.waterStill };

    public static final Block[] posion =   reverse(regen);
    public static final Block[] weak =     reverse(strength);
    public static final Block[] slow =     reverse(swift);
    public static final Block[] harming =  reverse(healing);

    public static LinkedList<Block[]> allTypes = new LinkedList<Block[]>();

    private static void populateTypes()
    {
        allTypes.add(regen);
        allTypes.add(swift);
        allTypes.add(fire);
        allTypes.add(healing);
        allTypes.add(strength);
        allTypes.add(water);
        allTypes.add(invis);
        allTypes.add(night);
        allTypes.add(posion);
        allTypes.add(weak);
        allTypes.add(slow);
        allTypes.add(harming);
    }

    public static int getInverse(int blockId)
    {
        populateTypes();

        for (Block[] block : allTypes)
        {
            if (block != null && block.length > 0)
                if (block[0].blockID == blockId)
                {
                    return block[1].blockID;
                }
        }

        return Block.waterStill.blockID;
    }

    public static Block[] reverse(Block[] array)
    {
        if (array == null)
        {
            return array;
        }

        int i = 0;
        int j = array.length - 1;
        Block tmp;

        while (j > i)
        {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }

        return array;
    }
}
