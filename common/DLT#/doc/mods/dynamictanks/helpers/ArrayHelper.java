package doc.mods.dynamictanks.helpers;

public class ArrayHelper {
	
	public static void nullArray(Object[] array, int start, int end) {
		
		for (start = 0; start < end; start++)
			array[start] = null;
	}
}
