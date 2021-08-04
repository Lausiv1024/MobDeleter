package lausiv1024.gui.slot;

import lausiv1024.items.Upgrade_chip;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class UpgradeChipSlot extends Slot {
    public UpgradeChipSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof Upgrade_chip;
    }
}
