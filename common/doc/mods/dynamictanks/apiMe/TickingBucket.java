package doc.mods.dynamictanks.apiMe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import doc.mods.dynamictanks.helpers.NumberHelper;

public class TickingBucket extends ItemBucket {

	private Map<String, Integer>     monitoredVars = new HashMap<String, Integer>();
	private Map<String, String>      displayNames  = new HashMap<String, String> ();
	private Map<String, tickingType> doDecrease    = new HashMap<String, tickingType>();
	private Map<String, Boolean>     doDisplay     = new HashMap<String, Boolean>();
	private Map<String, String>      dependantVars = new HashMap<String, String>();
	
	private List<Integer> ignoredDamage = new ArrayList<Integer>(); 

	public enum tickingType {
		INCREASE,
		DECREASE,
		NOMOVEMENT;
	}

	public TickingBucket(int par1) {
		super(par1, 0);
	}

	public void addMonitoredElement(String eleName, String displayName, int startVal, tickingType doesDecrease) {
		monitoredVars.put(eleName, startVal);
		displayNames .put(eleName, displayName);
		doDecrease   .put(eleName, doesDecrease);
		doDisplay    .put(eleName, true);
	}

	public void addDependantVar(String independantName, String dependantName) {
		
	}
	
	public boolean cancelTooltipDisplay(String eleName) {
		if (doDisplay.containsKey(eleName)) {
			doDisplay.remove(eleName);
			doDisplay.put(eleName, false);
			return true;
		}
		return false;		
	}

	public void addIgnoredDamage(int itemDamage) {
		ignoredDamage.add(itemDamage);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		for (Map.Entry<String, Integer> variableSet : monitoredVars.entrySet()) {
			if (doDisplay.containsKey(variableSet.getKey()) && doDisplay.get(variableSet.getKey()).booleanValue())
				if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey(variableSet.getKey()))
					list.add(displayNames.get(variableSet.getKey()) + ": " + NumberHelper.round(1, (((float) (stack.stackTagCompound.getInteger(variableSet.getKey())) / (float) (variableSet.getValue())) * 100)) + "%");
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		for (int i : ignoredDamage)
			if (i == stack.getItemDamage())
				return;
		
		for (Map.Entry<String, String> dependantSet : dependantVars.entrySet()) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			
			if (!stack.stackTagCompound.hasKey(dependantSet.getKey()))
				if (doDecrease.get(dependantSet.getValue()).equals(tickingType.DECREASE))
					stack.stackTagCompound.setInteger(dependantSet.getKey(), monitoredVars.get(dependantSet.getKey()));
				else if (doDecrease.get(dependantSet.getValue()).equals(tickingType.INCREASE))
					stack.stackTagCompound.setInteger(dependantSet.getKey(), 0);
				else
					stack.stackTagCompound.setInteger(dependantSet.getKey(), monitoredVars.get(dependantSet.getKey()));
			if (!stack.stackTagCompound.hasKey(dependantSet.getValue()))
				if (doDecrease.get(dependantSet.getValue()).equals(tickingType.DECREASE))
					stack.stackTagCompound.setInteger(dependantSet.getValue(), monitoredVars.get(dependantSet.getValue()));
				else if (doDecrease.get(dependantSet.getValue()).equals(tickingType.INCREASE))
					stack.stackTagCompound.setInteger(dependantSet.getValue(), 0);
				else
					stack.stackTagCompound.setInteger(dependantSet.getValue(), monitoredVars.get(dependantSet.getValue()));
			
			if (doDecrease.containsKey(dependantSet.getKey()) && doDecrease.get(dependantSet.getKey()).equals(tickingType.DECREASE)) {
				int currentVal = stack.stackTagCompound.getInteger(dependantSet.getKey());
				currentVal--;
				if (currentVal < 0)
					currentVal = 0;
				stack.stackTagCompound.setInteger(dependantSet.getKey(), currentVal);
				
				//Start Value
				if (doDecrease.containsKey(dependantSet.getValue()) && doDecrease.get(dependantSet.getValue()).equals(tickingType.DECREASE)) {
					int currentVals = stack.stackTagCompound.getInteger(dependantSet.getValue());
					currentVals--;
					if (currentVals < 0)
						currentVals = 0;
					stack.stackTagCompound.setInteger(dependantSet.getValue(), currentVals);
				} else if (doDecrease.containsKey(dependantSet.getValue()) && doDecrease.get(dependantSet.getValue()).equals(tickingType.INCREASE)) {
					int currentVals = stack.stackTagCompound.getInteger(dependantSet.getValue());
					currentVals++;
					if (currentVals > monitoredVars.get(dependantSet.getValue()))
						currentVals = monitoredVars.get(dependantSet.getValue());
					stack.stackTagCompound.setInteger(dependantSet.getValue(), currentVals);
				} 
				//End Value
			} else if (doDecrease.containsKey(dependantSet.getKey()) && doDecrease.get(dependantSet.getKey()).equals(tickingType.INCREASE)) {
				int currentVal = stack.stackTagCompound.getInteger(dependantSet.getKey());
				currentVal++;
				if (currentVal > monitoredVars.get(dependantSet.getKey()))
					currentVal = monitoredVars.get(dependantSet.getKey());
				stack.stackTagCompound.setInteger(dependantSet.getKey(), currentVal);
				
				//Start Value
				if (doDecrease.containsKey(dependantSet.getValue()) && doDecrease.get(dependantSet.getValue()).equals(tickingType.DECREASE)) {
					int currentVals = stack.stackTagCompound.getInteger(dependantSet.getValue());
					currentVals--;
					if (currentVals < 0)
						currentVals = 0;
					stack.stackTagCompound.setInteger(dependantSet.getValue(), currentVals);
				} else if (doDecrease.containsKey(dependantSet.getValue()) && doDecrease.get(dependantSet.getValue()).equals(tickingType.INCREASE)) {
					int currentVals = stack.stackTagCompound.getInteger(dependantSet.getValue());
					currentVals++;
					if (currentVals > monitoredVars.get(dependantSet.getValue()))
						currentVals = monitoredVars.get(dependantSet.getValue());
					stack.stackTagCompound.setInteger(dependantSet.getValue(), currentVals);
				} 
				//End Value
			} 
		}
		
