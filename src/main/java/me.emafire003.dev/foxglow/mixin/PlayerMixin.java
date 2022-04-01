package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.util.Color;
import me.emafire003.dev.foxglow.FoxGlow;
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

    private PlayerEntity player = ((PlayerEntity)(Object)this);
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
                        ColoredGlowLib.setColorToEntity(player, new Color(random.nextInt(254) + 1, random.nextInt(254) + 1, random.nextInt(254) + 1));
                    }else if(!ColoredGlowLib.hasEntityColor(player)){
                        ColoredGlowLib.setColorToEntity(player, foxcolor);
                    }
                    if(FoxGlow.getAP1()){
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20*2, 200, true, false));
                    }

                }else if(!ColoredGlowLib.getEntityTypeColor(this.getType()).equals(Color.getWhiteColor())){
                    //This is done because the entity would still have another color selected otherwise.
                    ColoredGlowLib.setColorToEntity(player, Color.getWhiteColor());
                }

            }
        }
    }
}

