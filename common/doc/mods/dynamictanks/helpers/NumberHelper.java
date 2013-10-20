package doc.mods.dynamictanks.helpers;

public class NumberHelper {

	public static int inRange(int Min, int Max) {
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	
}
