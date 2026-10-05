package net.snekold.alphamod.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class ModFoods {
    public static final FoodProperties CHIPS = new FoodProperties.Builder().nutrition(10).saturationModifier(0.6f).build();

    public static final Consumable CHIPS_CONSUMABLE = Consumables
            .defaultFood()
            .consumeSeconds(1.0f)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WEAKNESS, 450), 0.10f))
            .build();
}
