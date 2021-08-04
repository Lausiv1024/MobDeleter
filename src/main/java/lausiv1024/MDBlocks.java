package lausiv1024;

import lausiv1024.blocks.FireChargeDeleter;
import lausiv1024.blocks.MobDeleterBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public class MDBlocks {
    public static final List<Block> BLOCK_LIST = new ArrayList<>();

    public static final Block MOB_DELETER_BLOCK = register("mob_deleter_block", new MobDeleterBlock(AbstractBlock.Properties.create(Material.ROCK)
    .hardnessAndResistance(2, 10).sound(SoundType.METAL).harvestLevel(1).setRequiresTool()));
    public static final Block FIRE_CHARGE_DELETER = register("fire_charge_deleter", new FireChargeDeleter(AbstractBlock.Properties.create(Material.ROCK)
    .hardnessAndResistance(2, 10).sound(SoundType.METAL).harvestLevel(1).setRequiresTool()));
    public static final Block EP_GLOWSTONE = register("ep_glowstone",
            new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.3f, 1200.0F).sound(SoundType.GLASS).setLightLevel(value -> 15)));

    private static Block register(String registerName, Block block){
        Block blockNamed = block.setRegistryName(MobDeleter.ID, registerName);
        BLOCK_LIST.add(blockNamed);
        return blockNamed;
    }
}
