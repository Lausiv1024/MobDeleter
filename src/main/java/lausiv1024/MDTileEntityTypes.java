package lausiv1024;

import lausiv1024.blocks.FireChargeDeleter;
import lausiv1024.tileEntity.FireChargeDeleterTileEntity;
import lausiv1024.tileEntity.MobDeleterTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.ArrayList;
import java.util.List;

public class MDTileEntityTypes {
    public static final List<TileEntityType> TILE_ENTITY_TYPES = new ArrayList<>();

    public static final TileEntityType MOB_DELETER = register("mob_deleter_tileentity",
            TileEntityType.Builder.create(MobDeleterTileEntity::new, MDBlocks.MOB_DELETER_BLOCK).build(null));
    public static final TileEntityType FIRE_CHARGE_DELETER = register("fire_charge_deleter_tileentity",
            TileEntityType.Builder.create(FireChargeDeleterTileEntity::new, MDBlocks.FIRE_CHARGE_DELETER).build(null));

    private static TileEntityType<?> register(String registerName, TileEntityType<?> type){
        TileEntityType<?> typeNamed = type.setRegistryName(MobDeleter.ID, registerName);
        TILE_ENTITY_TYPES.add(typeNamed);
        return typeNamed;
    }
}
