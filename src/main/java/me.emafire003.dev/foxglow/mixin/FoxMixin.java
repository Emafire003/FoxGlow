package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.util.Color;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.Items;
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
        if(this.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(Items.GLOW_BERRIES)){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));

            if(world.getGameRules().getBoolean(CUSTOM_COLOR_GLOW)){
                if(world.getGameRules().getBoolean(RANDOM_COLOR_GLOW)){
                    ColoredGlowLib.setColorToEntityType(this.getType(),new Color(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1));
                }else{
                    ColoredGlowLib.setColorToEntityType(this.getType(), foxcolor);
                }
            }else if(!ColoredGlowLib.getEntityTypeColor(this.getType()).equals(Color.getWhiteColor())){
                //This is done because the entity would still have another color selected otherwise.
                ColoredGlowLib.setColorToEntityType(this.getType(), Color.getWhiteColor());
            }

        }
    }

}

