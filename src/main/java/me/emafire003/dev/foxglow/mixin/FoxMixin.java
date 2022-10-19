package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
//Слава Украïнi!
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.emafire003.dev.foxglow.FoxGlow.*;

@Mixin(Fox.class)
public abstract class FoxMixin extends Animal {


    protected FoxMixin(EntityType<? extends Animal> type, Level world) {
        super(type, world);
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    protected void injectInTickMovementMethod(CallbackInfo ci) {

        if(FoxGlow.getGlowFoodsList().contains(ForgeRegistries.ITEMS.getKey(this.getMainHandItem().getItem()))){
            this.addEffect(new MobEffectInstance(MobEffects.GLOWING, FOXGLOW_DURATION*20, 1, true, false));

            if(FoxGlow.getCGL()){
                ColoredGlowLibCompat.doColoredGlowLibStuff(this.level, ((Fox)(Object)this));
            }
            if(FoxGlow.getAP1()){
                this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, FOXGLOW_DURATION*20, 1, true, false));
                this.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, FOXGLOW_DURATION*20*2, 200, true, false));

            }

        }
    }

}

