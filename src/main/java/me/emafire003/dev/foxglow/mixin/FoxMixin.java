package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.emafire003.dev.foxglow.FoxGlow.*;

@Pseudo
@Mixin(FoxEntity.class)
public abstract class FoxMixin extends AnimalEntity {

    protected FoxMixin(EntityType<? extends FoxEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;finishUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;"))
    protected void injectInTickMovementMethod(CallbackInfo ci) {

        if(FoxGlow.getGlowFoodsList().contains(Registries.ITEM.getId(this.getEquippedStack(EquipmentSlot.MAINHAND).getItem()))){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, this.getWorld().getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));

            if(FabricLoader.getInstance().isModLoaded("coloredglowlib")){
                ColoredGlowLibCompat.doColoredGlowLibStuff(getWorld(), ((FoxEntity)(Object)this));
            }

            if(FoxGlow.getAP1()){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, this.getWorld().getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, this.getWorld().getGameRules().getInt(FOXGLOW_DURATION)*20*2, 200, true, false));
            }

        }
    }

}

