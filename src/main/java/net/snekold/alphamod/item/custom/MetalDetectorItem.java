package net.snekold.alphamod.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        
        if(!level.isClientSide()) {
            // находимся на сервере
            boolean foundBlock = false;

            // сломать предмет
            context.getItemInHand().hurtAndBreak(1, player, context.getHand());

            for (int i = 0; i < pos.getY() + 64; i++) {
                BlockState blockState = level.getBlockState(pos.below(i));

                if (isValuableBlock(blockState)){
                    //пишем в чат точный координаты
                    //outputValuableCoordinates(pos.below(i), player, blockState.getBlock());
                    foundBlock = true;

                    // проиграть звук
                    level.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.5f, 1f);
                    // создать частицы
                    spawnFoundParticles(level, pos, blockState);

                    break;

                }
            }

            if(!foundBlock) { // не нашли блок за координату
                //пишем в чат не нашли
                //outputNoValuablesFound(player);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private void spawnFoundParticles(Level level, BlockPos positionClicked, BlockState blockState) {
        for(int i = 0; i < 20; i++) {
            ServerLevel serverLevel = (ServerLevel) level;

            serverLevel.sendParticles(
                    // 1. КАКИЕ частицы спавним
                    new BlockParticleOption(ParticleTypes.BLOCK, blockState),

                    // 2, 3, 4. ГДЕ спавним (Координаты X, Y, Z)
                    positionClicked.getX() + 0.5d, // X: координата блока + 0.5 (чтобы спавнить по центру блока, а не на краю)
                    positionClicked.getY() + 1,    // Y: координата блока + 1 (чтобы частицы появились НАД блоком, а не внутри него)
                    positionClicked.getZ() + 0.5d, // Z: координата блока + 0.5 (снова центр)

                    // 5. СКОЛЬКО частиц за один вызов
                    1, // Так как у нас цикл на 20 раз, то 1 * 20 = 20 частиц.

                    // 6, 7, 8. КУДА они полетят (Разброс / Скорость по осям X, Y, Z)
                    Math.cos(i * 18) * 0.5d, // Разброс по X
                    0.15d,                    // Разброс по Y (всегда летят немного вверх)
                    Math.sin(i * 18) * 0.5d, // Разброс по Z

                    // 9. ОБЩАЯ СКОРОСТЬ частиц
                    0.2
            );
        }
    }

    private void outputValuableCoordinates(BlockPos pos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found: ")
                        .append(block.getName()
                        .append(Component.literal(" at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")"))));

    }

    private void outputNoValuablesFound(Player player) {
        player.sendSystemMessage(Component.translatable("item.alphamod.metal_detector.no_valuables"));
    }

    private boolean isValuableBlock(BlockState blockState) {
        return blockState.is(Blocks.IRON_BLOCK) || blockState.is(Blocks.DEEPSLATE_IRON_ORE)
                || blockState.is(Blocks.DIAMOND_ORE) || blockState.is(Blocks.DEEPSLATE_DIAMOND_ORE);
    }

    @Override // для расширеный подсказок
    public void appendHoverText(ItemStack itemStack,
                                TooltipContext context,
                                TooltipDisplay display,
                                Consumer<Component> builder,
                                TooltipFlag tooltipFlag) {

        if(Minecraft.getInstance().hasShiftDown()) { // с шифтом и без
            builder.accept(Component.translatable("toolip.alphamod.metal_detector.shift_down"));
        } else {
            builder.accept(Component.translatable("toolip.alphamod.metal_detector.shift_none"));
        }

        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
