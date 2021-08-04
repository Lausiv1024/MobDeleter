package lausiv1024.items;

import lausiv1024.MDUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MobDataChip extends Item {
    public MobDataChip(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!playerIn.isSneaking()) return new ActionResult(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
        CompoundNBT compound = MDUtil.openNBTData(playerIn.getHeldItem(handIn));
        if (compound.getInt("entityId") != 0){
            compound.putInt("entityId" ,0);
            compound.putString("entityName", "");
            if (!worldIn.isRemote)
                playerIn.sendMessage(new StringTextComponent("Setting cleared."), playerIn.getUniqueID());
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        World world = player.world;
        if (entity instanceof LivingEntity){
            int entityId = entity.getEntityId();
            CompoundNBT compound = MDUtil.openNBTData(stack);
            compound.putInt("entityId", entityId);
            compound.putString("entityName", entity.getName().getString());
            if (!world.isRemote)
                player.sendMessage(new StringTextComponent("Setting successfully saved."), player.getUniqueID());
        }
        player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 1);
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compound = MDUtil.openNBTData(stack);
        if (compound != null){
            int entityId = compound.getInt("entityId");
            String entityName = compound.getString("entityName");
            if (entityId != 0)
                tooltip.add(new StringTextComponent("Entity : " + entityName));
        }
    }
}
