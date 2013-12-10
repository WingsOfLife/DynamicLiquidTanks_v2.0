package doc.mods.dynamictanks.apiMe;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.nbt.NBTTagCompound;

public class PotionRecipe
{
    public static enum Types
    {
        Regeneration("Regeneration"),
        Swiftness("Swiftness"),
        Fire_Resistance("Fire_Resistance"),
        Poison("Poison"),
        Healing("Healing"),
        Night_Vision("Night_Vision"),
        Weakness("Weakness"),
        Strength("Strength"),
        Slowness("Slowness"),
        Harming("Harming"),
        Water_Breathing("Water_Breathing"),
        Invisibility("Invisibility");

        public final String name;

        private Types(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public static enum collisionType
    {
        Entity(0),
        Item(1),
        BlockItem(2);

        private final int value;

        private collisionType(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return this.value;
        }
    }

    /**
     * This is used to add or remove stability to the various liquid potions added by this mod.
     * This method should be called at the Init of your mod.
     *
     * @param type The type of potion you wish to effect. See the list "type" above.
     * @param objType The type of object that will be colliding with the liquid. See the list "collisionType" above.
     * @param positive This value determines if it is to add stability to the potion. False = Remove : True = Add.
     * @param value The amount of stability you wish to remove from the type chosen. But be bellow 1200 * 20
     * @param objId The id the corresponds to the objType, if it is an item give the itemId, block give blockId, entity give entityId
     */
    public static void addRecipe(Types type, collisionType objType, boolean positive, int value, int objId)
    {
        NBTTagCompound toSend = new NBTTagCompound();
        toSend.setString("Type", type.getName());
        toSend.setInteger("ObjType", objType.getValue());
        toSend.setBoolean("Positive", positive);
        toSend.setInteger("Value", value);
        toSend.setInteger("ObjId", objId);
        FMLInterModComms.sendMessage("dynamictanks", "addInversionRecipe", toSend);
    }
}
