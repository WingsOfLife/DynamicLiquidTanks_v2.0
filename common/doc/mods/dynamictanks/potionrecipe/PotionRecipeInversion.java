package doc.mods.dynamictanks.potionrecipe;

import java.util.ArrayList;

public class PotionRecipeInversion
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

    public static ArrayList<int[]> regenInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> swiftInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> fireInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> poisonInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> healingInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> nightInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> weakInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> strengthInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> slownessInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> harmingInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> waterInversionValues = new ArrayList<int[]>();
    public static ArrayList<int[]> invisInversionValues = new ArrayList<int[]>();

    public static boolean addInversionRecipe(Types type, collisionType objId, int id, int invValue)
    {
        if (id < 0 || invValue > CPotionHelper.maxExistance || invValue < 0)
        {
            return false;
        }

        switch (type)
        {
            case Fire_Resistance:
                fireInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Harming:
                harmingInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Healing:
                healingInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Invisibility:
                invisInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Night_Vision:
                nightInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Poison:
                poisonInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Regeneration:
                regenInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Slowness:
                slownessInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Strength:
                strengthInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Swiftness:
                swiftInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Water_Breathing:
                waterInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;

            case Weakness:
                weakInversionValues.add(new int[] { objId.getValue(), id, invValue });
                break;
        }

        return true;
    }
}
