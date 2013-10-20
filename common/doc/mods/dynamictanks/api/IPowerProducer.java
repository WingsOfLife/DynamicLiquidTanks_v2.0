package doc.mods.dynamictanks.api;

public interface IPowerProducer {
	
	/**
	 * Method to produce energy.
	 * Should first fill internal buffer before
	 * attempting to fill any adjacent @IStorageUnit.
	 * @return amount produced
	 */
	int produce();
	
}
