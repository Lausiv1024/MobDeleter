package lausiv1024.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lausiv1024.MobDeleter;
import lausiv1024.container.MobDeleterContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MobDeleterScreen extends ContainerScreen<MobDeleterContainer> {
    private static final ResourceLocation GUITEXTURE = new ResourceLocation(MobDeleter.ID, "textures/gui/mob_deleter_gui.png");

    public MobDeleterScreen(MobDeleterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
        font.drawString(matrixStack, "Targeted Entity :", 7, 20, 4210752);
        font.drawString(matrixStack, getTargetedEntity(), 7, 30, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUITEXTURE);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;

        this.blit(matrixStack, k, l, 0, 0, this.xSize, this.ySize);
    }

    private String getTargetedEntity(){
        Slot slot = getContainer().inventorySlots.get(0);
        if (slot != null && slot.getStack() != ItemStack.EMPTY){
            CompoundNBT compound = slot.getStack().getTag();
            return compound.getString("entityName");
        }
        return "";
    }
}
