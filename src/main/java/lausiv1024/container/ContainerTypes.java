package lausiv1024.container;

import lausiv1024.gui.MobDeleterGuiFactory;
import net.minecraft.inventory.container.ContainerType;

public class ContainerTypes{
    public static final ContainerType<MobDeleterContainer> CONTAINER_MOBDELETER = new ContainerType(new MobDeleterGuiFactory());
}
