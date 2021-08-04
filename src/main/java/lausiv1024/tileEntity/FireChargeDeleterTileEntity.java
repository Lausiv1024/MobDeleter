package lausiv1024.tileEntity;

import lausiv1024.MDTileEntityTypes;
import lausiv1024.MDUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class FireChargeDeleterTileEntity extends TileEntity implements ITickableTileEntity {
    public FireChargeDeleterTileEntity() {
        super(MDTileEntityTypes.FIRE_CHARGE_DELETER);
    }

    @Override
    public void tick() {
        deleteRangedEntity();
    }

    private void deleteRangedEntity(){
        for (int i = 0; i < getRangeUpgradeSize() + 3; i++){
            for (int j = 0; j < getRangeUpgradeSize() + 3; j++){
                int x = i - (1 + getRangeUpgradeSize());
                int z = j - (1 + getRangeUpgradeSize());
                Chunk chunk = MDUtil.getRelativeChunk(world, getPos().getX(), getPos().getZ(), x, z);
                int chunkStartX = chunk.getPos().getXStart();
                int chunkStartZ = chunk.getPos().getZStart();
                AxisAlignedBB bb = new AxisAlignedBB(chunkStartX, 1, chunkStartZ, chunkStartX + 16, 255, chunkStartZ + 16);
                List<FireballEntity> entityList1 = world.getEntitiesWithinAABB(FireballEntity.class, bb);
                for (FireballEntity entityFireball : entityList1) {
                    LivingEntity base = (LivingEntity) entityFireball.func_234616_v_();
                    if (base != null) base.attackEntityFrom(DamageSource.GENERIC, Float.MAX_VALUE);
                    entityFireball.remove();
                }
            }
        }
    }


    private int getRangeUpgradeSize(){
        return 5;
    }
}
