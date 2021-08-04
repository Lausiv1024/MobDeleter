package lausiv1024.gui.slot;

import lausiv1024.MDItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ChipSlot extends Slot {
    public ChipSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == MDItems.MOB_DATA_CHIP;
    }
}