		for (Map.Entry<String, Integer> variableSet : monitoredVars.entrySet()) {
			if (dependantVars.containsKey(variableSet.getKey()) || dependantVars.containsValue(variableSet.getKey()))
				return;
			
			if (doDecrease.containsKey(variableSet.getKey())) {
				if (stack.stackTagCompound == null)
					stack.stackTagCompound = new NBTTagCompound();
				
				if (doDecrease.get(variableSet.getKey()).equals(tickingType.NOMOVEMENT)) {
					if (!stack.stackTagCompound.hasKey(variableSet.getKey()))
						stack.stackTagCompound.setInteger(variableSet.getKey(), variableSet.getValue());
				} else if (doDecrease.get(variableSet.getKey()).equals(tickingType.DECREASE)) {
					if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey(variableSet.getKey())) {
						int currentVal = stack.stackTagCompound.getInteger(variableSet.getKey());
						currentVal--;
						if (currentVal < 0)
							currentVal = 0;
						stack.stackTagCompound.setInteger(variableSet.getKey(), currentVal);
					}
					if (stack.stackTagCompound != null && !stack.stackTagCompound.hasKey(variableSet.getKey()))
						stack.stackTagCompound.setInteger(variableSet.getKey(), variableSet.getValue());
				} else if (doDecrease.get(variableSet.getKey()).equals(tickingType.INCREASE)) {
					if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey(variableSet.getKey())) {
						int currentVal = stack.stackTagCompound.getInteger(variableSet.getKey());
						currentVal++;
						if (currentVal > variableSet.getValue())
							currentVal = variableSet.getValue();
						stack.stackTagCompound.setInteger(variableSet.getKey(), currentVal);
					}
					if (stack.stackTagCompound != null && !stack.stackTagCompound.hasKey(variableSet.getKey()))
						stack.stackTagCompound.setInteger(variableSet.getKey(), 0);
				}
			}
		}
	}

}
