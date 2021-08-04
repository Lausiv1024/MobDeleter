package lausiv1024.tileEntity;

import lausiv1024.MDItems;
import lausiv1024.MDTileEntityTypes;
import lausiv1024.MDUtil;
import lausiv1024.MobDeleter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class MobDeleterTileEntity extends TileEntity implements IInventory, ITickableTileEntity {
    private NonNullList<ItemStack> mobDeleterContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

    public MobDeleterTileEntity() {
        super(MDTileEntityTypes.MOB_DELETER);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        mobDeleterContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, mobDeleterContents);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, mobDeleterContents);
        return compound;
    }

    private void deleteRangedEntity(){
        if (mobDeleterContents.get(0) != ItemStack.EMPTY){
            ItemStack stack = mobDeleterContents.get(0).copy();
            CompoundNBT compound = stack.getTag();
            if (compound != null && !compound.getString("entityName").equals("")){
                for (int i = 0; i < 2 * getRangeUpgradeSize() + 3; i++){
                    for (int j = 0; j < 2 * getRangeUpgradeSize() + 3; j++){
                        int x = i - (1 + getRangeUpgradeSize());
                        int z = j - (1 + getRangeUpgradeSize());
                        Chunk chunk = MDUtil.getRelativeChunk(world, getPos().getX(), getPos().getZ(), x, z);
                        int chunkStartX = chunk.getPos().getXStart();
                        int chunkStartZ = chunk.getPos().getZStart();
                        AxisAlignedBB bb = new AxisAlignedBB(chunkStartX, 1, chunkStartZ, chunkStartX + 16, 255, chunkStartZ + 16);
                        List<LivingEntity> entityList1 = world.getEntitiesWithinAABB(LivingEntity.class, bb);
                        for (LivingEntity entity : entityList1) {
                            if (entity.getName().getString().equals(compound.getString("entityName"))) {
                                entity.setPosition(entity.getPosX(), 1, entity.getPosZ());
                                entity.attackEntityFrom(DamageSource.GENERIC, Float.MAX_VALUE);
                                addNeighborContainerEntityDrops(bb);
                            }
                        }
                    }
                }
            }
        }
    }

    private void putToNeighborTileEntity(ItemStack stack){
        if (world.getTileEntity(new BlockPos(this.getPos().getX(), this.getPos().getY() + 1, this.getPos().getZ())) != null &&
                world.getTileEntity(new BlockPos(this.getPos().getX(), this.getPos().getY() + 1, this.getPos().getZ())) instanceof IInventory){
            IInventory iInventory = (IInventory) world.getTileEntity(new BlockPos(this.getPos().getX(), this.getPos().getY() + 1, this.getPos().getZ()));

            for (int i = 0; i < iInventory.getSizeInventory(); i++){
                if (iInventory.isItemValidForSlot(i, stack)){
                    if (iInventory.getStackInSlot(i) != ItemStack.EMPTY && iInventory.getStackInSlot(i).getItem() == stack.getItem() &&
                            !stack.hasTag() && iInventory.getStackInSlot(i).getCount() != iInventory.getStackInSlot(i).getMaxStackSize()){

                        if (iInventory.getStackInSlot(i).getCount() + stack.getCount() < stack.getItem().getItemStackLimit(stack)){
                            iInventory.getStackInSlot(i).grow(stack.getCount());
                            break;
                        }else{
                            int currentInventoryStackSize = iInventory.getStackInSlot(i).getCount();
                            iInventory.getStackInSlot(i).setCount(iInventory.getStackInSlot(i).getMaxStackSize());
                            stack.setCount(iInventory.getStackInSlot(i).getMaxStackSize() - currentInventoryStackSize);
                            markDirty();
                        }

                    }else if (iInventory.getStackInSlot(i) == ItemStack.EMPTY) {
                        iInventory.setInventorySlotContents(i, stack.copy());
                        break;
                    }
                }
                
            }
        }
    }

    private void addNeighborContainerEntityDrops(AxisAlignedBB rangedesuyone){
        List<ItemEntity> entityItems = world.getEntitiesWithinAABB(ItemEntity.class, rangedesuyone);
        for (ItemEntity itemDeath : entityItems) {
            if (hasCollectChip()) putToNeighborTileEntity(itemDeath.getItem().copy());
            itemDeath.remove();
        }
    }

    private int getRangeUpgradeSize(){
        int n = 0;
        for (int i = 1; i < 7;i++){
            if (mobDeleterContents.get(i) != ItemStack.EMPTY && mobDeleterContents.get(i).getItem() ==MDItems.RANGE_UPGRADE){
                n++;
            }
        }
        return n;
    }

    private boolean hasCollectChip(){
        for (int i = 1; i < 7; i++){
            if (mobDeleterContents.get(i) != ItemStack.EMPTY && mobDeleterContents.get(i).getItem() == MDItems.COLLECT_UPGRADE) return true;
        }
        return false;
    }

    private boolean checkRedStoneInput(){
        return !world.isBlockPowered(getPos());
    }


    @Override
    public void tick() {
        if (checkRedStoneInput())
            deleteRangedEntity();
    }

    @Override
    public int getSizeInventory() {
        return 7;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) { return mobDeleterContents.get(index);}


    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (mobDeleterContents.get(index) == ItemStack.EMPTY) return ItemStack.EMPTY;
        ItemStack stack;
        if (mobDeleterContents.get(index).getCount() <= count){
            stack = mobDeleterContents.get(index);
            mobDeleterContents.set(index, ItemStack.EMPTY);
            return stack;
        }
        stack = mobDeleterContents.get(index).split(count);
        if (mobDeleterContents.get(index).getCount() < 1){
            mobDeleterContents.set(index, ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }


    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = mobDeleterContents.get(index).copy();
        mobDeleterContents.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        mobDeleterContents.set(index, stack);
        if (stack != ItemStack.EMPTY && stack.getCount() > this.getInventoryStackLimit()){
            mobDeleterContents.get(index).setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clear() {}
}
