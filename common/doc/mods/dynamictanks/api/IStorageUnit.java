package doc.mods.dynamictanks.api;

public interface IStorageUnit {
	
	/**
	 * Method to fill the @IStorageUnit with energy.
	 * It is imperative that this method is implemented.
	 * @return the amount inputed into the StorageUnit.
	 */
	int fillUnit();
	
	/**
	 * Method to drain energy from the @IStorageUnit.
	 * It is imperative that this method is implemented.
	 * @return the amount drained from the StorageUnit.
	 */
	int drainUnit();
	
}
