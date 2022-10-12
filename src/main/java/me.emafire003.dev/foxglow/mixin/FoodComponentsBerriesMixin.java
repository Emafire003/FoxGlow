package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodComponents.class)
public interface FoodComponentsBerriesMixin {
    @Accessor("GLOW_BERRIES")
    @Mutable
    static void setEdible(FoodComponent component) {
        FoxGlow.LOGGER.info("==========================tryina modify==========================");
        throw new AssertionError();
    }
}
