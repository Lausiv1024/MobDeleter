package lausiv1024.gui;

import lausiv1024.container.MobDeleterContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.ConfigGuiHandler;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;

public class MobDeleterGuiFactory implements IContainerFactory {

    @Override
    public Container create(int windowId, PlayerInventory inv, PacketBuffer data) {
        return new MobDeleterContainer(windowId, inv, data);
    }
}
