package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.emafire003.dev.foxglow.FoxGlow.FOXGLOW_DURATION;
import static me.emafire003.dev.foxglow.FoxGlow.EVERYONE_GLOWS;

@Mixin(FoodComponent.class)
public abstract class GlowyFoodMixin {

    @Inject(method = "onConsume", at = @At("HEAD"))
    public void injectTryGlowingFood(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable, CallbackInfo ci){

        if(world instanceof ServerWorld sworld){

            //Checks if the item eaten is the glowberry/a glowfood item & if the playerglow is active
            if((user instanceof FoxEntity || sworld.getGameRules().getBoolean(EVERYONE_GLOWS))
                    && FoxGlow.getGlowFoodsList().contains(Registries.ITEM.getId(stack.getItem()))){
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, sworld.getGameRules().getInt(FOXGLOW_DURATION)*20, 0, true, false));

                //checks if custom color glow is enabled
                if(FabricLoader.getInstance().isModLoaded("coloredglowlib")) {
                    ColoredGlowLibCompat.doColoredGlowLibStuff(sworld, user);
                }

                if(FoxGlow.getAP1()){
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, sworld.getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, sworld.getGameRules().getInt(FOXGLOW_DURATION)*20*2, 200, true, false));
                }

            }
        }
    }
}
