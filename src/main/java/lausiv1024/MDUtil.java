package lausiv1024;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class MDUtil {
    public static CompoundNBT openNBTData(final ItemStack stack){
        CompoundNBT compound = stack.getTag();

        if (compound == null){
            stack.setTag(compound = new CompoundNBT());
        }
        return compound;
    }

    public static Chunk getRelativeChunk(World world, int xChood, int zChood, int countX, int countZ){
        int x = xChood + countX * 16;
        int z = zChood + countZ * 16;
        return world.getChunkAt(new BlockPos(x, 1, z));
    }
}
