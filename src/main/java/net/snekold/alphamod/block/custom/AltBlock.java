package net.snekold.alphamod.block.custom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.Collections;
import java.util.List;

public class AltBlock extends Block {

    // Свойство для хранения ID исходного блока (0-100000 должно хватить)
    public static final IntegerProperty SOURCE_BLOCK_ID = IntegerProperty.create("source", 0, 100000);

    public AltBlock(Properties properties) {
        super(properties);
        // Регистрируем default state с source = 0 (воздух/ручная установка)
        this.registerDefaultState(this.stateDefinition.any().setValue(SOURCE_BLOCK_ID, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOURCE_BLOCK_ID);
    }

    // Переопределяем дропы — вместо себя дропаем исходный блок
    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        int sourceId = state.getValue(SOURCE_BLOCK_ID);

        // Если source = 0 (блок поставили вручную, а не через опал), дропаем сам ALT_BLOCK
        if (sourceId == 0) {
            return Collections.singletonList(new ItemStack(this.asItem()));
        }

        // Иначе пытаемся получить исходный блок по ID
        Block sourceBlock = BuiltInRegistries.BLOCK.byId(sourceId);

        // Если блок найден и это не воздух, дропаем его
        if (sourceBlock != null && sourceBlock != Blocks.AIR) {
            return Collections.singletonList(new ItemStack(sourceBlock.asItem()));
        }

        // На всякий случай (если ID битый), дропаем сам ALT_BLOCK
        return Collections.singletonList(new ItemStack(this.asItem()));
    }
}