package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.CGLColorCompat;
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
import static me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat.foxcolor;

@Mixin(Fox.class)
public abstract class FoxMixin extends Animal {


    protected FoxMixin(EntityType<? extends Animal> type, Level world) {
        super(type, world);
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    protected void injectInTickMovementMethod(CallbackInfo ci) {

        if(FoxGlow.getGlowFoodsList().contains(ForgeRegistries.ITEMS.getKey(this.getMainHandItem().getItem()))){
            this.addEffect(new MobEffectInstance(MobEffects.GLOWING, this.level.getGameRules().getInt(FOXGLOW_DURATION.getRule())*20, 1, true, false));
            if(FoxGlow.getCGL()){
                if(level.getGameRules().getBoolean(CUSTOM_COLOR_GLOW.getRule())){
                    if(random.nextInt(1005) == 1){
                        ColoredGlowLibCompat.getLib().setRainbowColorToEntity(((Fox)(Object)this), true);
                    }else if(level.getGameRules().getBoolean(RANDOM_COLOR_GLOW.getRule())){
                        ColoredGlowLibCompat.getLib().setColorToEntityType(this.getType(), CGLColorCompat.randomColor(random));
                    }else{
                        ColoredGlowLibCompat.getLib().setColorToEntityType(this.getType(), foxcolor);
                    }
                    if(FoxGlow.getAP1()){
                        this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, this.level.getGameRules().getInt(FOXGLOW_DURATION.getRule())*20, 1, true, false));
                        this.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, this.level.getGameRules().getInt(FOXGLOW_DURATION.getRule())*20*2, 200, true, false));
                    }
                }else if(!ColoredGlowLibCompat.getLib().getEntityTypeColor(this.getType()).equals(CGLColorCompat.getWhiteColor())){
                    //This is done because the entity would still have another color selected otherwise.
                    ColoredGlowLibCompat.getLib().setColorToEntityType(this.getType(), CGLColorCompat.getWhiteColor());
                }
            }

        }
    }

}

