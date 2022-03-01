package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.util.Color;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import static me.emafire003.dev.foxglow.FoxGlow.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "eatFood", at = @At("HEAD"))
    protected void injectInEatFoodMethod(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        //checks if the world is actually the server world, so it can apply stuff properly, like gamerules
        if(world instanceof ServerWorld){
            ServerWorld sworld = (ServerWorld) world;

            //Checks if the item eatean is the glowberry item & if the playerglow is active
            if(stack.getItem().equals(Items.GLOW_BERRIES) && sworld.getGameRules().getBoolean(PLAYERGLOW)){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 0, true, false));

                //checks if custom color glow is enabled
                if(sworld.getGameRules().getBoolean(CUSTOM_COLOR_GLOW)) {

                    //Checks if it should apply a random color
                    if (sworld.getGameRules().getBoolean(RANDOM_COLOR_GLOW)) {
                        ColoredGlowLib.setColorToEntityType(this.getType(), new Color(random.nextInt(254) + 1, random.nextInt(254) + 1, random.nextInt(254) + 1));
                    } else {
                        ColoredGlowLib.setColorToEntityType(this.getType(), foxcolor);
                    }

                }else if(!ColoredGlowLib.getEntityTypeColor(this.getType()).equals(Color.getWhiteColor())){
                    //This is done because the entity would still have another color selected otherwise.
                    ColoredGlowLib.setColorToEntityType(this.getType(), Color.getWhiteColor());
                }

            }
        }
    }
}

