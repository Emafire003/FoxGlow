package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.coloredglowlib.util.Color;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
//Слава Украïнi!
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.emafire003.dev.foxglow.FoxGlow.*;

@Mixin(FoxEntity.class)
public abstract class FoxMixin extends AnimalEntity {

    protected FoxMixin(EntityType<? extends FoxEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;finishUsing(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;"))
    protected void injectInTickMovementMethod(CallbackInfo ci) {

        if(FoxGlow.getGlowFoodsList().contains(Registry.ITEM.getId(this.getEquippedStack(EquipmentSlot.MAINHAND).getItem()))){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));

            if(FoxGlow.getCGL()){
                if(world.getGameRules().getBoolean(CUSTOM_COLOR_GLOW)){
                    if(random.nextInt(1005) == 1){
                        ColoredGlowLibCompat.getLib().setRainbowColorToEntity(((FoxEntity)(Object)this), true);
                    }else if(world.getGameRules().getBoolean(RANDOM_COLOR_GLOW)){
                        ColoredGlowLibCompat.getLib().setColorToEntityType(this.getType(),new Color(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1));
                    }else{
                        ColoredGlowLibCompat.getLib().setColorToEntityType(this.getType(), foxcolor);
                    }
                    if(FoxGlow.getAP1()){
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20*2, 200, true, false));
                    }
                }else if(!ColoredGlowLibCompat.getLib().getEntityTypeColor(this.getType()).equals(Color.getWhiteColor())){
                    //This is done because the entity would still have another color selected otherwise.
                    ColoredGlowLibCompat.getLib().setColorToEntityType(this.getType(), Color.getWhiteColor());
                }
            }

        }
    }

}

