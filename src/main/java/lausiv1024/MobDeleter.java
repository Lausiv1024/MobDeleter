package lausiv1024;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MobDeleter.ID)
public class MobDeleter {
    public static final String ID = "mobdeleter";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final ItemGroup MOB_DELETER_ITEMGROUP = new ItemGroup("mob_deleter_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MDBlocks.MOB_DELETER_BLOCK);
        }
    };
}
