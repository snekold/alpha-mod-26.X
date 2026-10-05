package net.snekold.alphamod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.snekold.alphamod.block.ModBlocks;
import org.jspecify.annotations.Nullable;

public class OpalBlock extends Block {

    public OpalBlock(Properties properties) {
        super(properties);
    }

    // ─── Задача 1: превращение в ALT_BLOCK ───
    @Override
    protected void neighborChanged(
            BlockState state,
            Level level,
            BlockPos pos,
            Block block,
            @Nullable Orientation orientation,
            boolean movedByPiston
    ) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);

        if (level.isClientSide()) return;

        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        // 1. Проверка: блок сверху твёрдый
        if (!aboveState.isFaceSturdy(level, abovePos, Direction.DOWN)) return;

        // 2. Проверка: это ещё не ALT_BLOCK
        if (aboveState.is(ModBlocks.ALT_BLOCK.get())) return;

        // 3. ⭐ ГЛАВНАЯ ПРОВЕРКА: блок не должен быть нерушимым
        float destroyTime = aboveState.getBlock().defaultDestroyTime();
        if (destroyTime < 0.0F) {  // -1.0F = INDESTRUCTIBLE (bedrock и т.д.)
            return;
        }

        // 4. Дополнительно: можно запретить превращение определённых блоков
        // Например, командных блоков, барьеров, порталов
        if (isProtectedBlock(aboveState)) return;

        // Превращаем
        int sourceBlockId = BuiltInRegistries.BLOCK.getId(aboveState.getBlock());
        BlockState newAltState = ModBlocks.ALT_BLOCK.get().defaultBlockState()
                .setValue(AltBlock.SOURCE_BLOCK_ID, sourceBlockId);

        level.setBlock(abovePos, newAltState, Block.UPDATE_ALL);
    }

    // Метод для проверки защищённых блоков
    private boolean isProtectedBlock(BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.BARRIER
                || block == Blocks.COMMAND_BLOCK
                || block == Blocks.CHAIN_COMMAND_BLOCK
                || block == Blocks.REPEATING_COMMAND_BLOCK
                || block == Blocks.STRUCTURE_BLOCK
                || block == Blocks.STRUCTURE_VOID
                || block == Blocks.END_PORTAL_FRAME
                || block == Blocks.END_PORTAL
                || block == Blocks.NETHER_PORTAL
                || block == Blocks.END_GATEWAY
                || block == Blocks.JIGSAW;
    }

    // ─── Задача 2: ломаем ALT_BLOCK сверху ───
    // В новых версиях onRemove заменён на affectNeighborsAfterRemoval
    @Override
    protected void affectNeighborsAfterRemoval(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            boolean movedByPiston
    ) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        if (aboveState.is(ModBlocks.ALT_BLOCK.get())) {
            // true = дропнуть предметы как при обычном разрушении
            level.destroyBlock(abovePos, true);
        }

        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }
}