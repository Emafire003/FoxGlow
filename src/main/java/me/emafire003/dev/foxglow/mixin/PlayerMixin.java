package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.CGLColorCompat;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import static me.emafire003.dev.foxglow.FoxGlow.*;
import static me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat.foxcolor;

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

            //Checks if the item eaten is the glowberry/a glowfood item & if the playerglow is active
            if(FoxGlow.getGlowFoodsList().contains(Registry.ITEM.getId(stack.getItem())) && sworld.getGameRules().getBoolean(PLAYERGLOW)){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 0, true, false));

                //checks if custom color glow is enabled
                if(sworld.getGameRules().getBoolean(CUSTOM_COLOR_GLOW) && FoxGlow.getCGL()) {

                    //Checks if it should apply a random color
                    if (sworld.getGameRules().getBoolean(RANDOM_COLOR_GLOW)) {
                        ColoredGlowLibCompat.getLib().setColorToEntity(player, CGLColorCompat.randomColor(random));
                    }else if(!ColoredGlowLibCompat.getLib().hasEntityColor(player)){
                        ColoredGlowLibCompat.getLib().setColorToEntity(player, foxcolor);
                    }
                    if(FoxGlow.getAP1()){
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20, 1, true, false));
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, this.world.getGameRules().getInt(FOXGLOW_DURATION)*20*2, 200, true, false));
                    }

                }else if(!ColoredGlowLibCompat.getLib().getEntityTypeColor(this.getType()).equals(CGLColorCompat.getWhiteColor())){
                    //This is done because the entity would still have another color selected otherwise.
                    ColoredGlowLibCompat.getLib().setColorToEntity(player, CGLColorCompat.getWhiteColor());
                }

            }
        }
    }
}

