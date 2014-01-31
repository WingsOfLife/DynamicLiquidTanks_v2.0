package doc.mods.dynamictanks.helpers;

public class NumberHelper
{
    public static int inRange(int Min, int Max)
    {
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

    public static int[] divisionWithRemainder(double dividen, double divisor)
    {
        if (dividen % divisor == 0)
            return new int[] { (int)(dividen / divisor), 0 };

        return new int[] { (int)(dividen / divisor), (int)(dividen % divisor) };
    }
    
    public static float round(int numDigits, float numToRound) {
    	return (float) ((float) Math.round(numToRound * Math.pow(10, numDigits)) / Math.pow(10, numDigits));
    }
}
