package me.emafire003.dev.foxglow.mixin;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoodComponents.class)
public interface GetBerriesComponentMixin {
    @Accessor("GLOW_BERRIES")
    @Mutable
    static FoodComponent getBerryComponent() {
        throw new AssertionError();
    }
}
