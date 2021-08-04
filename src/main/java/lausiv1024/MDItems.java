package lausiv1024;

import lausiv1024.items.MobDataChip;
import lausiv1024.items.Upgrade_chip;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.List;

public class MDItems {
    public static final List<Item> ITEM_LIST = new ArrayList<>();

    public static final Item MOB_DELETER_BLOCKITEM = register("mob_deleter_block",
            new BlockItem(MDBlocks.MOB_DELETER_BLOCK, new Item.Properties().group(MobDeleter.MOB_DELETER_ITEMGROUP)));
    public static final Item MOB_DATA_CHIP = register("mobdata_chip",
            new MobDataChip(new Item.Properties().group(MobDeleter.MOB_DELETER_ITEMGROUP).maxStackSize(1)));
    public static final Item RANGE_UPGRADE = register("upgrade_chip-range",
            new Upgrade_chip(new Item.Properties().group(MobDeleter.MOB_DELETER_ITEMGROUP)));
    public static final Item COLLECT_UPGRADE = register("upgrade_chip-collect",
            new Upgrade_chip(new Item.Properties().group(MobDeleter.MOB_DELETER_ITEMGROUP)));
    public static final Item FIRE_CHARGE_DELETER_BLOCKITEM = register("fire_charge_deleter",
            new BlockItem(MDBlocks.FIRE_CHARGE_DELETER, new Item.Properties().group(MobDeleter.MOB_DELETER_ITEMGROUP)));
    public static final Item EP_GLOWSTONE = register("ep_glowstone",
            new BlockItem(MDBlocks.EP_GLOWSTONE, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    private static Item register(String registryName, Item item){
        Item itemNamed = item.setRegistryName(MobDeleter.ID, registryName);
        ITEM_LIST.add(itemNamed);
        return itemNamed;
    }
}
