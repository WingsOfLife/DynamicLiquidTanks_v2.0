package doc.mods.dynamictanks.apiMe;

public interface IPowerEater
{
    /**
     * Method to consume energy from either @IPowerProducer or
     * @IStorageUntil
     * @return return the amount of energy consumed
     */
    int consume();
}
