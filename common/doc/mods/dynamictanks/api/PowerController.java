package doc.mods.dynamictanks.api;

public class PowerController {

	protected int producedAmount = 5;
	protected int consumeAmount = 15;
	
	protected int contained = 0;
	protected int internalStorage = 375;
	
	public void modify(int contained, int internalStorage, int consumeAmount) {
		this.contained = contained;
		this.internalStorage = internalStorage;
		this.consumeAmount = consumeAmount;
	}
	
	public boolean drain() {
		if ((internalStorage - consumeAmount) >= 0) {
			internalStorage -= consumeAmount;
			return true;
		}
		return false;
	}
}
