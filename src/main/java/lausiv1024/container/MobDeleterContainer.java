package lausiv1024.container;

import jdk.nashorn.internal.ir.Block;
import lausiv1024.MobDeleter;
import lausiv1024.gui.slot.ChipSlot;
import lausiv1024.gui.slot.UpgradeChipSlot;
import lausiv1024.items.MobDataChip;
import lausiv1024.items.Upgrade_chip;
import lausiv1024.tileEntity.MobDeleterTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MobDeleterContainer extends Container {
    MobDeleterTileEntity mobDeleterInventory;
    private BlockPos pos;

    private static final int index0 = 0;
    private static final int indexPlayer = 7;
    private static final int indexQuickSlot1 = 34;
    private static final int indexAllSlot = 43;


    public MobDeleterContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(ContainerTypes.CONTAINER_MOBDELETER, id);
        pos = null;
        pos = buffer.readBlockPos();
        TileEntity tileEntity = playerInventory.player.world.getTileEntity(pos);
        mobDeleterInventory = (MobDeleterTileEntity) tileEntity;

        this.addSlot(new ChipSlot(mobDeleterInventory, 0, 80, 35));


        int i7 = 0;
        for (int y = 0; y < 2; y++){
            for (int x = 0; x < 3; x++){
                i7++;
                this.addSlot(new UpgradeChipSlot(mobDeleterInventory, i7, 118 + x * 18, 6 + y * 18));
            }
        }

        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 9; x++){
                this.addSlot(new Slot(playerInventory, x + (y * 9) + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory, i, 8 + (i * 18), 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) inventorySlots.get(index);
        if (slot != null && slot.getHasStack()){
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();
            if (index0 <= index && index < indexPlayer){
                if (!this.mergeItemStack(stack, indexPlayer, indexAllSlot, true)){
                    return ItemStack.EMPTY;
                }
            }else{
                if (stack.getItem() instanceof MobDataChip){
                    Slot slot114514 = (Slot) inventorySlots.get(0);
                    if (slot114514.getHasStack()) return ItemStack.EMPTY;
                    slot114514.putStack(stack.copy());
                    slot114514.onSlotChanged();
                    slot.putStack(ItemStack.EMPTY);
                    return ItemStack.EMPTY;
                }else if (stack.getItem() instanceof Upgrade_chip){
                    if (stack.getCount() > 6){
                        ItemStack stack1 = stack.copy();
                        stack1.setCount(1);
                        for (int i = 0; i < 6; i++){
                            Slot slot1 = (Slot) inventorySlots.get(i + 1);
                            if (!slot1.getHasStack()){
                                slot1.putStack(stack1.copy());
                                slot1.onSlotChanged();
                                stack.shrink(1);
                            }
                        }
                        return ItemStack.EMPTY;
                    }else{
                        ItemStack stack1 = stack.copy();
                        stack1.setCount(1);
                        int ij = 0;
                        while (stack.getCount() > 0){
                            Slot slot1 = (Slot) inventorySlots.get(ij + 1);
                            if (!slot1.getHasStack()){
                                slot1.putStack(stack1.copy());
                                slot1.onSlotChanged();
                                stack.shrink(1);
                            }
                            ij++;
                            if (ij >= indexPlayer) return ItemStack.EMPTY;
                        }
                        if (stack.getCount() == 0){
                            putStackInSlot(index, ItemStack.EMPTY);
                        }
                        return ItemStack.EMPTY;
                    }
                }
            }
            if (stack.getCount() == 0){
                slot.putStack(ItemStack.EMPTY);
            }else{
                slot.onSlotChanged();
            }
            if (stack.getCount() ==  itemStack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }
        return itemStack;
    }

    @Override
    public boolean canDragIntoSlot(Slot slotIn) {
        return super.canDragIntoSlot(slotIn);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
