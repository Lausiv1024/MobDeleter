package lausiv1024;

import lausiv1024.container.ContainerTypes;
import lausiv1024.container.MobDeleterContainer;
import lausiv1024.gui.MobDeleterScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        for (Item item : MDItems.ITEM_LIST){
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){
        for (Block block : MDBlocks.BLOCK_LIST){
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void registerTileEntityType(final RegistryEvent.Register<TileEntityType<?>> event){
        for (TileEntityType<?> tileEntityType : MDTileEntityTypes.TILE_ENTITY_TYPES){
            event.getRegistry().register(tileEntityType);
        }
    }

    @SubscribeEvent
    public static void registerTileContainerType(final RegistryEvent.Register<ContainerType<?>> event){
        event.getRegistry().register(ContainerTypes.CONTAINER_MOBDELETER.setRegistryName(MobDeleter.ID, "mobdeleter_container"));
    }

    @SubscribeEvent
    public static void clientSetUp(final FMLClientSetupEvent event){
        ScreenManager.registerFactory(ContainerTypes.CONTAINER_MOBDELETER, MobDeleterScreen::new);
    }
}
